package com.salesmanager.core.business.services.catalog.product.feature;


import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.feature.ProductFeature;

import java.util.List;

public interface ProductFeatureService extends SalesManagerEntityService<Long, ProductFeature> {


    List<ProductFeature> findListByProductId(Long productId);
}
