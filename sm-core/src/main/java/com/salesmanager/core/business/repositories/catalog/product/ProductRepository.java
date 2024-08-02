package com.salesmanager.core.business.repositories.catalog.product;

import java.math.BigDecimal;
import java.util.List;

import com.salesmanager.core.model.catalog.product.ProductAuditStatus;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.Product;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {


	@Query(value="SELECT " +
			"CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
			"FROM " +
			"Product p " +
			"JOIN MerchantStore m ON m.id = ?2 " +
			"LEFT JOIN ProductVariant pv ON pv.product.id = p.id " +
			"WHERE (pv.sku = ?1 OR p.sku = ?1)")
	boolean existsBySku(String sku, Integer store);


	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.discount = :discount WHERE p.id = :productId")
	void updateProductDiscount(@Param("productId") Long productId, @Param("discount") Integer discount);


	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.price = :price WHERE p.id = :productId")
	void updateProductPrice(@Param("productId") Long productId, @Param("price") BigDecimal price);


	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.priceRangeList = :priceRange WHERE p.id = :productId")
	void updateProductPriceRange(@Param("productId") Long productId, @Param("priceRange") String priceRange);



	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.type.id = :productTypeId WHERE p.id = :productId")
	void updateProductType(@Param("productId") Long productId, @Param("productTypeId") Long productTypeId);

	@Modifying
	@Transactional
	@Query(value = "INSERT INTO PRODUCT_CATEGORY (PRODUCT_ID, CATEGORY_ID) VALUES (:productId, :categoryId) ON DUPLICATE KEY UPDATE CATEGORY_ID = :categoryId", nativeQuery = true)
	void addOrUpdateProductCategory(@Param("productId") Long productId, @Param("categoryId") Long categoryId);

	@Modifying
	@Transactional
	@Query(value ="UPDATE PRODUCT_CATEGORY pc SET pc.CATEGORY_ID = :categoryId WHERE pc.PRODUCT_ID = :productId", nativeQuery = true)
	void updateProductCategory(@Param("productId") Long productId, @Param("categoryId") Long categoryId);


	@Modifying
	@Transactional
	@Query("UPDATE Product p SET p.hsCode = :hsCode WHERE p.id = :productId")
	void updateProductHsCode(@Param("productId") Long productId, @Param("hsCode") String hsCode);

	@Query(value="SELECT " +
			"CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
			"FROM " +
			"Product p " +
			"WHERE  p.sku = ?1")
	boolean existsByProductIdentifier(String code);




	@Query(value="SELECT " +
			" COUNT(*) " +
			"FROM " +
			"Product p ")
	Integer countProduct();


	@Query(value="SELECT " +
			" COUNT(*) " +
			"FROM " +
			"Product p " +
			"JOIN MerchantStore m ON m.id = ?2 " +
			"WHERE p.shippingTemplateId = ?1 ")
	Integer countProductByShippingTemplateIdAndStoreId(Long shippingTemplateId, Integer store);


	@Query(value = "SELECT COUNT(p) FROM Product p JOIN p.categories c WHERE c.id IN :categoryIds")
	Long countProductsByCategoryIds(@Param("categoryIds") List<Long> categoryIds);


	@Query(

			value = "select p.PRODUCT_ID from {h-schema}PRODUCT p join {h-schema}MERCHANT_STORE m ON p.MERCHANT_ID = m.MERCHANT_ID left join {h-schema}PRODUCT_VARIANT i ON i.PRODUCT_ID = p.PRODUCT_ID where p.SKU=?1 or i.SKU=?1 and m.MERCHANT_ID=?2",
			nativeQuery = true
	)
	List<Object> findBySku(String sku, Integer consultId);

	@Query(

			value = "select p.PRODUCT_ID from {h-schema}PRODUCT p left join {h-schema}PRODUCT_VARIANT i ON i.PRODUCT_ID = p.PRODUCT_ID where p.SKU=?1 or i.SKU=?1",
			nativeQuery = true
	)
	List<Object> findBySku(String sku);


	@Query(value="SELECT " +
			"p.id " +
			"FROM " +
			"Product p " +
			"WHERE  p.outProductId is not null ")
	List<Long> findListByOutId();

	@Query(value="SELECT " +
			"p " +
			"FROM " +
			"Product p " +
			"WHERE  p.outProductId = ?1")
	Product findByOutId(Long outId);
}
