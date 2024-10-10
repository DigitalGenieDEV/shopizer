package com.salesmanager.core.model.catalog.product.review;

import java.util.Date;

public interface ReadProductReview {
	String getProduct_type();
	Long getProduct_id();
	Long getProduct_review_id();
	Date getReview_date();
	int getReviews_rating();
	String getDescription();
	Long getCustomer_id();
	String getFirst_name();
	String getLast_name();
	String getProduct_title();
	int getRecommend_count();
	int getImage_count();
	int getProduct_quantity();
	int getProduct_price();
}
