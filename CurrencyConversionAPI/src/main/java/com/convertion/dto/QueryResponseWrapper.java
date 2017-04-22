package com.convertion.dto;

import java.io.Serializable;
import java.util.List;

/**
 * This class is a helper class that will contain the response to the client
 * query.
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 */
public class QueryResponseWrapper implements Serializable {

    private static final long serialVersionUID = 1L;

    private final String from;

    private final String to;
    
    /** boolean that mark if there is an infinite arbitrage or not */
    private boolean hasInfiniteArbitrage;

    /** contains the max value possible of the conversion */
    private double bestConversionValue;

    /** contains the path that allow to get the bestConversionValue */
    private List<String> conversionPath;

    /** contains the infinite arbitrage if it exists */
    private List<String> infiniteArbitrage;

    /** contains the path from the source currency to reach the infinite arbitrage */
    private List<String> pathToInfiniteArbitrage;

    /** contains the path from the infinite arbitrage to the end currency*/
    private List<String> pathFromInfiniteArbitrage;

    public QueryResponseWrapper(String fromCurrency, String toCurrency) {
        this.from = fromCurrency;
        this.to = toCurrency;
    }

    public boolean isHasInfiniteArbitrage() {
        return hasInfiniteArbitrage;
    }

    public void setHasInfiniteArbitrage(boolean hasInfiniteArbitrage) {
        this.hasInfiniteArbitrage = hasInfiniteArbitrage;
    }

    public double getBestConversionValue() {
        return bestConversionValue;
    }

    public void setBestConversionValue(double bestConversionValue) {
        this.bestConversionValue = bestConversionValue;
    }

    public List<String> getConversionPath() {
        return conversionPath;
    }

    public void setConversionPath(List<String> conversionPath) {
        this.conversionPath = conversionPath;
    }

    public List<String> getInfiniteArbitrage() {
        return infiniteArbitrage;
    }

    public void setInfiniteArbitrage(List<String> infiniteArbitrage) {
        this.infiniteArbitrage = infiniteArbitrage;
    }

    public List<String> getPathToInfiniteArbitrage() {
        return pathToInfiniteArbitrage;
    }

    public void setPathToInfiniteArbitrage(List<String> pathToInfiniteArbitrage) {
        this.pathToInfiniteArbitrage = pathToInfiniteArbitrage;
    }

    public List<String> getPathFromInfiniteArbitrage() {
        return pathFromInfiniteArbitrage;
    }

    public void setPathFromInfiniteArbitrage(List<String> pathFromInfiniteArbitrage) {
        this.pathFromInfiniteArbitrage = pathFromInfiniteArbitrage;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    @Override
    public String toString() {
        return "QueryResponseWrapper [from=" + from + ", to=" + to + ", hasInfiniteArbitrage=" + hasInfiniteArbitrage
                + ", bestConversionValue=" + bestConversionValue + ", conversionPath=" + conversionPath
                + ", infiniteArbitrage=" + infiniteArbitrage + ", pathToInfiniteArbitrage=" + pathToInfiniteArbitrage
                + ", pathFromInfiniteArbitrage=" + pathFromInfiniteArbitrage + "]";
    }
    
}
