package com.salesmanager.core.business.services.catalog.product.review;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.catalog.product.review.ProductReviewRecommendRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.review.ProductReviewRecommend;

@Service("productReviewRecommendService")
public class ProductReviewRecommendServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductReviewRecommend> implements ProductReviewRecommendService {
	
	private ProductReviewRecommendRepository productReviewRecommendRepository;
	
	public ProductReviewRecommendServiceImpl(ProductReviewRecommendRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.productReviewRecommendRepository = repository;
	}

	@Override
	public ProductReviewRecommend getByCutomerReview(Long customerId, Long reviewId) {
		// TODO Auto-generated method stub
		return productReviewRecommendRepository.getByCutomerReview(customerId, reviewId);
	}

	

}
