package com.salesmanager.core.business.repositories.catalog.product.review;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.catalog.product.review.ProductReviewDTO;
import com.salesmanager.core.model.catalog.product.review.ReadProductReview;
import com.salesmanager.core.model.reference.language.Language;

public interface ProductReviewRepository extends JpaRepository<ProductReview, Long> {
	
	@Query(value = "SELECT p.PRODUCT_ID "
			+ ", pr.PRODUCT_REVIEW_ID "
			+ ", pr.REVIEW_DATE "
			+ ", pr.REVIEWS_RATING "
			+ ", pr.DATE_CREATED "
			+ ", pr.DATE_MODIFIED "
			+ ", pr.UPDT_ID "
			+ ", prd.DESCRIPTION "
			+ ", c.CUSTOMER_ID "
			+ ", c.BILLING_FIRST_NAME AS FIRST_NAME "
			+ ", c.BILLING_LAST_NAME AS LAST_NAME "
			+ ", pd.TITLE AS PRODUCT_TITLE "
			+ ", pd.DESCRIPTION AS PRODUCT_DESCRIPTION "
			+ ", IFNULL(prr.RECOMMEND_COUNT, 0) AS RECOMMEND_COUNT "
			+ ", IFNULL(pri.IMAGE_COUNT, 0) AS IMAGE_COUNT "
			+ "FROM PRODUCT_REVIEW pr LEFT JOIN PRODUCT_REVIEW_DESCRIPTION prd ON pr.PRODUCT_REVIEW_ID = prd.PRODUCT_REVIEW_ID "
			+ "LEFT JOIN CUSTOMER c ON pr.CUSTOMERS_ID = c.CUSTOMER_ID "
			+ "LEFT JOIN PRODUCT p ON pr.PRODUCT_ID = p.PRODUCT_ID "
			+ "LEFT JOIN PRODUCT_DESCRIPTION pd ON p.PRODUCT_ID = pd.PRODUCT_ID "
			+ "LEFT JOIN ( "
			+ "SELECT PRODUCT_REVIEW_ID "
			+ ", COUNT(*) AS RECOMMEND_COUNT "
			+ "FROM PRODUCT_REVIEW_RECOMMEND "
			+ "WHERE 1 = 1 "
			+ "AND ACTIVE = TRUE "
			+ "GROUP BY PRODUCT_REVIEW_ID "
			+ ") prr ON pr.PRODUCT_REVIEW_ID = prr.PRODUCT_REVIEW_ID "
			+ "LEFT JOIN ( "
			+ "SELECT PRODUCT_REVIEW_ID "
			+ ", COUNT(*) IMAGE_COUNT "
			+ "FROM PRODUCT_REVIEW_IMAGE "
			+ "GROUP BY PRODUCT_REVIEW_ID "
			+ ") pri ON pr.PRODUCT_REVIEW_ID = pri.PRODUCT_REVIEW_ID "
			+ "WHERE 1 = 1 "
			+ "AND pr.PRODUCT_ID = ?1 "
			+ "AND pd.LANGUAGE_ID = ?2 "
			+ "AND prd.DESCRIPTION LIKE CONCAT('%', ?3, '%') ",
			countQuery = "SELECT COUNT(*) "
					+ "FROM PRODUCT_REVIEW pr LEFT JOIN PRODUCT_REVIEW_DESCRIPTION prd ON pr.PRODUCT_REVIEW_ID = prd.PRODUCT_REVIEW_ID "
					+ "LEFT JOIN CUSTOMER c ON pr.CUSTOMERS_ID = c.CUSTOMER_ID "
					+ "LEFT JOIN PRODUCT p ON pr.PRODUCT_ID = p.PRODUCT_ID "
					+ "LEFT JOIN PRODUCT_DESCRIPTION pd ON p.PRODUCT_ID = pd.PRODUCT_ID "
					+ "LEFT JOIN ( "
					+ "SELECT PRODUCT_REVIEW_ID "
					+ ", COUNT(*) AS RECOMMEND_COUNT "
					+ "FROM PRODUCT_REVIEW_RECOMMEND "
					+ "WHERE 1 = 1 "
					+ "AND ACTIVE = TRUE "
					+ "GROUP BY PRODUCT_REVIEW_ID "
					+ ") prr ON pr.PRODUCT_REVIEW_ID = prr.PRODUCT_REVIEW_ID "
					+ "LEFT JOIN ( "
					+ "SELECT PRODUCT_REVIEW_ID, "
					+ "COUNT(*) IMAGE_COUNT "
					+ "FROM PRODUCT_REVIEW_IMAGE "
					+ "GROUP BY PRODUCT_REVIEW_ID "
					+ ") pri ON pr.PRODUCT_REVIEW_ID = pri.PRODUCT_REVIEW_ID "
					+ "WHERE 1 = 1 "
					+ "AND pr.PRODUCT_ID = ?1 "
					+ "AND pd.LANGUAGE_ID = ?2 "
					+ "AND prd.DESCRIPTION LIKE CONCAT('%', ?3, '%') ", nativeQuery = true)
	Page<ReadProductReview> listByProductId(Long productId, Integer languageId, String keyword, Pageable pageable);
	
