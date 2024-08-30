package com.salesmanager.core.business.services.catalog.qna;

import javax.inject.Inject;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.catalog.qna.ProductQnaImageRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.qna.ProductQnaImage;

@Service("productQnaImageService")
public class ProductQnaImageServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductQnaImage> implements ProductQnaImageService {
	
	@Inject
	private ProductQnaImageRepository productQnaImageRepository;
	
	public ProductQnaImageServiceImpl(ProductQnaImageRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.productQnaImageRepository = repository;
	}

	@Override
	public void save(ProductQnaImage qnaImage) {
		this.productQnaImageRepository.save(qnaImage);
	}
	
}
