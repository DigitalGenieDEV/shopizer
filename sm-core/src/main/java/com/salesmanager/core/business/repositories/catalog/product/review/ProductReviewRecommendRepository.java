package com.salesmanager.core.business.repositories.catalog.product.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.review.ProductReviewRecommend;

public interface ProductReviewRecommendRepository extends JpaRepository<ProductReviewRecommend, Long> {
	
	@Query(value = "SELECT * "
			+ "FROM PRODUCT_REVIEW_RECOMMEND prr "
			+ "WHERE 1 = 1 "
			+ "AND CUSTOMERS_ID = ?1 "
			+ "AND PRODUCT_REVIEW_ID = ?2", nativeQuery = true)
	ProductReviewRecommend getByCutomerReview(Long customerId, Long reviewId);

}
