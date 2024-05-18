package com.salesmanager.core.model.catalog.product.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SelectionItemRequest {

    @JsonProperty("uid")
    private Integer uid;

    @JsonProperty("cookieid")
    private String cookieid;

    @JsonProperty("size")
    private Integer size = 20;

    @JsonProperty("page_idx")
    private Integer pageIdx = 0;

    @JsonProperty("selection_id")
    private Integer selectionId;

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

    public Integer getSelectionId() {
        return selectionId;
    }

    public void setSelectionId(Integer selectionId) {
        this.selectionId = selectionId;
    }
}
