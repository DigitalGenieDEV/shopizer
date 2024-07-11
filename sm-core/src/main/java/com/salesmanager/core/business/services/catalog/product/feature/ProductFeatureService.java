package com.salesmanager.core.business.services.catalog.product.feature;


import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.feature.ProductFeature;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProductFeatureService extends SalesManagerEntityService<Long, ProductFeature> {


    List<ProductFeature> findListByProductId(Long productId);


    ProductFeature findListByProductId(Long productId, String tag);


    void addMainDisplayManagementProduct(Long productId, String tag);




    public void sortUpdateMainDisplayManagementProduct(Long productId, String tag, Integer sort);


    void removeMainDisplayManagementProduct(Long productId, String tag);

}
