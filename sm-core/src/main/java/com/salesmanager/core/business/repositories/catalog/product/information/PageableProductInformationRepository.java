package com.salesmanager.core.business.repositories.catalog.product.information;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.information.ProductInformation;
import com.salesmanager.core.model.catalog.product.information.ReadProductInformation;

public interface PageableProductInformationRepository extends JpaRepository<ProductInformation, Long>{
	
	@Query(value = "SELECT  ID, DIVISION, DESC1, DESC2, DESC3 FROM PRODUCT_INFORMATION \r\n"
			+ "WHERE DIVISION = ?3  \r\n"
			+ "	AND  MERCHANT_ID  = ?1 \r\n"
			+ "	AND LANGUAGE_ID = ?2 \r\n"
			+ "ORDER BY DATE_CREATED DESC ",
	      countQuery = "SELECT  COUNT(ID)  FROM PRODUCT_INFORMATION  WHERE 1=1  \r\n"
	    		  + "WHERE DIVISION = ?3  \r\n"
	  			+ "	AND  MERCHANT_ID  = ?1 \r\n"
	  			+ "	AND LANGUAGE_ID = ?2 \r\n", nativeQuery=true)
	Page<ReadProductInformation> getList(int store, int language, String division, Pageable pageable);
}
