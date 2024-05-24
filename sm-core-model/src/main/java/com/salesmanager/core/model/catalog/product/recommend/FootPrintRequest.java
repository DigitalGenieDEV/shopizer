package com.salesmanager.core.model.catalog.product.recommend;

import com.fasterxml.jackson.annotation.JsonProperty;

public class FootPrintRequest {


    @JsonProperty("uid")
    private Integer uid;

    @JsonProperty("size")
    private Integer size = 20;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
