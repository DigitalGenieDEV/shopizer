package com.salesmanager.shop.model.catalog.product;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductReviewImage {
	private Long id;
	private String reviewImageUrl;
	private Long productReviewId;
}
