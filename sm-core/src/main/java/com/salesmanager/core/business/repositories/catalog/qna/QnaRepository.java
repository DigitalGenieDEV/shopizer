package com.salesmanager.core.business.repositories.catalog.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.qna.Qna;

public interface QnaRepository extends JpaRepository<Qna, Long> {
	
	@Query(value = "SELECT pq.PRODUCT_QNA_ID AS QNA_ID "
			+ ", DATE_FORMAT(pq.QNA_DATE, '%Y-%m-%d') AS QNA_DATE "
			+ ", pqd.TITLE AS QNA_TITLE "
			+ ", pqd.DESCRIPTION AS QNA_DESCRIPTION "
			+ ", pq.SECRET "
			+ ", pq.QUESTION_TYPE "
			+ ", CASE WHEN pqr.DESCRIPTION_ID IS NULL THEN 'NONE' ELSE 'DONE' END AS REPLY_STATUS "
			+ ", c.CUSTOMER_ID "
			+ ", c.BILLING_FIRST_NAME AS CUSTOMERS_FIRST_NAME "
			+ ", c.BILLING_LAST_NAME AS CUSTOMERS_LAST_NAME "
			+ ", 'PRODUCT' AS PRODUCT_TYPE "
			+ ", p.PRODUCT_ID "
			+ ", pd.TITLE AS PRODUCT_TITLE "
			+ "FROM MERCHANT_STORE ms LEFT JOIN PRODUCT p ON ms.MERCHANT_ID = p.MERCHANT_ID "
			+ "LEFT JOIN PRODUCT_DESCRIPTION pd ON p.PRODUCT_ID = pd.PRODUCT_ID "
			+ "LEFT JOIN PRODUCT_QNA pq ON p.PRODUCT_ID = pq.PRODUCT_ID "
			+ "LEFT JOIN PRODUCT_QNA_DESCRIPTION pqd ON pq.PRODUCT_QNA_ID = pqd.PRODUCT_QNA_ID "
			+ "LEFT JOIN PRODUCT_QNA_REPLY pqr ON pq.PRODUCT_QNA_ID = pqr.PRODUCT_QNA_ID "
			+ "LEFT JOIN CUSTOMER c ON pq.CUSTOMERS_ID = c.CUSTOMER_ID "
			+ "WHERE 1 = 1 "
			+ "AND ms.MERCHANT_ID = ?1 "
			+ "AND pq.PRODUCT_QNA_ID IS NOT NULL "
			+ "AND CASE WHEN (?2 IS NULL AND ?3 IS NULL) THEN TRUE ELSE DATE_FORMAT(pq.QNA_DATE, '%Y-%m-%d') BETWEEN ?2 AND ?3 END "
			+ "AND CASE WHEN ?4 IS NOT NULL THEN pq.QUESTION_TYPE = ?4 ELSE TRUE END "
			+ "AND CASE WHEN ?5 = 'CNAME' THEN (CONCAT(c.BILLING_LAST_NAME, c.BILLING_FIRST_NAME) LIKE CONCAT('%', ?6, '%') OR CONCAT(c.BILLING_FIRST_NAME, c.BILLING_LAST_NAME) LIKE CONCAT('%', ?6, '%')) ELSE TRUE END "
			+ "AND CASE WHEN ?5 = 'PNAME' THEN pd.TITLE LIKE CONCAT('%', ?6, '%') ELSE TRUE END "
			+ "AND pd.LANGUAGE_ID = ?7 ", nativeQuery = true)
	Page<Qna> getAllByStore(Integer id, String sDate, String eDate, String category, String keywordType, String keyword,
			Integer id2, Pageable pageRequest);

}
