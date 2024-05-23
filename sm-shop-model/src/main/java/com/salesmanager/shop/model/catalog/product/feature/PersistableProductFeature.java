package com.salesmanager.shop.model.catalog.product.feature;

import com.salesmanager.core.model.feature.ProductFeatureStatus;

import java.io.Serializable;

public class PersistableProductFeature extends ProductFeature implements Serializable {

    private static final long serialVersionUID = 1L;


    private String key;

    private String value;

    private String productId;

    private ProductFeatureStatus productFeatureStatus;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public ProductFeatureStatus getProductFeatureStatus() {
        return productFeatureStatus;
    }

    public void setProductFeatureStatus(ProductFeatureStatus productFeatureStatus) {
        this.productFeatureStatus = productFeatureStatus;
    }
}
