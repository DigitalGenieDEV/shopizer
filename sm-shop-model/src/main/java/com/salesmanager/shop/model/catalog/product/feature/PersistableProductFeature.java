package com.salesmanager.shop.model.catalog.product.feature;

import com.salesmanager.core.model.feature.ProductFeatureStatus;
import lombok.Data;

import java.io.Serializable;

@Data
public class PersistableProductFeature extends ProductFeature implements Serializable {

    private static final long serialVersionUID = 1L;


}
