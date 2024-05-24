package com.salesmanager.core.model.catalog.product.search;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public class SearchRequest {

    @JsonProperty("uid")
    private Integer uid;

    @JsonProperty("deviceid")
    private String deviceid;

    @JsonProperty("lang")
    private String lang;

    @JsonProperty("q")
    private String q;

    @JsonProperty("size")
    private Integer size = 20;

    @JsonProperty("page_idx")
    private Integer pageIdx = 0;

    @JsonProperty("sort")
    private String sort;

    @JsonProperty("filter_options")
    private Map<String, List<String>> filterOptions;

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

    public Map<String, List<String>> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(Map<String, List<String>> filterOptions) {
        this.filterOptions = filterOptions;
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


    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }
}
