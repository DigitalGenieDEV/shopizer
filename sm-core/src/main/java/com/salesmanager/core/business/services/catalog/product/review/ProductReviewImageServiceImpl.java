package com.salesmanager.core.business.services.catalog.product.review;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.review.ProductReviewImageRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.review.ProductReviewImage;

@Service("productReviewImageService")
public class ProductReviewImageServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductReviewImage> implements ProductReviewImageService {
	
	private ProductReviewImageRepository productReviewImageRepository;
	
	public ProductReviewImageServiceImpl(ProductReviewImageRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.productReviewImageRepository = repository;
	}
	
	@Override
	public List<ProductReviewImage> getByProductReviewId(Long productReviewId) {
		// TODO Auto-generated method stub
		return productReviewImageRepository.getByProductReviewId(productReviewId);
	}
	
	@Override
	public void save(ProductReviewImage productReviewImage) throws ServiceException {
		// TODO Auto-generated method stub
		productReviewImageRepository.save(productReviewImage);
	}

	@Override
	public void deleteByProductReviewId(Long reviewId) {
		// TODO Auto-generated method stub
		productReviewImageRepository.deleteAllByProductReviewId(reviewId);
	}
}
