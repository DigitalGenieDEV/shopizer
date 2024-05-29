package com.salesmanager.shop.model.search;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.recommend.ReadableDisplayProduct;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ReadableSearchResult extends ReadableList implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private ReadableAttrForFilt filterOptions;

    private List<ReadableDisplayProduct> products = new ArrayList<>();

    public ReadableAttrForFilt getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(ReadableAttrForFilt filterOptions) {
        this.filterOptions = filterOptions;
    }

    public List<ReadableDisplayProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ReadableDisplayProduct> products) {
        this.products = products;
    }
}
