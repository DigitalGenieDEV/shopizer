package com.salesmanager.core.model.catalog.product.recommend;

import com.salesmanager.core.model.catalog.product.Product;

import java.util.ArrayList;
import java.util.List;

public class GuessULikeResult {

    private List<Product> productList = new ArrayList<>();

    private String cacheid;

    private Integer hitNumber;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }

    public Integer getHitNumber() {
        return hitNumber;
    }

    public void setHitNumber(Integer hitNumber) {
        this.hitNumber = hitNumber;
    }
}
