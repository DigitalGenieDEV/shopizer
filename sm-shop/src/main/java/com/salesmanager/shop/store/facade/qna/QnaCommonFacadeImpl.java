package com.salesmanager.shop.store.facade.qna;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.qna.ProductQnaService;
import com.salesmanager.core.business.services.catalog.qna.ProductQnaImageService;
import com.salesmanager.core.business.services.catalog.qna.QnaService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaReply;
import com.salesmanager.core.model.catalog.qna.ProductQnaImage;
import com.salesmanager.core.model.catalog.qna.Qna;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductQna;
import com.salesmanager.shop.model.catalog.product.PersistableProductQnaReply;
import com.salesmanager.shop.model.catalog.product.ReadableProductQna;
import com.salesmanager.shop.model.catalog.product.ReadableProductQnaList;
import com.salesmanager.shop.model.catalog.qna.ReadableQna;
import com.salesmanager.shop.model.catalog.qna.ReadableQnaList;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.populator.catalog.PersistableProductQnaPopulator;
import com.salesmanager.shop.populator.catalog.PersistableProductQnaReplyPopulator;
import com.salesmanager.shop.populator.catalog.ReadableProductQnaPopulator;
import com.salesmanager.shop.populator.catalog.ReadableQnaPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.qna.facade.QnaCommonFacade;
import com.salesmanager.shop.utils.ImageFilePath;

@Service("qnaCommonFacade")
public class QnaCommonFacadeImpl implements QnaCommonFacade {
	
	@Inject
	private ContentFacade contentFacade;
	
	@Inject
	private CustomerService customerService;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;
	
	@Inject
	private LanguageService languageService;
	
	@Inject
	private PricingService pricingService;
	
	@Inject
	private ProductService productService;
	
	@Inject
	private ProductQnaService productQnaService;
	
	@Inject
	private ProductQnaImageService productQnaImageService;
	
	@Inject
	private QnaService qnaService;
	
	@Override
	public void saveOrUpdateQna(@Valid PersistableProductQna persistableQna, MerchantStore store, Language language, List<MultipartFile> qnaImages) throws Exception {
		// TODO Auto-generated method stub
		PersistableProductQnaPopulator populator = new PersistableProductQnaPopulator();
		populator.setLanguageService(languageService);
		populator.setCustomerService(customerService);
		populator.setProductService(productService);
		
		ProductQna qna = new ProductQna();
		populator.populate(persistableQna, qna, store, language);

		if (persistableQna.getId() == null) {
			productQnaService.create(qna);
		} else {
			productQnaService.update(qna);
		}
		
//		persistableQna.setId(qna.getId());
		
		if(qnaImages != null) {
			for(MultipartFile file : qnaImages) {
				ProductQnaImage qnaImage = new ProductQnaImage();
				ContentFile f = new ContentFile();
				f.setContentType(file.getContentType());
				f.setName(file.getOriginalFilename());
				try {
					f.setFile(file.getBytes());
				} catch (IOException e) {
					throw new ServiceRuntimeException("Error while getting file bytes");
				}
				String fileName = contentFacade.addLibraryFile(f, store.getCode(), FileContentType.valueOf(persistableQna.getFileContentType()));
				qnaImage.setImageUrl(imageUtils.buildLibraryFileUtils(store, fileName, persistableQna.getFileContentType()));
				qnaImage.setProductQna(qna);
				productQnaImageService.save(qnaImage);
			}
		}
	}

	@Override
	public void saveReply(Long qnaId, @Valid PersistableProductQnaReply persistableReply, MerchantStore merchantStore, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		ProductQna qna = productQnaService.getById(qnaId);
		
		ProductQnaReply reply = new ProductQnaReply();
		PersistableProductQnaReplyPopulator populator = new PersistableProductQnaReplyPopulator();
		populator.setProductQnaService(productQnaService);
		persistableReply.setId(qnaId);
		populator.populate(persistableReply, reply, merchantStore, language);
		
		qna.setReply(reply);
		productQnaService.update(qna);
	}

