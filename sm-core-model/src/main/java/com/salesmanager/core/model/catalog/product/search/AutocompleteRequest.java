package com.salesmanager.core.model.catalog.product.search;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AutocompleteRequest {

    @JsonProperty("uid")
    private Integer uid;

    @JsonProperty("deviceid")
    private String deviceid;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("q")
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
//        return pairs;
//    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }
}
