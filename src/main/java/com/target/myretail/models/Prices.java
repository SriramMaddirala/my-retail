package com.target.myretail.models;

import org.springframework.data.annotation.Id;

public class Prices {
    @Id
    private String id;
    private String value;
    private String currencyCode;
    public Prices(String id, String value, String currencyCode){
        this.id = id;
        this.value = value;
        this.currencyCode = currencyCode;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }
}
