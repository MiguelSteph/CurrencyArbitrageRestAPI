package com.convertion.dto;

import java.io.Serializable;

/**
 * This class represent a Currency
 * 
 * @author KAKANAKOU Miguel Stephane (Skakanakou@gmail.com)
 *
 */
public class Currency implements Serializable {

    private static final long serialVersionUID = 1L;

    /** the code of a currency */
    private final String code;

    /** the name of a currency */
    private final String fullname;

    public Currency(String code, String fullname) {
        this.code = code;
        this.fullname = fullname;
    }

    public String getCode() {
        return code;
    }

    public String getFullname() {
        return fullname;
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
        Currency other = (Currency) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Currency [code=" + code + ", fullname=" + fullname + "]";
    }

}
