package com.salesmanager.core.business.services.catalog.product.erp;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.catalog.product.ProductMaterial;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ErpService extends SalesManagerEntityService<Long, Material> {

    List<Material> queryList();


    void deleteProductMaterialByProductId(Long productId);


    List<ProductMaterial> queryByProductId(Long productId);
}