	@Query(value = "SELECT p.PRODUCT_ID \r\n"
			+ "     , pr.PRODUCT_REVIEW_ID \r\n"
			+ "     , pr.REVIEW_DATE \r\n"
			+ "     , pr.REVIEWS_RATING \r\n"
			+ "     , pr.DATE_CREATED \r\n"
			+ "     , pr.DATE_MODIFIED \r\n"
			+ "     , pr.UPDT_ID \r\n"
			+ "     , prd.DESCRIPTION \r\n"
			+ "     , c.CUSTOMER_ID \r\n"
			+ "     , c.BILLING_FIRST_NAME AS FIRST_NAME \r\n"
			+ "     , c.BILLING_LAST_NAME AS LAST_NAME \r\n"
			+ "     , pd.TITLE AS PRODUCT_TITLE \r\n"
			+ "     , pd.DESCRIPTION AS PRODUCT_DESCRIPTION \r\n"
			+ "     , IFNULL(prr.RECOMMEND_COUNT, 0) AS RECOMMEND_COUNT \r\n"
			+ "     , IFNULL(pri.IMAGE_COUNT, 0) AS IMAGE_COUNT \r\n"
			+ "     , op.PRODUCT_QUANTITY \r\n"
			+ "     , opp.PRODUCT_PRICE \r\n"
			+ "FROM CUSTOMER c LEFT JOIN PRODUCT_REVIEW pr ON c.CUSTOMER_ID  = pr.CUSTOMERS_ID \r\n"
			+ "     LEFT JOIN PRODUCT_REVIEW_DESCRIPTION prd ON pr.PRODUCT_REVIEW_ID = prd.PRODUCT_REVIEW_ID \r\n"
			+ "     LEFT JOIN PRODUCT p ON pr.PRODUCT_ID = p.PRODUCT_ID \r\n"
			+ "     LEFT JOIN PRODUCT_DESCRIPTION pd ON p.PRODUCT_ID = pd.PRODUCT_ID \r\n"
			+ "     LEFT JOIN ORDER_PRODUCT op ON p.PRODUCT_ID = op.PRODUCT_ID \r\n"
			+ "     LEFT JOIN ORDER_PRODUCT_PRICE opp ON op.ORDER_PRODUCT_ID = opp.ORDER_PRODUCT_ID \r\n"
			+ "     LEFT JOIN ( \r\n"
			+ "       SELECT PRODUCT_REVIEW_ID \r\n"
			+ "            , COUNT(*) AS RECOMMEND_COUNT \r\n"
			+ "       FROM PRODUCT_REVIEW_RECOMMEND \r\n"
			+ "       WHERE 1 = 1 \r\n"
			+ "             AND ACTIVE = TRUE \r\n"
			+ "       GROUP BY PRODUCT_REVIEW_ID \r\n"
			+ "     ) prr ON pr.PRODUCT_REVIEW_ID = prr.PRODUCT_REVIEW_ID \r\n"
			+ "     LEFT JOIN ( \r\n"
			+ "       SELECT PRODUCT_REVIEW_ID \r\n"
			+ "            , COUNT(*) IMAGE_COUNT \r\n"
			+ "     FROM PRODUCT_REVIEW_IMAGE \r\n"
			+ "     GROUP BY PRODUCT_REVIEW_ID \r\n"
			+ "     ) pri ON pr.PRODUCT_REVIEW_ID = pri.PRODUCT_REVIEW_ID \r\n"
			+ "WHERE 1 = 1 \r\n"
			+ "     AND c.CUSTOMER_ID = ?1 \r\n"
			+ "     AND pd.LANGUAGE_ID = ?2 \r\n"
			+ "ORDER BY DATE_CREATED DESC \r\n",
		   countQuery = "SELECT COUNT(*) \r\n"
		   		+ "FROM CUSTOMER c LEFT JOIN PRODUCT_REVIEW pr ON c.CUSTOMER_ID  = pr.CUSTOMERS_ID \r\n"
		   		+ "     LEFT JOIN PRODUCT_REVIEW_DESCRIPTION prd ON pr.PRODUCT_REVIEW_ID = prd.PRODUCT_REVIEW_ID \r\n"
		   		+ "     LEFT JOIN PRODUCT p ON pr.PRODUCT_ID = p.PRODUCT_ID \r\n"
		   		+ "     LEFT JOIN PRODUCT_DESCRIPTION pd ON p.PRODUCT_ID = pd.PRODUCT_ID \r\n"
		   		+ "     LEFT JOIN ( \r\n"
		   		+ "       SELECT PRODUCT_REVIEW_ID \r\n"
		   		+ "            , COUNT(*) AS RECOMMEND_COUNT \r\n"
		   		+ "       FROM PRODUCT_REVIEW_RECOMMEND \r\n"
		   		+ "       WHERE 1 = 1 \r\n"
		   		+ "             AND ACTIVE = TRUE \r\n"
		   		+ "       GROUP BY PRODUCT_REVIEW_ID \r\n"
		   		+ "     ) prr ON pr.PRODUCT_REVIEW_ID = prr.PRODUCT_REVIEW_ID \r\n"
		   		+ "     LEFT JOIN ( \r\n"
		   		+ "       SELECT PRODUCT_REVIEW_ID \r\n"
		   		+ "            , COUNT(*) IMAGE_COUNT \r\n"
		   		+ "     FROM PRODUCT_REVIEW_IMAGE \r\n"
		   		+ "     GROUP BY PRODUCT_REVIEW_ID \r\n"
		   		+ "     ) pri ON pr.PRODUCT_REVIEW_ID = pri.PRODUCT_REVIEW_ID \r\n"
		   		+ "WHERE 1 = 1\r\n"
		   		+ "     AND c.CUSTOMER_ID = ?1\r\n"
		   		+ "     AND pd.LANGUAGE_ID = ?2", nativeQuery = true)
	Page<ReadProductReview> listByCustomerId(Long customerId, Integer languageId, Pageable pageable);
	
