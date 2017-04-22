package com.convertion.services;

import java.util.Collection;

import com.convertion.dto.Currency;
import com.convertion.dto.QueryResponseWrapper;

/**
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 *
 */
public interface QueryService {

    Collection<Currency> supportedCurrencies();

    QueryResponseWrapper convert(String from, String to) throws Exception;

}
