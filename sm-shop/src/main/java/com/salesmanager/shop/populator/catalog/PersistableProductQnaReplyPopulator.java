package com.salesmanager.shop.populator.catalog;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.product.qna.ProductQnaService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaReply;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductQnaReply;

public class PersistableProductQnaReplyPopulator
		extends AbstractDataPopulator<PersistableProductQnaReply, ProductQnaReply> {
	private ProductQnaService productQnaService;
	
	@Override
	public ProductQnaReply populate(PersistableProductQnaReply source, ProductQnaReply target, MerchantStore store,
			Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		try {
			if(target == null) {
				target = new ProductQnaReply();
			}
			target.setTitle(source.getTitle());
			target.setDescription(source.getDescription());
			target.setLanguage(language);
			target.setName("-");
		
			ProductQna qna = productQnaService.getById(source.getId());
			target.setProductQna(qna);
			
			return target;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionException("Cannot populate ProductReview", e);
		}
	}

	@Override
	protected ProductQnaReply createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public ProductQnaService getProductQnaService() {
		return productQnaService;
	}

	public void setProductQnaService(ProductQnaService productQnaService) {
		this.productQnaService = productQnaService;
	}

}
