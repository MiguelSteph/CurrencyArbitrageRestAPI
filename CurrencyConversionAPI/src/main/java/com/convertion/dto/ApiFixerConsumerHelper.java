package com.convertion.dto;

import java.util.Map;

/**
 * This class help to consume the http://api.fixer.io/latest rest api.
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 *
 */
public class ApiFixerConsumerHelper {

    private String base;
    private String date;
    private Map<String, Double> rates;
    public String getBase() {
        return base;
    }
    public void setBase(String base) {
        this.base = base;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public Map<String, Double> getRates() {
        return rates;
    }
    public void setRates(Map<String, Double> rates) {
        this.rates = rates;
    }
    
}
