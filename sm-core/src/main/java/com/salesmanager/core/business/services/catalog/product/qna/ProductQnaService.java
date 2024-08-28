package com.salesmanager.core.business.services.catalog.product.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.salesmanager.core.model.catalog.product.qna.ProductQna;

public interface ProductQnaService {
	ProductQna getById(Long qnaId);
	void create(ProductQna qna);
	void update(ProductQna qna);
	Page<ProductQna> getProductQnaList(Long productId, Integer customerId, boolean checkSelf, boolean checkSecret, String category, Pageable pageRequest);
	void deleteById(ProductQna qna);
}
