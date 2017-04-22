package com.convertion.services;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.convertion.dto.Currency;
import com.convertion.dto.Edges;
import com.convertion.dto.QueryResponseWrapper;
import com.convertion.repositories.CurrencyRepository;
import com.convertion.repositories.EdgesRepository;

@Service
public class QueryServiceImpl implements QueryService {

    private static final int currencySize = 171;
    
    private CurrencyRepository currencyRepository;
    private EdgesRepository edgesRepository;
    
    @Autowired
    public QueryServiceImpl(CurrencyRepository currencyRepository, EdgesRepository edgesRepository) {
        this.currencyRepository = currencyRepository;
        this.edgesRepository = edgesRepository;
    }
    
    @Override
    public Collection<Currency> supportedCurrencies() {
        return currencyRepository.findAllCurrencies();
    }

    @Override
    public QueryResponseWrapper convert(String from, String to) throws Exception {
        Currency fromCurr = currencyRepository.findCurrency(from);
        Currency toCurr = currencyRepository.findCurrency(to);
        
        if ((fromCurr == null) || (toCurr == null)) {
            throw new Exception("The given currencies are not supported");
        }
        
        QueryResponseWrapper response = new QueryResponseWrapper(from, to);
        response.setFromCurrFullName(fromCurr.getFullname());
        response.setToCurrFullName(toCurr.getFullname());
        
        performBellManFord(from, to, response);
        
        return response;
    }
    
    private void performBellManFord(String from, String to, QueryResponseWrapper response) {
        Map<String, String> prev = new HashMap<>();
        Map<String, Double> replacedDist = new HashMap<>();
        
        Collection<Currency> currencies = currencyRepository.findAllCurrencies();
        for (Currency curr : currencies) {
            replacedDist.put(curr.getCode(), Double.MAX_VALUE);
            prev.put(curr.getCode(), null);
        }
        
        replacedDist.put(from, 0.0);
        
        String u;
        String v;
        Collection<Edges> allEdges;
        for (int i=0; i<currencySize-1; i++) {
            allEdges = edgesRepository.findAllEdges();
            for (Edges edge : allEdges) {
                v = edge.getEnd();
                u = edge.getFrom();
                if ((replacedDist.get(u) != Double.MAX_VALUE) && (replacedDist.get(v) > replacedDist.get(u) + getReplacedDist(edge.getRates()))) {
                    //update the preview
                    prev.put(v, u);
                    //update replacedDist
                    replacedDist.put(v, replacedDist.get(u) + getReplacedDist(edge.getRates()));
                }
            }
        }
        
        Queue<String> lastRelaxed = new ArrayDeque<>();
        allEdges = edgesRepository.findAllEdges();
        for (Edges edge : allEdges) {
            v = edge.getEnd();
            u = edge.getFrom();
            if ((replacedDist.get(u) != Double.MAX_VALUE) && (replacedDist.get(v) > replacedDist.get(u) + getReplacedDist(edge.getRates()))) {
                //update the preview
                prev.put(v, u);
                //update replacedDist
                replacedDist.put(v, replacedDist.get(u) + getReplacedDist(edge.getRates()));
                lastRelaxed.add(v);
            }
        }
        
        Collection<Edges> outgoingEdges;
        
        if (!lastRelaxed.isEmpty()) {
            //we have infinite arbitrage
            response.setHasInfiniteArbitrage(true);
            response.setConversionPath(null);
            response.setBestConversionValue(-1);
            
            //Detect the infinite arbitrage
            u = lastRelaxed.peek();
            for (int i=0; i<currencySize; i++) {
                u = prev.get(u);
            }
            List<String> infiniteArbitrage = new ArrayList<>();
            String y = u;
            infiniteArbitrage.add(y);
            do {
                u = prev.get(u);
                infiniteArbitrage.add(u);
            }while (!y.equals(u));
            
            response.setInfiniteArbitrage(infiniteArbitrage);
            
            System.out.println(infiniteArbitrage);
            
            //Detect the path to and from the infinite arbitrage
            Map<String, String> directRoutPrev = new HashMap<>();
            
            boolean find = false;
            Queue<String> que = new ArrayDeque<>();
            u = from;
            String inInfiniteArbitrage = null;
            que.add(u);
            while (!que.isEmpty()) {
                if (find) {
                    break;
                }
                u = que.poll();
                outgoingEdges = edgesRepository.findAllEdgesFromU(u);
                for (Edges edge : outgoingEdges) {
                    v = edge.getEnd();
                    if (!directRoutPrev.containsKey(v)) {
                        directRoutPrev.put(v, u);
                        if (infiniteArbitrage.contains(v)) {
                            find = true;
                            inInfiniteArbitrage = v;
                        }
                        que.add(v);
                    }
                }
            }
            
            List<String> pathToInfiniteArbitrage = new ArrayList<>();
            u = inInfiniteArbitrage;
            pathToInfiniteArbitrage.add(inInfiniteArbitrage);
            while (!u.equals(directRoutPrev.get(u))) {
                u = directRoutPrev.get(u);
                if (u == null) {
                    break;
                }
                System.out.println("u is "+ u);
                pathToInfiniteArbitrage.add(u);
            }
            
            List<String> realPathToInfiniteArbitrage = new ArrayList<>();
            for (int i=pathToInfiniteArbitrage.size()-1; i>=0; i--) {
                realPathToInfiniteArbitrage.add(pathToInfiniteArbitrage.get(i));
            }
            
            System.out.println("directRoutPrev "+ directRoutPrev);
            System.out.println();
            
            System.out.println();
            System.out.println("pathToInfiniteArbitrage "+ realPathToInfiniteArbitrage);
            System.out.println();
            
            response.setPathToInfiniteArbitrage(realPathToInfiniteArbitrage);
            
        } else {
            // we don't have infinite arbitrage
            response.setHasInfiniteArbitrage(false);
            List<String> inter = new ArrayList<>();
            u = to;
            System.out.println(u);
            System.out.println(from);
            while (!u.equals(from)) {
                inter.add(u);
                u = prev.get(u);
                System.out.println(u);
            }
            List<String> conversionPath = new ArrayList<>();
            for (int i = inter.size()-1; i>=0; i--) {
                conversionPath.add(inter.get(i));
            }
            double bestConversionVal = 1;
            String begin,endd;
            for (int i=0; i<conversionPath.size()-1; i++) {
                begin = conversionPath.get(i);
                endd = conversionPath.get(i+1);
                bestConversionVal = bestConversionVal * edgesRepository.findEdge(begin+"_"+endd).getRates();
            }
            
            response.setBestConversionValue(bestConversionVal);
            response.setConversionPath(conversionPath);
        }
        
    }
    
    private double getReplacedDist(double initialRate) {
        return (-1) * (Math.log(initialRate) / Math.log(2));
    }

}
