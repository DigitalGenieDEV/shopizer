package com.salesmanager.core.business.services.catalog.qna;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.catalog.qna.QnaRepository;
import com.salesmanager.core.model.catalog.qna.Qna;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

@Service("qnaService")
public class QnaServiceImpl implements QnaService {
	
	@Inject
	private QnaRepository qnaRepository;
	
	@Override
	public Page<Qna> getAllByStore(MerchantStore merchantStore, String sDate, String eDate, String category,
			String keywordType, String keyword, Language language, Pageable pageRequest) {
		// TODO Auto-generated method stub
		return qnaRepository.getAllByStore(merchantStore.getId(), sDate, eDate, category, keywordType, keyword, language.getId(), pageRequest);
	}
	
}
