package com.salesmanager.core.business.services.catalog.product.review;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.review.ProductReviewRecommend;

public interface ProductReviewRecommendService extends SalesManagerEntityService<Long, ProductReviewRecommend> {

	ProductReviewRecommend getByCutomerReview(Long customerId, Long reviewId);


}
