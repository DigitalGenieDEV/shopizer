package com.salesmanager.shop.model.catalog.product;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import com.salesmanager.shop.model.customer.ReadableCustomer;


public class ReadableProductReview extends ProductReviewEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ReadableCustomer customer;
	private Long recommendCount;
	private Set<ReadableProductReviewImage> reviewImages;
	
	public ReadableCustomer getCustomer() {
		return customer;
	}
	
	public void setCustomer(ReadableCustomer customer) {
		this.customer = customer;
	}

	public Long getRecommendCount() {
		return recommendCount;
	}

	public void setRecommendCount(Long recommendCount) {
		this.recommendCount = recommendCount;
	}

	public Set<ReadableProductReviewImage> getReviewImages() {
		return reviewImages;
	}

	public void setReviewImages(Set<ReadableProductReviewImage> reviewImages) {
		this.reviewImages = reviewImages;
	}

}
