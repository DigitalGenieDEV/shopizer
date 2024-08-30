package com.salesmanager.core.business.services.catalog.product.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.catalog.product.review.ProductReviewStatRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.review.ProductReviewStat;

@Service("productReviewStatService")
public class ProductReviewStatServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductReviewStat> implements ProductReviewStatService {
	
	private ProductReviewStatRepository productReviewStatRepository;
	
	
	public ProductReviewStatServiceImpl(ProductReviewStatRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.productReviewStatRepository = repository;
	}

	@Override
	public ProductReviewStat getByProduct(Product product) {
		// TODO Auto-generated method stub
		return productReviewStatRepository.getByProduct(product.getId());
	}
	
	

}
