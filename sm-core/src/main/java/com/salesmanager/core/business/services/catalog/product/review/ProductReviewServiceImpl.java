package com.salesmanager.core.business.services.catalog.product.review;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.review.ProductReviewRepository;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.catalog.product.review.ProductReviewDTO;
import com.salesmanager.core.model.catalog.product.review.ReadProductReview;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.reference.language.Language;

@Service("productReviewService")
public class ProductReviewServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductReview> implements ProductReviewService {
	
	private ProductReviewRepository productReviewRepository;
	
	@Inject
	private ProductService productService;
	
	@Inject
	public ProductReviewServiceImpl(ProductReviewRepository productReviewRepository) {
			super(productReviewRepository);
			this.productReviewRepository = productReviewRepository;
	}

	@Override
	public List<ProductReview> getByCustomer(Customer customer) {
		return productReviewRepository.findByCustomer(customer.getId());
	}

	@Override
	public List<ProductReview> getByProduct(Product product) {
		return productReviewRepository.findByProduct(product.getId());
	}
	
	@Override
	public ProductReview getByProductAndCustomer(Long productId, Long customerId) {
		return productReviewRepository.findByProductAndCustomer(productId, customerId);
	}
	
	@Override
	public List<ProductReview> getByProduct(Product product, Language language) {
		return productReviewRepository.findByProduct(product.getId(), language.getId());
	}
	
	private void saveOrUpdate(ProductReview review) throws ServiceException {
		

		Validate.notNull(review,"ProductReview cannot be null");
		Validate.notNull(review.getProduct(),"ProductReview.product cannot be null");
		Validate.notNull(review.getCustomer(),"ProductReview.customer cannot be null");
		
		
		//refresh product
		Product product = productService.getById(review.getProduct().getId());
		
		//ajust product rating
		Integer count = 0;
		if(product.getProductReviewCount()!=null) {
			count = product.getProductReviewCount();
		}

		BigDecimal averageRating = product.getProductReviewAvg();
		if(averageRating==null) {
			averageRating = new BigDecimal(0);
		}
		
		//get reviews
		
		BigDecimal totalRating = averageRating.multiply(new BigDecimal(count));
		totalRating = totalRating.add(new BigDecimal(review.getReviewRating()));
		
		count = count + 1;
		double avg = totalRating.doubleValue() / count;
		
		product.setProductReviewAvg(new BigDecimal(avg));
		product.setProductReviewCount(count);
		super.save(review);
		
		productService.update(product);
		
		review.setProduct(product);
		
	}
	
	public void update(ProductReview review) throws ServiceException {
		this.saveOrUpdate(review);
	}
	
	public void create(ProductReview review) throws ServiceException {
		this.saveOrUpdate(review);
	}

	/* (non-Javadoc)
	 * @see com.salesmanager.core.business.services.catalog.product.review.ProductReviewService#getByProductNoObjects(com.salesmanager.core.model.catalog.product.Product)
	 */
	@Override
	public List<ProductReview> getByProductNoCustomers(Product product) {
		return productReviewRepository.findByProductNoCustomers(product.getId());
	}

	@Override
	public Page<ReadProductReview> listByKeyword(Product product, String keyword, Language lang, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return productReviewRepository.listByProductId(product.getId(), lang.getId(), keyword, pageRequest);
	}
	
	@Override
	public Page<ReadProductReview> listByCustomerId(Long customerId, Language lang, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return productReviewRepository.listByCustomerId(customerId, lang.getId(), pageRequest);
	}
	
	@Override
	public Integer getRecommendCountByCustomerId(Long customerId) {
		// TODO Auto-generated method stub
		return productReviewRepository.getRecommendCountByCustomerId(customerId);
	}

	@Override
	public Page<ReadProductReview> listByStore(Integer storeId, String keyword, Language lang, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return productReviewRepository.listByStore(storeId, lang.getId(), keyword, pageRequest);
	}

	@Override
	public ProductReview findById(Long id) {
		// TODO Auto-generated method stub
		return productReviewRepository.findOne(id);
	}

}