	@Override
	public ReadableProductQna getProductQna(Long qnaId, MerchantStore store, Language lang) throws ConversionException {
		// TODO Auto-generated method stub
		ProductQna qna = productQnaService.getById(qnaId);
		ReadableProductQna readableQna = new ReadableProductQna();
		if(qna != null) {
			ReadableProductQnaPopulator populator = new ReadableProductQnaPopulator();
			populator.setPricingService(pricingService);
			populator.setImageUtils(imageUtils);
			populator.populate(qna, readableQna, store, lang);
		}
		
		return readableQna;
	}

	@Override
	public ReadableProductQnaList getProductQnaList(Long productId, boolean checkSecret, boolean checkSelf, Integer customerId,
			String category, Pageable pageRequest, MerchantStore store, Language lang) {
		// TODO Auto-generated method stub
		QuestionType qt = null;
		try {
			qt = QuestionType.valueOf(category);
			category = qt.name();
		} catch (Exception e) {
			// TODO: handle exception
			category = null;
		}
		
		Page<ProductQna> productQnaList = productQnaService.getProductQnaList(productId, customerId, checkSelf, checkSecret, category, pageRequest);
		ReadableProductQnaList readableProductQnaList = new ReadableProductQnaList();
		List<ReadableProductQna> readableQnaList = new ArrayList<ReadableProductQna>();
		
		if(!CollectionUtils.isEmpty(productQnaList.getContent())) {
			for(ProductQna qna : productQnaList) {
				ReadableProductQnaPopulator populator = new ReadableProductQnaPopulator();
				populator.setPricingService(pricingService);
				populator.setImageUtils(imageUtils);
				ReadableProductQna readableQna = new ReadableProductQna();
				try {
					populator.populate(qna, readableQna, store, lang);
				} catch (ConversionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				readableQnaList.add(readableQna);
			}
		}
		
		readableProductQnaList.setData(readableQnaList);
		readableProductQnaList.setRecordsTotal(productQnaList.getTotalElements());
		readableProductQnaList.setTotalPages(productQnaList.getTotalPages());
		readableProductQnaList.setNumber(productQnaList.getSize());
		readableProductQnaList.setRecordsFiltered(productQnaList.getSize());
		
		return readableProductQnaList;
	}

	@Override
	public void deleteQna(Long qnaId) {
		// TODO Auto-generated method stub
		ProductQna qna = productQnaService.getById(qnaId);
		this.productQnaService.deleteById(qna);
	}

	@Override
	public ReadableQnaList getAllByStore(Pageable pageRequest, String sDate, String eDate, String category,
			String keywordType, String keyword, MerchantStore merchantStore, Language language) {
		// TODO Auto-generated method stub
		
		QuestionType qt = null;
		try {
			qt = QuestionType.valueOf(category);
			category = qt.name();
		} catch (Exception e) {
			// TODO: handle exception
			category = null;
		}
		
		Page<Qna> qnaList = this.qnaService.getAllByStore(merchantStore, sDate, eDate, category, keywordType, keyword, language, pageRequest);
		
		ReadableQnaList readableQnaList = new ReadableQnaList();
		List<ReadableQna> readableQnas = new ArrayList<ReadableQna>();
		
		if(!CollectionUtils.isEmpty(qnaList.getContent())) {
			for(Qna qna : qnaList) {
				ReadableQnaPopulator populator = new ReadableQnaPopulator();
				ReadableQna readableQna = new ReadableQna();
				try {
					populator.populate(qna, readableQna, merchantStore, language);
				} catch (ConversionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				readableQnas.add(readableQna);
			}
		}
		readableQnaList.setData(readableQnas);
		readableQnaList.setRecordsTotal(qnaList.getTotalElements());
		readableQnaList.setTotalPages(qnaList.getTotalPages());
		readableQnaList.setNumber(qnaList.getSize());
		readableQnaList.setRecordsFiltered(qnaList.getSize());
		return readableQnaList;
	}

}
