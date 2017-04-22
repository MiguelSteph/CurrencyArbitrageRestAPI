package com.convertion.repositories;

import java.util.Collection;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.convertion.dto.Currency;

/**
 * This is an implementation of the CurrencyRepository
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 */

@Repository
public class CurrencyRepositoryImpl implements CurrencyRepository {

    private static final String KEY = "Currency";

    private RedisTemplate<String, Currency> redisTemplate;
    private HashOperations<String, String, Currency> hashOps;

    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Autowired
    public CurrencyRepositoryImpl(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @PostConstruct
    private void init() {
        hashOps = redisTemplate.opsForHash();
    }

    /** {@inheritDoc} */
    @Override
    public void saveCurrency(Currency currency) {
        hashOps.put(KEY, currency.getCode(), currency);
    }

    /** {@inheritDoc} */
    @Override
    public Currency findCurrency(String code) {
        return hashOps.get(KEY, code);
    }

    /** {@inheritDoc} */
    @Override
    public Collection<Currency> findAllCurrencies() {
        return hashOps.entries(KEY).values();
    }

    /** {@inheritDoc} */
    @Override
    public void deleteCurrency(String code) {
        hashOps.delete(KEY, code);
    }

}
