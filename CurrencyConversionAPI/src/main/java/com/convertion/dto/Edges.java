package com.convertion.dto;

import java.io.Serializable;

/**
 * This class represent the directed edge between two currencies.
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 *
 */
public class Edges implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * the code of a directed edges. the code is formatted as follow from_end.
     */
    private final String code;

    private final String from;

    private final String end;

    private final double rates;

    public Edges(String from, String end, double rates) {
        this.from = from;
        this.end = end;
        this.code = from + "_" + end;
        this.rates = rates;
    }

    public String getCode() {
        return code;
    }

    public String getFrom() {
        return from;
    }

    public String getEnd() {
        return end;
    }

    public double getRates() {
        return rates;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Edges other = (Edges) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Edges [code=" + code + ", from=" + from + ", end=" + end + ", rates=" + rates + "]";
    }

}
