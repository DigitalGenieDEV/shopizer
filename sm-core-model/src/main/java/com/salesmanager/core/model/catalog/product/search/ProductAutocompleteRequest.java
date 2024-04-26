package com.salesmanager.core.model.catalog.product.search;

import org.apache.commons.httpclient.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ProductAutocompleteRequest {

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

    public List<NameValuePair> getParams() {
        List<NameValuePair> pairs = new ArrayList<>();

        if (lang != null) {
            pairs.add(new NameValuePair("lang", lang));
        }

        if (q != null) {
            pairs.add(new NameValuePair("q", q));
        }

        return pairs;
    }
}
