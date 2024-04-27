package com.salesmanager.core.model.catalog.product.search;

import org.apache.commons.httpclient.NameValuePair;

import java.util.ArrayList;
import java.util.List;

public class ProductSearchRequest {

    private String lang;
    private String q;
    private Integer size = 20;
    private Integer pageIdx = 1;
    private String attrFilt;

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

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getPageIdx() {
        return pageIdx;
    }

    public void setPageIdx(Integer pageIdx) {
        this.pageIdx = pageIdx;
    }

    public String getAttrFilt() {
        return attrFilt;
    }

    public void setAttrFilt(String attrFilt) {
        this.attrFilt = attrFilt;
    }

    public List<NameValuePair> getParams() {
        List<NameValuePair> pairs = new ArrayList<>();

        if (lang != null) {
            pairs.add(new NameValuePair("lang", lang));
        }

        if (q != null) {
            pairs.add(new NameValuePair("q", q));
        }

        if (size != null) {
            pairs.add(new NameValuePair("size", String.valueOf(size)));
        }

        if (pageIdx != null) {
            pairs.add(new NameValuePair("page_idx", String.valueOf(pageIdx)));
        }

        if (attrFilt != null) {
            pairs.add(new NameValuePair("attr_filt", String.valueOf(attrFilt)));
        }


        return pairs;
    }
}
