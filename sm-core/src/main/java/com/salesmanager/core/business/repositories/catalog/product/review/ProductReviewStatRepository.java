package com.salesmanager.core.business.repositories.catalog.product.review;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.review.ProductReviewStat;

public interface ProductReviewStatRepository extends JpaRepository<ProductReviewStat, Long> {
	@Query(value = "SELECT PRODUCT_ID "
			+ ", SUM(CASE WHEN REVIEWS_RATING = 1 THEN 1 ELSE 0 END) AS REVIEWS_RATING_COUNT1 "
			+ ", ROUND(SUM(CASE WHEN REVIEWS_RATING = 1 THEN 1 ELSE 0 END) / COUNT(*), 2) * 100 AS REVIEWS_RATING_PERCENT1 "
			+ ", SUM(CASE WHEN REVIEWS_RATING = 2 THEN 1 ELSE 0 END) AS REVIEWS_RATING_COUNT2 "
			+ ", ROUND(SUM(CASE WHEN REVIEWS_RATING = 2 THEN 1 ELSE 0 END) / COUNT(*), 2) * 100 AS REVIEWS_RATING_PERCENT2 "
			+ ", SUM(CASE WHEN REVIEWS_RATING = 3 THEN 1 ELSE 0 END) AS REVIEWS_RATING_COUNT3 "
			+ ", ROUND(SUM(CASE WHEN REVIEWS_RATING = 3 THEN 1 ELSE 0 END) / COUNT(*), 2) * 100 AS REVIEWS_RATING_PERCENT3 "
			+ ", SUM(CASE WHEN REVIEWS_RATING = 4 THEN 1 ELSE 0 END) AS REVIEWS_RATING_COUNT4 "
			+ ", ROUND(SUM(CASE WHEN REVIEWS_RATING = 4 THEN 1 ELSE 0 END) / COUNT(*), 2) * 100 AS REVIEWS_RATING_PERCENT4 "
			+ ", SUM(CASE WHEN REVIEWS_RATING = 5 THEN 1 ELSE 0 END) AS REVIEWS_RATING_COUNT5 "
			+ ", ROUND(SUM(CASE WHEN REVIEWS_RATING = 5 THEN 1 ELSE 0 END) / COUNT(*), 2) * 100 AS REVIEWS_RATING_PERCENT5 "
			+ ", SUM(REVIEWS_RATING) AS REVIEWS_RATING_SUM "
			+ ", COUNT(*) AS TOTAL_COUNT "
			+ ", ROUND(SUM(REVIEWS_RATING) / COUNT(*), 2) AS REVIEWS_RATING_AVG "
			+ "FROM PRODUCT_REVIEW pr "
			+ "WHERE 1 = 1 "
			+ "AND PRODUCT_ID = ?1", nativeQuery = true )
	ProductReviewStat getByProduct(Long id);
}
