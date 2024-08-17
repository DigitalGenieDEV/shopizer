package com.salesmanager.core.business.repositories.material;

import com.salesmanager.core.business.repositories.catalog.product.ProductRepositoryCustom;
import com.salesmanager.core.model.catalog.product.Material;
import com.salesmanager.core.model.catalog.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;


public interface MaterialRepository extends JpaRepository<Material, Long> {

	@Query(value="SELECT " +
			"m " +
			"FROM " +
			"Material m " +
			" order by m.sort ")
	List<Material> queryList();



}
