package com.salesmanager.shop.model.recommend;

public class RecSelectionProductRequest {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer uid;
    private String deviceId;
    private Integer categoryId;
    private Integer number = 0;
    private Integer size = 20;
    private String tag;
    private String cacheid;

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

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }
}
