package com.salesmanager.shop.populator.erp;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.catalog.product.MaterialType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.Material;
import com.salesmanager.shop.model.catalog.MaterialDescription;
import com.salesmanager.shop.model.catalog.PersistableMaterial;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class PersistableMaterialPopulator extends AbstractDataPopulator<PersistableMaterial, com.salesmanager.core.model.catalog.product.Material> {

	@Autowired
	private LanguageService languageService;
	
	public com.salesmanager.core.model.catalog.product.Material populate(PersistableMaterial source, com.salesmanager.core.model.catalog.product.Material target)
			throws ConversionException {

		try {

			if(!CollectionUtils.isEmpty(source.getDescriptions())) {
				Set<com.salesmanager.core.model.catalog.product.MaterialDescription> descriptions = new HashSet<>();
				for(MaterialDescription desc  : source.getDescriptions()) {
					com.salesmanager.core.model.catalog.product.MaterialDescription description = new com.salesmanager.core.model.catalog.product.MaterialDescription();
					Language lang = languageService.getByCode(desc.getLanguage());
					if(lang==null) {
						throw new ConversionException("Language is null for code " + description.getLanguage() + " use language ISO code [en, fr ...]");
					}
					description.setLanguage(lang);
					description.setName(desc.getName());
					description.setTitle(desc.getTitle());
					description.setSubName(desc.getSubName());
					description.setSubTitle(desc.getSubTitle());
					descriptions.add(description);
				}
				target.setDescriptions(descriptions);
			}

			target.setCode(source.getCode());
			target.setSort(source.getSort());
			target.setType(StringUtils.isNotEmpty(source.getType())?
					MaterialType.valueOf(source.getType()) : null);
			target.setPrice(source.getPrice());
			target.setSubCode(source.getSubCode());
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public com.salesmanager.core.model.catalog.product.Material populate(PersistableMaterial source, com.salesmanager.core.model.catalog.product.Material target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected com.salesmanager.core.model.catalog.product.Material createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
