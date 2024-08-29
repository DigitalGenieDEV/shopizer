package com.salesmanager.core.business.repositories.catalog.product.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.salesmanager.core.model.catalog.product.review.ProductReview;

public interface PageableProductReviewRepository extends PagingAndSortingRepository<ProductReview, Long> {
	
	@Query(name = "SELECT * "
			+ "FROM PRODUCT_REVIEW pr "
			+ "WHERE 1 = 1 "
			+ "AND pr.PRODUCT_ID = ?1 "
			+ "AND pr.PRODUCT_REVIEW_ID IN ( "
			+ "SELECT PRODUCT_REVIEW_ID "
			+ "FROM PRODUCT_REVIEW_DESCRIPTION "
			+ "WHERE 1 = 1 "
			+ "AND DESCRIPTION LIKE CONCAT('%', ?2, '%'))", nativeQuery = true)
	Page<ProductReview> getByProduct(Long productId, String keyword, Pageable pageable);
}
