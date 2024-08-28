package com.salesmanager.core.business.services.catalog.product.qna;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.qna.ProductQnaRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.product.review.ProductReview;

@Service("productQnaService")
public class ProductQnaServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductQna> implements ProductQnaService {
	
	@Inject
	private ProductQnaRepository productQnaRepository;
	
	public ProductQnaServiceImpl(ProductQnaRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.productQnaRepository = repository;
	}

	@Override
	public void create(ProductQna qna) {
		// TODO Auto-generated method stub
		productQnaRepository.save(qna);
	}
	
	@Override
	public void update(ProductQna qna) {
		// TODO Auto-generated method stub
		this.productQnaRepository.save(qna);
	}

	@Override
	public Page<ProductQna> getProductQnaList(Long productId, Integer customerId, boolean checkSelf,
			boolean checkSecret, String category, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return this.productQnaRepository.getByProductId(productId, customerId, checkSelf, checkSecret, category, pageRequest);
	}

	@Override
	public void deleteById(ProductQna qna) {
		// TODO Auto-generated method stub
		this.productQnaRepository.delete(qna);
	}
}
