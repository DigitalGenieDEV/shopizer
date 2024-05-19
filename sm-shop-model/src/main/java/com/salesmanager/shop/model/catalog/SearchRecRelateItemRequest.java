package com.salesmanager.shop.model.catalog;

import java.io.Serializable;

public class SearchRecRelateItemRequest implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Integer uid;
    private String cookieid;
    private Integer pageIdx = 0;
    private Integer size = 20;
    private Integer productId;

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

    public Integer getPageIdx() {
        return pageIdx;
    }

    public void setPageIdx(Integer pageIdx) {
        this.pageIdx = pageIdx;
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
}
