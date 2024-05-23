package com.salesmanager.core.business.repositories.catalog.product;

import java.util.List;

import com.salesmanager.core.model.catalog.product.ProductAuditStatus;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.Product;
import org.springframework.data.repository.query.Param;


public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {


	@Query(value="SELECT " +
			"CASE WHEN COUNT(*) > 0 THEN true ELSE false END " +
			"FROM " +
			"Product p " +
			"JOIN MerchantStore m ON m.id = ?2 " +
			"LEFT JOIN ProductVariant pv ON pv.product.id = p.id " +
			"WHERE (pv.sku = ?1 OR p.sku = ?1)")
	boolean existsBySku(String sku, Integer store);


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
			"p " +
			"FROM " +
			"Product p " +
			"WHERE  p.outProductId = ?1")
	Product findByOutId(Long outId);
}
