package com.salesmanager.core.business.repositories.catalog.product.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;

public interface ProductQnaRepository extends JpaRepository<ProductQna, Long> {
	
	@Query(value = "SELECT * "
			+ "FROM PRODUCT_QNA pq "
			+ "WHERE 1 = 1 "
			+ "AND pq.PRODUCT_ID = ?1 "
			+ "AND CASE WHEN ?4 = FALSE THEN TRUE ELSE pq.SECRET = FALSE END "
			+ "AND CASE WHEN ?3 = TRUE then CUSTOMERS_ID = ?2 ELSE TRUE END "
			+ "AND CASE WHEN ?5 IS NULL THEN TRUE ELSE QUESTION_TYPE = ?5 END", nativeQuery = true)
	Page<ProductQna> getByProductId(Long productId, Integer customerId, boolean checkSelf, boolean checkSecret, String qt, Pageable pageRequest);

}
