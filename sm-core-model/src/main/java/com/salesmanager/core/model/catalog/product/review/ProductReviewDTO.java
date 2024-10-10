package com.salesmanager.core.model.catalog.product.review;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class ProductReviewDTO {
	private Long productId;
	private Long productReviewId;
	private Date reviewDate;
	private int reviewsRating;
	private String reviewDescription;
	private Long customerId;
	private String firstName;
	private String lastName;
	private String productTitle;
	private int recommendCount;
	private int imageCount;
	private int productQuantity;
	private int productPrice;
	private List<ProductReviewImage> images;
	public ProductReviewDTO() {

	}
	public ProductReviewDTO(Long productId, Long productReviewId, Date reviewDate, int reviewsRating,
			String reviewDescription, Long customerId, String firstName, String lastName, String productTitle,
			int recommendCount, int imageCount, int productQuantity, int productPrice) {
		this.productId = productId;
		this.productReviewId = productReviewId;
		this.reviewDate = reviewDate;
		this.reviewsRating = reviewsRating;
		this.reviewDescription = reviewDescription;
		this.customerId = customerId;
		this.firstName = firstName;
		this.lastName = lastName;
		this.productTitle = productTitle;
		this.recommendCount = recommendCount;
		this.imageCount = imageCount;
		this.productQuantity = productQuantity;
		this.productPrice = productPrice;
	}
	public ProductReviewDTO(ReadProductReview review) {
		// TODO Auto-generated constructor stub
		this.productId = review.getProduct_id();
		this.productReviewId = review.getProduct_review_id();
		this.reviewDate = review.getReview_date();
		this.reviewsRating = review.getReviews_rating();
		this.reviewDescription = review.getDescription();
		this.customerId = review.getCustomer_id();
		this.firstName = review.getFirst_name();
		this.lastName = review.getLast_name();
		this.productTitle = review.getProduct_title();
		this.recommendCount = review.getRecommend_count();
		this.imageCount = review.getImage_count();
		this.productQuantity = review.getProduct_quantity();
		this.productPrice = review.getProduct_price();
	}
}
