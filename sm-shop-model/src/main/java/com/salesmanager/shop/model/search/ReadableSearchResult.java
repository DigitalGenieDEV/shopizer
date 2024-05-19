package com.salesmanager.shop.model.search;

import com.salesmanager.shop.model.common.ReadablePaginationResult;
import com.salesmanager.shop.model.entity.ReadableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ReadableSearchResult extends ReadableList implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private Map<String, List<String>> attrForFilt;

    private List<ReadableSearchProductV2> products = new ArrayList<>();

    public Map<String, List<String>> getAttrForFilt() {
        return attrForFilt;
    }

    public void setAttrForFilt(Map<String, List<String>> attrForFilt) {
        this.attrForFilt = attrForFilt;
    }

    public List<ReadableSearchProductV2> getProducts() {
        return products;
    }

    public void setProducts(List<ReadableSearchProductV2> products) {
        this.products = products;
    }
}
