package com.salesmanager.core.business.repositories.catalog.product.feature;

import com.salesmanager.core.model.feature.ProductFeature;
import com.salesmanager.core.model.feature.ProductFeatureType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProductFeatureRepository extends JpaRepository<ProductFeature, Long> {

    @Query(value="SELECT " +
            "p " +
            "FROM " +
            "ProductFeature p " +
            "WHERE  p.productId = ?1")
    List<ProductFeature> findListByProductId(Long productId);


    @Query(value="SELECT " +
            "p " +
            "FROM " +
            "ProductFeature p " +
            "WHERE  p.productId = ?1 and p.key = ?2")
    List<ProductFeature> findListByProductIdAndTag(Long productId, String tag);



    @Query(value = "SELECT * FROM PRODUCT_FEATURE pf WHERE pf.KEY_NAME = :key AND pf.TYPE = :type ORDER BY pf.SORT  DESC LIMIT 1", nativeQuery = true)
    ProductFeature findTopByKeyOrderBySortAsc(@Param("key") String key, @Param("type") String type);

    @Modifying
    @Transactional
    @Query("UPDATE ProductFeature p SET p.sort = :sort , p.productFeatureType = :productFeatureType " +
            "WHERE p.productId = :productId and p.key = :key")
    void updateProductFeatureSort(@Param("productId") Long productId, @Param("key") String key,
                                  @Param("sort") Integer sort,
                                  @Param("productFeatureType") ProductFeatureType productFeatureType);



    @Modifying
    @Transactional
    @Query("DELETE FROM ProductFeature p WHERE p.productId = :id")
    void deleteByProductId(@Param("id") Long id);



}
