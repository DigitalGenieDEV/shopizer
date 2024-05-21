package com.salesmanager.core.business.repositories.catalog.product.feature;

import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.feature.ProductFeature;
import com.salesmanager.core.model.catalog.product.price.ProductPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ProductFeatureRepository extends JpaRepository<ProductFeature, Long> {

    @Query(value="SELECT " +
            "p " +
            "FROM " +
            "ProductFeature p " +
            "WHERE  p.productId = ?1")
    List<ProductFeature> findListByProductId(Long productId);
}
