package com.salesmanager.shop.model.catalog;

import java.io.Serializable;

public class SearchRecRelateItemRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer uid;
    private String deviceId;
    private Integer number = 0;
    private Integer size = 20;
    private Integer productId;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
