package com.convertion.services;

/**
 * This service will fill our redis database and can also refresh it.
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 *
 */
public interface DataService {

    /**
     * this method read the supported currencies files and save all the currencies.
     */
    void loadSupportedCurrencies();
    
    /**
     * this method queries the http://api.fixer.io api and save the latest rates
     */
    void loadLatestEdges();
    
}
