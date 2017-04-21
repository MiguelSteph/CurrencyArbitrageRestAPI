package com.convertion.repositories;

import java.util.Collection;

import com.convertion.dto.Currency;

public interface CurrencyRepository {

    /**
     * Save the given currency.
     * 
     * @param currency
     */
    void saveCurrency(Currency currency);

    /**
     * Find and return the currency that match with the provided code.
     * 
     * @param code
     * @return the currency that match with the provided code.
     */
    Currency findCurrency(String code);

    /**
     * Return all the currency
     * 
     * @return A list of currencies
     */
    Collection<Currency> findAllCurrencies();

    /**
     * Delete the currency that match with the provided code.
     * 
     * @param code
     */
    void deleteCurrency(String code);

}
