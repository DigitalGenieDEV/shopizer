package com.salesmanager.shop.model.recommend;

import com.salesmanager.shop.model.entity.ReadableList;

import java.util.ArrayList;
import java.util.List;

public class ReadableRecProductList extends ReadableList {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private List<ReadableDisplayProduct> products = new ArrayList<>();

    private String cacheid;

    public List<ReadableDisplayProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ReadableDisplayProduct> products) {
        this.products = products;
    }

    public String getCacheid() {
        return cacheid;
    }

    public void setCacheid(String cacheid) {
        this.cacheid = cacheid;
    }
}
