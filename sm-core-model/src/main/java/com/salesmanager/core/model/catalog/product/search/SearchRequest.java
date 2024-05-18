package com.salesmanager.core.model.catalog.product.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class SearchRequest {

    @JsonProperty("uid")
    private Integer uid;

    @JsonProperty("cookieid")
    private String cookieid;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("q")
    private String q;

    @JsonProperty("size")
    private Integer size = 20;

    @JsonProperty("page_idx")
    private Integer pageIdx = 0;

    @JsonProperty("attr_filt")
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

    public Map<String, String> getAttrFilt() {
        return attrFilt;
    }

    public void setAttrFilt(Map<String, String> attrFilt) {
        this.attrFilt = attrFilt;
    }

    //    public List<NameValuePair> getParams() {
//        List<NameValuePair> pairs = new ArrayList<>();
//
//        if (lang != null) {
//            pairs.add(new NameValuePair("lang", lang));
//        }
//
//        if (q != null) {
//            pairs.add(new NameValuePair("q", q));
//        }
//
//        if (size != null) {
//            pairs.add(new NameValuePair("size", String.valueOf(size)));
//        }
//
//        if (pageIdx != null) {
//            pairs.add(new NameValuePair("page_idx", String.valueOf(pageIdx)));
//        }
//
//        if (attrFilt != null) {
//            pairs.add(new NameValuePair("attr_filt", String.valueOf(attrFilt)));
//        }
//
//
//        return pairs;
//    }

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
}
