package com.salesmanager.shop.model.search;

import com.salesmanager.shop.model.entity.ReadableList;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReadableSearchResult extends ReadableList implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ReadableAttrForFilt filterOptions;

    private List<ReadableSearchProductV2> products = new ArrayList<>();

    public ReadableAttrForFilt getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(ReadableAttrForFilt filterOptions) {
        this.filterOptions = filterOptions;
    }

    public List<ReadableSearchProductV2> getProducts() {
        return products;
    }

    public void setProducts(List<ReadableSearchProductV2> products) {
        this.products = products;
    }
}
