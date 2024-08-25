package com.salesmanager.shop.model.catalog.product;

import javax.persistence.Column;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductReviewStat {
	private Long productId;
	private Long reviewRatingCount1;
	private Long reviewRatingPercent1;
	private Long reviewRatingCount2;
	private Long reviewRatingPercent2;
	private Long reviewRatingCount3;
	private Long reviewRatingPercent3;
	private Long reviewRatingCount4;
	private Long reviewRatingPercent4;
	private Long reviewRatingCount5;
	private Long reviewRatingPercent5;
	private Long reviewRatingSum;
	private Long totalCount;
	private Double reviewRatingAvg;
	public ReadableProductReviewStat(Long productId) {
		this.productId = productId;
		this.reviewRatingCount1 = Long.valueOf(0);
		this.reviewRatingPercent1 = Long.valueOf(0);
		this.reviewRatingCount2 = Long.valueOf(0);
		this.reviewRatingPercent2 = Long.valueOf(0);
		this.reviewRatingCount3 = Long.valueOf(0);
		this.reviewRatingPercent3 = Long.valueOf(0);
		this.reviewRatingCount4 = Long.valueOf(0);
		this.reviewRatingPercent4 = Long.valueOf(0);
		this.reviewRatingCount5 = Long.valueOf(0);
		this.reviewRatingPercent5 = Long.valueOf(0);
		this.reviewRatingSum = Long.valueOf(0);
		this.totalCount = Long.valueOf(0);
		this.reviewRatingAvg = Double.valueOf(0);
	}
	
	
}
