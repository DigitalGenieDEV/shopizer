package com.salesmanager.core.model.catalog.product.search;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.recommend.ProductResult;

import java.util.List;
import java.util.Map;

public class SearchProductResult {

    private List<ProductResult> productResults;

    private Map<String, List<String>> filterOptions;

    private Integer hitNumber;

    public List<ProductResult> getProductResults() {
        return productResults;
    }

    public void setProductResults(List<ProductResult> productResults) {
        this.productResults = productResults;
    }

    public Map<String, List<String>> getFilterOptions() {
        return filterOptions;
    }

    public void setFilterOptions(Map<String, List<String>> filterOptions) {
        this.filterOptions = filterOptions;
    }

    public Integer getHitNumber() {
        return hitNumber;
    }

    public void setHitNumber(Integer hitNumber) {
        this.hitNumber = hitNumber;
    }
}