	@Query("select p from ProductReview p join fetch p.customer pc join fetch p.product pp join fetch pp.merchantStore ppm left join fetch p.descriptions pd where p.id = ?1")
	ProductReview findOne(Long id);
	
	@Query("select p from ProductReview p join fetch p.customer pc join fetch p.product pp join fetch pp.merchantStore ppm left join fetch p.descriptions pd where pc.id = ?1")
	List<ProductReview> findByCustomer(Long customerId);
	
	@Query("select p from ProductReview p left join fetch p.descriptions pd join fetch p.customer pc join fetch pc.merchantStore pcm left join fetch pc.defaultLanguage pcl left join fetch pc.attributes pca left join fetch pca.customerOption pcao left join fetch pca.customerOptionValue pcav left join fetch pcao.descriptions pcaod left join fetch pcav.descriptions pcavd join fetch p.product pp join fetch pp.merchantStore ppm  join fetch p.product pp join fetch pp.merchantStore ppm left join fetch p.descriptions pd where pp.id = ?1")
	List<ProductReview> findByProduct(Long productId);
	
	@Query("select p from ProductReview p join fetch p.product pp join fetch pp.merchantStore ppm  where pp.id = ?1")
	List<ProductReview> findByProductNoCustomers(Long productId);
	
	@Query("select p from ProductReview p left join fetch p.descriptions pd join fetch p.customer pc join fetch pc.merchantStore pcm left join fetch pc.defaultLanguage pcl left join fetch pc.attributes pca left join fetch pca.customerOption pcao left join fetch pca.customerOptionValue pcav left join fetch pcao.descriptions pcaod left join fetch pcav.descriptions pcavd join fetch p.product pp join fetch pp.merchantStore ppm  join fetch p.product pp join fetch pp.merchantStore ppm left join fetch p.descriptions pd where pp.id = ?1 and pd.language.id =?2")
	List<ProductReview> findByProduct(Long productId, Integer languageId);
	
