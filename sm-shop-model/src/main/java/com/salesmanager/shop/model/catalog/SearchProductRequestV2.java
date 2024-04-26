package com.salesmanager.shop.model.catalog;

import java.io.Serializable;

public class SearchProductRequestV2 implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
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
}
