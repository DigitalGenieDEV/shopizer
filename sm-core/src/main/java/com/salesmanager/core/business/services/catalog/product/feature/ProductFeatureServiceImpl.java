package com.salesmanager.core.business.services.catalog.product.feature;

import com.salesmanager.core.business.repositories.catalog.product.feature.ProductFeatureRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.feature.ProductFeature;
import com.salesmanager.core.model.feature.ProductFeatureType;
import org.apache.commons.collections4.CollectionUtils;
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

    @Override
    public ProductFeature findListByProductId(Long productId, String tag) {
        List<ProductFeature> listByProductIdAndTag = productFeatureRepository.findListByProductIdAndTag(productId, tag);
        if (CollectionUtils.isEmpty(listByProductIdAndTag)){
            return null;
        }
        return listByProductIdAndTag.get(0);
    }

    @Override
    public void addMainDisplayManagementProduct(Long productId, String tag) {
        ProductFeature productFeature =  findListByProductId(productId, tag);
        if (productFeature == null){
            //throw error
        }
        if (productFeature.getProductFeatureType()== null ||
                ProductFeatureType.MARKET_SORT == productFeature.getProductFeatureType()){
            ProductFeature productFeatureRepositoryTopByKeyOrderBySortAsc = productFeatureRepository.findTopByKeyOrderBySortAsc(tag, ProductFeatureType.MARKET_SORT.name());
            if (productFeatureRepositoryTopByKeyOrderBySortAsc == null || productFeatureRepositoryTopByKeyOrderBySortAsc.getSort() == null ){
                productFeatureRepository.updateProductFeatureSort(productId, tag, 1, ProductFeatureType.MARKET_SORT);
            }else {
                productFeatureRepository.updateProductFeatureSort(productId, tag, productFeatureRepositoryTopByKeyOrderBySortAsc.getSort() + 1, ProductFeatureType.MARKET_SORT);
            }
        }

    }

    @Override
    public void sortUpdateMainDisplayManagementProduct(Long productId, String tag, Integer sort) {
        productFeatureRepository.updateProductFeatureSort(productId, tag, sort, ProductFeatureType.MARKET_SORT);
    }

    @Override
    public void removeMainDisplayManagementProduct(Long productId, String tag) {
        productFeatureRepository.updateProductFeatureSort(productId, tag, null, null);
    }
}
