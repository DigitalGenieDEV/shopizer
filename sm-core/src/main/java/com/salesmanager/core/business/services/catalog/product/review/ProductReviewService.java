package com.salesmanager.core.business.services.catalog.product.review;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.catalog.product.review.ProductReviewDTO;
import com.salesmanager.core.model.catalog.product.review.ReadProductReview;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.reference.language.Language;

public interface ProductReviewService extends SalesManagerEntityService<Long, ProductReview> {
	
	List<ProductReview> getByCustomer(Customer customer);
	List<ProductReview> getByProduct(Product product);
	List<ProductReview> getByProduct(Product product, Language language);
	ProductReview getByProductAndCustomer(Long productId, Long customerId);
	List<ProductReview> getByProductNoCustomers(Product product);
	Page<ReadProductReview> listByKeyword(Product product, String keyword, Language lang, Pageable pageRequest);
	Page<ReadProductReview> listByStore(Integer id, String keyword, Language lang, Pageable pageRequest);
	ProductReview findById(Long id);
	Page<ReadProductReview> listByCustomerId(Long customerId, Language language, Integer tabIndex,  Pageable pageRequest);
	Integer getRecommendCountByCustomerId(Long customerId);
	ProductReview getByOrderProductAndCustomer(Long orderProductId, Long customerId);
}
