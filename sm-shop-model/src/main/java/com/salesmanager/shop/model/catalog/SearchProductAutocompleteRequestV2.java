package com.salesmanager.shop.model.catalog;

import java.io.Serializable;

public class SearchProductAutocompleteRequestV2 implements Serializable  {


    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String lang;
    private String q;

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getQ() {
        return q;
    }

    public void setQ(String q) {
        this.q = q;
    }
}