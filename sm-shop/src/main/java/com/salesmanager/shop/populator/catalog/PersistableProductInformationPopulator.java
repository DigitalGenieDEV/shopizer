package com.salesmanager.shop.populator.catalog;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.information.ProductInformation;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.product.information.PersistableProductInformation;

@Component
public class PersistableProductInformationPopulator  extends AbstractDataPopulator<PersistableProductInformation, ProductInformation> {
	@Autowired
	private LanguageService languageService;
	public ProductInformation populate(PersistableProductInformation source, ProductInformation target, MerchantStore merchantStore,  Language language)
			throws ConversionException {
		
		

		try {
			List<Language> languages = new ArrayList<Language>();
			Validate.notNull(target, "ProductInformation target cannot be null");
			target.setId(source.getId());
			target.setDivision(source.getDivision());
			target.setDesc1(source.getDesc1());
			target.setDesc2(source.getDesc2());
			target.setDesc3(source.getDesc3());
			target.setMerchantStore(merchantStore);
			Language lang = languageService.getByCode(source.getLanguage());
			languages.add(lang);
			target.setDefaultLanguage(lang);
		
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}


	@Override
	protected ProductInformation createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
