package com.salesmanager.shop.populator.catalog;

import java.text.SimpleDateFormat;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.qna.Qna;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProductQna;
import com.salesmanager.shop.model.catalog.qna.ReadableQna;

public class ReadableQnaPopulator extends AbstractDataPopulator<Qna, ReadableQna> {

	@Override
	public ReadableQna populate(Qna source, ReadableQna target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		try {
			if(target == null) {
				target = new ReadableQna();
			}
			target.setId(source.getId());
			target.setQnaDate(source.getQnaDate());
			target.setQnaTitle(source.getQnaTitle());
			target.setQnaDescription(source.getQnaDescription());
			target.setSecret(source.isSecret());
			target.setCategory(source.getQuestionType());
			target.setReplyStatus(source.getReplyStatus());
			target.setCustomerId(source.getCustomerId());
			target.setCustomerFirstName(source.getCustomerFirstName());
			target.setCustomerLastName(source.getCustomerLastName());
			target.setProductType(source.getProductType());
			target.setProductId(source.getProductId());
			target.setProductTitle(source.getProductTitle());
			return target;
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionException(e);
		}
	}

	@Override
	protected ReadableQna createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
