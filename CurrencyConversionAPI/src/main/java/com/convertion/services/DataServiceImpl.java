package com.convertion.services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.convertion.dto.ApiFixerConsumerHelper;
import com.convertion.dto.Currency;
import com.convertion.dto.Edges;
import com.convertion.repositories.CurrencyRepository;
import com.convertion.repositories.EdgesRepository;

/**
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 *
 */
@Service
public class DataServiceImpl implements DataService {

    private final static String baseUrl = "http://api.fixer.io/latest";
    private CurrencyRepository currencyRepository;
    private EdgesRepository edgesRepository;
    
    @Autowired
    public DataServiceImpl(CurrencyRepository currencyRepository, EdgesRepository edgesRepository) {
        this.currencyRepository = currencyRepository;
        this.edgesRepository = edgesRepository;
    }
    
    /** {@inheritDoc} */
    @Override
    public void loadSupportedCurrencies() {
        Resource res = new ClassPathResource("supportedCurrencies/supportedCurrencies.txt");
        try {
            File f = res.getFile();
            BufferedReader b = new BufferedReader(new FileReader(f));
            String readLine;
            String[] strTab;
            while ((readLine = b.readLine()) != null) {
                if (!readLine.isEmpty()) {
                    strTab = readLine.split("::");
                    currencyRepository.saveCurrency(new Currency(strTab[0], strTab[1]));
                }
            }
            b.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("loading finished");
    }

    @Override
    public void loadLatestEdges() {
        Collection<Currency> currencies = currencyRepository.findAllCurrencies();
        RestTemplate restTemplate = new RestTemplate();
        ApiFixerConsumerHelper helper;
        int i=0;
        for (Currency currency : currencies) {
            Map<String, String> params = new HashMap<String, String>();
            params.put("base", currency.getCode());
            System.out.println(++i);
            try {
                helper = restTemplate.getForObject(baseUrl, ApiFixerConsumerHelper.class, params);
                if (helper == null) {
                    System.out.println(currency.getCode());
                }else {
                    Edges edges;
                    for (String key : helper.getRates().keySet()) {
                        edges = null;
                        edges = edgesRepository.findEdge(currency.getCode()+"_"+key);
                        if (edges != null) {
                            edgesRepository.deleteEdge(edges.getCode());
                        }
                        edges = new Edges(currency.getCode(), key, helper.getRates().get(key));
                        edgesRepository.saveEdges(edges);
                    }
                }
            }catch(Exception e) {
                System.out.println(e.getMessage());
                System.out.println(currency.getCode());
            }
        }
        
    }

}
