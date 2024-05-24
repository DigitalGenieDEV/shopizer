package com.salesmanager.shop.model.catalog;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class SearchProductRequestV2 implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer uid;
    private String deviceId;
    private String lang;
    private String q;
    private Integer size = 20;
    private Integer number = 0;
    private String sort;
    private Map<String, List<Object>> filterOptions;

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

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Map<String, List<Object>> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(Map<String, List<Object>> filterOptions) {
        this.filterOptions = filterOptions;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
