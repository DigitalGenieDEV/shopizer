package com.salesmanager.core.model.catalog.product.review;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class ProductReviewStat extends SalesManagerEntity<Long, ProductReviewStat> {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "REVIEWS_RATING_COUNT1")
	private Long reviewRatingCount1;
	
	@Column(name = "REVIEWS_RATING_PERCENT1")
	private Long reviewRatingPercent1;
	
	@Column(name = "REVIEWS_RATING_COUNT2")
	private Long reviewRatingCount2;
	
	@Column(name = "REVIEWS_RATING_PERCENT2")
	private Long reviewRatingPercent2;
	
	@Column(name = "REVIEWS_RATING_COUNT3")
	private Long reviewRatingCount3;
	
	@Column(name = "REVIEWS_RATING_PERCENT3")
	private Long reviewRatingPercent3;
	
	@Column(name = "REVIEWS_RATING_COUNT4")
	private Long reviewRatingCount4;
	
	@Column(name = "REVIEWS_RATING_PERCENT4")
	private Long reviewRatingPercent4;
	
	@Column(name = "REVIEWS_RATING_COUNT5")
	private Long reviewRatingCount5;
	
	@Column(name = "REVIEWS_RATING_PERCENT5")
	private Long reviewRatingPercent5;
	
	@Column(name = "REVIEWS_RATING_SUM")
	private Long reviewRatingSum;
	
	@Column(name = "TOTAL_COUNT")
	private Long totalCount;
	
	@Column(name = "REVIEWS_RATING_AVG")
	private Double reviewRatingAvg;

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return productId;
	}

	@Override
	public void setId(Long id) {
		// TODO Auto-generated method stub
		this.productId = id;
	}
}
