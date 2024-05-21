package com.salesmanager.shop.model.recommend;

import com.salesmanager.shop.model.entity.ReadableList;

import java.util.ArrayList;
import java.util.List;

public class ReadableRecProductList extends ReadableList {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<ReadableRecProduct> products = new ArrayList<>();

    private String cacheid;

    public List<ReadableRecProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ReadableRecProduct> products) {
        this.products = products;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }
}
