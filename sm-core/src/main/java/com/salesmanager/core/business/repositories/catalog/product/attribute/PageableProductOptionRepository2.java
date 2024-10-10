package com.salesmanager.core.business.repositories.catalog.product.attribute;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ReadProductOption;

public interface PageableProductOptionRepository2  extends PagingAndSortingRepository<ProductOption, Long> {

	@Query(value = "SELECT  A.PRODUCT_OPTION_ID AS ID, C.NAME , A.PRODUCT_OPTION_CODE AS CODE, A.PRODUCT_OPTION_TYPE AS TYPE, D.PRODUCT_OPTION_SET_ID AS SETID, C.DESCRIPTION_ID AS DESCID  FROM PRODUCT_OPTION A INNER JOIN MERCHANT_STORE B ON A.MERCHANT_ID = B.MERCHANT_ID\r\n"
			+ "LEFT JOIN PRODUCT_OPTION_DESC C ON A.PRODUCT_OPTION_ID = C.PRODUCT_OPTION_ID LEFT JOIN PRODUCT_OPTION_SET D\r\n"
			+ "ON A.PRODUCT_OPTION_ID = D.PRODUCT_OPTION_ID AND A.MERCHANT_ID = D.MERCHANT_ID\r\n"
			+ "WHERE B.MERCHANT_ID = ?1 \r\n"
			+ "	AND (CASE WHEN ?2 <> '' THEN C.NAME LIKE %?2%  ELSE TRUE  END ) \r\n"
			+ " AND D.CATEGORY_ID= ?3 \r\n"
			+ " AND C.LANGUAGE_ID= ?4 \r\n"
			,
		    countQuery =  "SELECT COUNT(*) FROM PRODUCT_OPTION A INNER JOIN MERCHANT_STORE B ON A.MERCHANT_ID = B.MERCHANT_ID\r\n"
					+ "LEFT JOIN PRODUCT_OPTION_DESC C ON A.PRODUCT_OPTION_ID = C.PRODUCT_OPTION_ID LEFT JOIN PRODUCT_OPTION_SET D\r\n"
					+ "ON A.PRODUCT_OPTION_ID = D.PRODUCT_OPTION_ID AND A.MERCHANT_ID = D.MERCHANT_ID\r\n"
					+ "WHERE B.MERCHANT_ID = ?1 \r\n"
					+ " AND (CASE WHEN ?2 <> '' THEN C.NAME LIKE %?2%  ELSE TRUE  END ) \r\n"
					+ " AND D.CATEGORY_ID= ?3 "
					+ " AND C.LANGUAGE_ID= ?4 \r\n", nativeQuery=true)
		Page<ReadProductOption> listOptions(int merchantStoreId, String name, int categoryId, Integer languageId, Pageable pageable);

}
