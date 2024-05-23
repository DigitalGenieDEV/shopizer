package com.salesmanager.core.business.services.catalog.product.feature;

import com.salesmanager.core.business.repositories.catalog.product.feature.ProductFeatureRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.feature.ProductFeature;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service
public class ProductFeatureServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductFeature> implements ProductFeatureService {
    public ProductFeatureServiceImpl(JpaRepository<ProductFeature, Long> repository) {
        super(repository);
    }

    ProductFeatureRepository productFeatureRepository;

    @Inject
    public ProductFeatureServiceImpl(ProductFeatureRepository productFeatureRepository) {
        super(productFeatureRepository);
        this.productFeatureRepository = productFeatureRepository;
    }


    @Override
    public List<ProductFeature> findListByProductId(Long productId) {
        if (productId==null){
            return null;
        }
        return productFeatureRepository.findListByProductId(productId);
    }
}
