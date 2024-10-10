package com.salesmanager.shop.model.catalog.product;

import java.util.List;

import com.salesmanager.core.model.catalog.product.review.ProductReviewDTO;
import com.salesmanager.shop.model.entity.ReadableList;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductReviewList extends ReadableList {
	private static final long serialVersionUID = 1L;
	private List<ProductReviewDTO> data;
	private ReadableProductReviewStat reviewStat;
	private Integer recommendCount;
}
