package com.convertion.rest;

import static org.junit.Assert.*;

import java.util.Collection;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.convertion.configuration.DispatcherConfiguration;
import com.convertion.configuration.RootConfiguration;
import com.convertion.dto.Currency;
import com.convertion.repositories.CurrencyRepository;
import com.convertion.repositories.EdgesRepository;
import com.convertion.services.DataService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { RootConfiguration.class, DispatcherConfiguration.class })
@WebAppConfiguration
public class TestDataService {

    @Autowired
    DataService dataService;
    @Autowired
    EdgesRepository edgesRepository;
    @Autowired
    CurrencyRepository currencyRepository;
    
    @Test
    public void test() {
        long begin = System.currentTimeMillis();
        dataService.loadSupportedCurrencies();
        long time = System.currentTimeMillis() - begin;
        System.out.println("The total time to load supportedCurrencies is " + time);
        
        begin = System.currentTimeMillis();
        dataService.loadLatestEdges();
        time = System.currentTimeMillis() - begin;
        System.out.println("The total time to load loadLatestEdges is " + time);
        
        int size = 0;
        Collection<Currency> currencies = currencyRepository.findAllCurrencies();
        
        for (Currency curr : currencies) {
            size += edgesRepository.findAllEdges(curr.getCode()).size();
        }
        
        System.out.println("we have in total " + size);
        
        
        assertEquals(264346, 264346);
        
    }

}
