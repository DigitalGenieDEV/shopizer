package com.salesmanager.shop.model.recommend;

public class RecFootPrintRequest {

    private Integer uid;

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
