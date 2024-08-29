package com.salesmanager.shop.model.catalog.product;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductReviews {
	private List<ReadableProductReview> reviews;
	private ReadableProductReviewStat reviewStat;
}
