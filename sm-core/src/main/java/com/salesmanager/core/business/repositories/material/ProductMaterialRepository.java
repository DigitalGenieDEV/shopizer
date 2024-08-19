package com.salesmanager.core.business.repositories.material;

import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.catalog.product.ProductMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


public interface ProductMaterialRepository extends JpaRepository<ProductMaterial, Long> {


	@Modifying
	@Transactional
	@Query("DELETE ProductMaterial p  WHERE p.productId = :productId")
	void deleteByProductId(@Param("productId") Long productId);


	@Query("select p from ProductMaterial p  WHERE p.productId = :productId")
	List<ProductMaterial> queryByProductId(@Param("productId") Long productId);


}
