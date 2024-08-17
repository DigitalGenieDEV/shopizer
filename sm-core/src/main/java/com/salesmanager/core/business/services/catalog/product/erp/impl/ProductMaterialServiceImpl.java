package com.salesmanager.core.business.services.catalog.product.erp.impl;

import com.salesmanager.core.business.repositories.material.MaterialRepository;
import com.salesmanager.core.business.repositories.material.ProductMaterialRepository;
import com.salesmanager.core.business.services.catalog.product.erp.ErpService;
import com.salesmanager.core.business.services.catalog.product.erp.ProductMaterialService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.catalog.product.ProductMaterial;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ProductMaterialServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductMaterial> implements ProductMaterialService {


    private ProductMaterialRepository productMaterialRepository;

    @Inject
    public ProductMaterialServiceImpl(ProductMaterialRepository productMaterialRepository) {
        super(productMaterialRepository);
        this.productMaterialRepository = productMaterialRepository;
    }

    @Override
    public void deleteProductMaterialByProductId(Long productId) {
        productMaterialRepository.deleteByProductId(productId);
    }

    @Override
    public List<ProductMaterial> queryByProductId(Long productId) {
        if (productId==null){
            return null;
        }
        return productMaterialRepository.queryByProductId(productId);
    }


}
