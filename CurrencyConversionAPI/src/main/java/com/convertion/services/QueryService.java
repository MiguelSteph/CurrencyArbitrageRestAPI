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

    /**
     * return a collection of all the supported currencies.
     * 
     * @return collection of supported currencies.
     */
    Collection<Currency> supportedCurrencies();

    /**
     * Find the best way to convert the from currency to the to currency.
     * 
     * @param from
     *            the source currency
     * @param to
     *            the destination currency
     * @return return a QueryResponseWrapper object that contains the answer to
     *         the query.
     * @throws Exception
     *             Throw Exception when there is no currency that match with the
     *             provided parameters
     */
    QueryResponseWrapper convert(String from, String to) throws Exception;

}
