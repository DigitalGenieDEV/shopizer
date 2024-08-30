package com.salesmanager.core.business.services.catalog.qna;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.salesmanager.core.model.catalog.qna.Qna;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;

public interface QnaService {

	public Page<Qna> getAllByStore(MerchantStore merchantStore, String sDate, String eDate, String category,
			String keywordType, String keyword, Language language, Pageable pageRequest);

}
