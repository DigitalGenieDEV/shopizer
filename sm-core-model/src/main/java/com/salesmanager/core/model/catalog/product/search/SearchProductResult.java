package com.salesmanager.core.model.catalog.product.search;

import com.salesmanager.core.model.catalog.product.Product;

import java.util.List;
import java.util.Map;

public class SearchProductResult {

    private List<Product> productList;

    private Map<String, List<String>> filterOptions;

    public List<Product> getProductList() {
        return productList;
    }

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    public Map<String, List<String>> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(Map<String, List<String>> filterOptions) {
        this.filterOptions = filterOptions;
    }
}
