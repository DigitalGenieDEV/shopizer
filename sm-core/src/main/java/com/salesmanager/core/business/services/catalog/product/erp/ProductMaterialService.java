package com.salesmanager.core.business.services.catalog.product.erp;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.catalog.product.ProductMaterial;

import java.util.List;

public interface ProductMaterialService extends SalesManagerEntityService<Long, ProductMaterial> {



    void deleteProductMaterialByProductId(Long productId);


    List<ProductMaterial> queryByProductId(Long productId);
}
