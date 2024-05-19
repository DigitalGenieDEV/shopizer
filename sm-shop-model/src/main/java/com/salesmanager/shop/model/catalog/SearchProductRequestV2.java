package com.salesmanager.shop.model.catalog;

import java.io.Serializable;
import java.util.Map;

public class SearchProductRequestV2 implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer uid;
    private String cookieid;
    private String lang;
    private String q;
    private Integer size = 20;
    private Integer pageIdx = 1;
    private Map<String, String> attrFilt;

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

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getCookieid() {
        return cookieid;
    }

    public void setCookieid(String cookieid) {
        this.cookieid = cookieid;
    }

    public Map<String, String> getAttrFilt() {
        return attrFilt;
    }

    public void setAttrFilt(Map<String, String> attrFilt) {
        this.attrFilt = attrFilt;
    }
}
