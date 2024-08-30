package com.salesmanager.shop.store.controller.qna.facade;

import java.util.List;

import javax.validation.Valid;

import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductQna;
import com.salesmanager.shop.model.catalog.product.PersistableProductQnaReply;
import com.salesmanager.shop.model.catalog.product.ReadableProductQna;
import com.salesmanager.shop.model.catalog.product.ReadableProductQnaList;
import com.salesmanager.shop.model.catalog.qna.ReadableQnaList;

public interface QnaCommonFacade {
	public ReadableProductQnaList getProductQnaList(Long productId, boolean checkSecret, boolean checkSelf, Integer customerId, String category, Pageable pageRequest, MerchantStore store, Language lang);
	public ReadableProductQna getProductQna(Long qnaId, MerchantStore store, Language lang) throws ConversionException;
	public void saveOrUpdateQna(PersistableProductQna persistableQna, MerchantStore store, Language language, List<MultipartFile> qnaImages) throws Exception;
	public void deleteQna(Long qnaId);
	
	public void saveReply(Long qnaId, @Valid PersistableProductQnaReply reply, MerchantStore merchantStore, Language language) throws Exception;
	public ReadableQnaList getAllByStore(Pageable pageRequest, String sDate, String eDate, String category, String keywordType, String keyword, MerchantStore merchantStore, Language language);
	
}
