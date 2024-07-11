package com.salesmanager.shop.model.catalog.product.feature;

import com.salesmanager.core.model.feature.ProductFeatureStatus;
import com.salesmanager.shop.model.entity.Entity;
import lombok.Data;

import java.io.Serializable;

@Data
public class ProductFeature extends Entity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;


    private String key;

    private String value;

    private Long productId;


    private Integer sort;

    private ProductFeatureStatus productFeatureStatus;

}