	@Query("select p from ProductReview p left join fetch p.descriptions pd join fetch p.customer pc join fetch pc.merchantStore pcm left join fetch pc.defaultLanguage pcl left join fetch pc.attributes pca left join fetch pca.customerOption pcao left join fetch pca.customerOptionValue pcav left join fetch pcao.descriptions pcaod left join fetch pcav.descriptions pcavd join fetch p.product pp join fetch pp.merchantStore ppm  join fetch p.product pp join fetch pp.merchantStore ppm left join fetch p.descriptions pd where pp.id = ?1 and pc.id = ?2")
	ProductReview findByProductAndCustomer(Long productId, Long customerId);
	
	@Query(value = "WITH PR AS ( "
			+ "SELECT 'PRODUCT' AS product_type "
			+ ", p.PRODUCT_ID  "
			+ ", pr.PRODUCT_REVIEW_ID "
			+ ", pr.REVIEW_DATE  "
			+ ", pr.REVIEWS_RATING  "
			+ ", prd.DESCRIPTION  "
			+ ", c.CUSTOMER_ID  "
			+ ", c.BILLING_FIRST_NAME AS FIRST_NAME "
			+ ", c.BILLING_LAST_NAME AS LAST_NAME "
			+ ", pd.TITLE AS PRODUCT_TITLE "
			+ ", pd.DESCRIPTION AS PRODUCT_DESCRIPTION "
			+ ", IFNULL(prr.RECOMMEND_COUNT, 0) AS RECOMMEND_COUNT "
			+ ", IFNULL(pri.IMAGE_COUNT, 0) AS IMAGE_COUNT "
			+ "FROM PRODUCT_REVIEW pr LEFT JOIN PRODUCT_REVIEW_DESCRIPTION prd ON pr.PRODUCT_REVIEW_ID = prd.PRODUCT_REVIEW_ID  "
			+ "LEFT JOIN PRODUCT p ON pr.PRODUCT_ID = p.PRODUCT_ID  "
			+ "LEFT JOIN PRODUCT_DESCRIPTION pd ON p.PRODUCT_ID = pd.PRODUCT_ID "
			+ "LEFT JOIN CUSTOMER c ON pr.CUSTOMERS_ID = c.CUSTOMER_ID "
			+ "LEFT JOIN ( "
			+ "SELECT PRODUCT_REVIEW_ID "
			+ ", COUNT(*) AS RECOMMEND_COUNT "
			+ "FROM PRODUCT_REVIEW_RECOMMEND  "
			+ "WHERE 1 = 1 "
			+ "AND ACTIVE = TRUE "
			+ "GROUP BY PRODUCT_REVIEW_ID  "
			+ ") prr ON pr.PRODUCT_REVIEW_ID = prr.PRODUCT_REVIEW_ID "
			+ "LEFT JOIN ( "
			+ "SELECT PRODUCT_REVIEW_ID  "
			+ ", COUNT(*) IMAGE_COUNT "
			+ "FROM PRODUCT_REVIEW_IMAGE   "
			+ "GROUP BY PRODUCT_REVIEW_ID  "
			+ ") pri ON pr.PRODUCT_REVIEW_ID = pri.PRODUCT_REVIEW_ID "
			+ "WHERE 1 = 1 "
			+ "AND p.MERCHANT_ID = ?1 "
			+ "AND pd.LANGUAGE_ID = ?2 "
			+ "AND prd.DESCRIPTION LIKE CONCAT('%', ?3, '%')  "
			+ ") "
			+ "SELECT * "
			+ "FROM PR ", 
			countQuery = "WITH PR AS ( "
					+ "SELECT 'PRODUCT' AS product_type "
					+ ", p.PRODUCT_ID  "
					+ ", pr.PRODUCT_REVIEW_ID "
					+ ", pr.REVIEW_DATE  "
					+ ", pr.REVIEWS_RATING  "
					+ ", prd.DESCRIPTION  "
					+ ", c.CUSTOMER_ID  "
					+ ", c.BILLING_FIRST_NAME AS FIRST_NAME "
					+ ", c.BILLING_LAST_NAME AS LAST_NAME "
					+ ", pd.TITLE AS PRODUCT_TITLE "
					+ ", pd.DESCRIPTION AS PRODUCT_DESCRIPTION "
					+ ", IFNULL(prr.RECOMMEND_COUNT, 0) AS RECOMMEND_COUNT "
					+ ", IFNULL(pri.IMAGE_COUNT, 0) AS IMAGE_COUNT "
					+ "FROM PRODUCT_REVIEW pr LEFT JOIN PRODUCT_REVIEW_DESCRIPTION prd ON pr.PRODUCT_REVIEW_ID = prd.PRODUCT_REVIEW_ID  "
					+ "LEFT JOIN PRODUCT p ON pr.PRODUCT_ID = p.PRODUCT_ID  "
					+ "LEFT JOIN PRODUCT_DESCRIPTION pd ON p.PRODUCT_ID = pd.PRODUCT_ID "
					+ "LEFT JOIN CUSTOMER c ON pr.CUSTOMERS_ID = c.CUSTOMER_ID "
					+ "LEFT JOIN ( "
					+ "SELECT PRODUCT_REVIEW_ID "
					+ ", COUNT(*) AS RECOMMEND_COUNT "
					+ "FROM PRODUCT_REVIEW_RECOMMEND  "
					+ "WHERE 1 = 1 "
					+ "AND ACTIVE = TRUE "
					+ "GROUP BY PRODUCT_REVIEW_ID  "
					+ ") prr ON pr.PRODUCT_REVIEW_ID = prr.PRODUCT_REVIEW_ID "
					+ "LEFT JOIN ( "
					+ "SELECT PRODUCT_REVIEW_ID  "
					+ ", COUNT(*) IMAGE_COUNT "
					+ "FROM PRODUCT_REVIEW_IMAGE   "
					+ "GROUP BY PRODUCT_REVIEW_ID  "
					+ ") pri ON pr.PRODUCT_REVIEW_ID = pri.PRODUCT_REVIEW_ID "
					+ "WHERE 1 = 1 "
					+ "AND p.MERCHANT_ID = ?1 "
					+ "AND pd.LANGUAGE_ID = ?2 "
					+ "AND prd.DESCRIPTION LIKE CONCAT('%', ?3, '%')  "
					+ ") "
					+ "SELECT COUNT(*) "
					+ "FROM PR ", nativeQuery = true)
	Page<ReadProductReview> listByStore(Integer storeId, Integer languageId, String keyword, Pageable pageRequest);
	
	@Query(value = "SELECT COUNT(*) AS RECOMMEND_COUNT \r\n"
			+ "FROM PRODUCT_REVIEW pr JOIN PRODUCT_REVIEW_RECOMMEND prr ON pr.PRODUCT_REVIEW_ID = prr.PRODUCT_REVIEW_ID\r\n"
			+ "WHERE 1 = 1\r\n"
			+ "      AND pr.CUSTOMERS_ID = ?1", nativeQuery = true)
	Integer getRecommendCountByCustomerId(Long customerId);
}
