package com.salesmanager.shop.model.catalog.product;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import com.salesmanager.core.model.catalog.product.review.ProductReviewImage;
import com.salesmanager.shop.model.customer.ReadableCustomer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductReview implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long productId;
	private Long productReviewId;
	private String reviewDate;
	private int reviewsRating;
	private String reviewDescription;
	private Long customerId;
	private String firstName;
	private String lastName;
	private String productTitle;
	private int recommendCount;
	private int imageCount;
	private List<ProductReviewImage> images;
}
