package com.salesmanager.core.business.services.catalog.product.review;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.review.ProductReviewImage;

public interface ProductReviewImageService extends SalesManagerEntityService<Long, ProductReviewImage> {
	List<ProductReviewImage> getByProductReviewId(Long productReviewId);
	void save(ProductReviewImage reviewImage) throws ServiceException;
	void deleteByProductReviewId(Long id);
}
