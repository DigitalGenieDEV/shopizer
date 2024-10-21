package com.salesmanager.shop.mapper.catalog;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService2;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValueDescription;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOptionValue;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;

@Component
public class PersistableProductOptionValueMapper
		implements Mapper<PersistableProductOptionValue, ProductOptionValue> {

	@Autowired
	private LanguageService languageService;
	
	@Autowired
	private ProductOptionService2 productOptionService2;

	ProductOptionValueDescription description(
			com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription description)
			throws Exception {
		Validate.notNull(description.getLanguage(), "description.language should not be null");
		ProductOptionValueDescription desc = new ProductOptionValueDescription();
		desc.setId(null);
		desc.setDescription(description.getDescription());
		desc.setName(description.getName());
		if(StringUtils.isBlank(desc.getName())) {
			desc.setName(description.getDescription());
		}
		if (description.getId() != null && description.getId().longValue() > 0) {
			desc.setId(description.getId());
		}
		Language lang = languageService.getByCode(description.getLanguage());
		desc.setLanguage(lang);
		return desc;
	}

	@Override
	public ProductOptionValue merge(PersistableProductOptionValue source, ProductOptionValue destination,
									MerchantStore store, Language language) {
		if (destination == null) {
			destination = new ProductOptionValue();
		}

		try {
			
			if(StringUtils.isBlank(source.getCode())) {
				if(!StringUtils.isBlank(destination.getCode())) {
					source.setCode(destination.getCode());
				}
			}

			if (!CollectionUtils.isEmpty(source.getDescriptions())) {
				for (com.salesmanager.shop.model.catalog.product.attribute.ProductOptionValueDescription desc : source
						.getDescriptions()) {
					ProductOptionValueDescription description = null;
					if (!CollectionUtils.isEmpty(destination.getDescriptions())) {
						for (ProductOptionValueDescription d : destination.getDescriptions()) {
							if (!StringUtils.isBlank(desc.getLanguage())
									&& desc.getLanguage().equals(d.getLanguage().getCode())) {
								
				            	  d.setDescription(desc.getDescription());
				            	  d.setName(desc.getName());
				            	  d.setTitle(desc.getTitle());
				            	  if(StringUtils.isBlank(d.getName())) {
				            		  d.setName(d.getDescription());
				            	  }
				            	  description = d;
				            	  break;

							}
						}
					} //else {
			          if(description == null) {
				          description = description(desc);
				          description.setProductOptionValue(destination);
				          destination.getDescriptions().add(description);
			          }
						//description = description(desc);
						//description.setProductOptionValue(destination);
					//}
					//destination.getDescriptions().add(description);
				}
			}
			destination.setProductOptionDisplayOnly(source.isDefaultValue());
			destination.setProductOptionValueImage(source.getImage());
			String optionValueCode = "";

			if(source.getCode().equals("")) {
				optionValueCode =  productOptionService2.getOptionValueCode();
			}else {
				if(source.getCode().indexOf("ETC")  != -1 || source.getCode().indexOf("BIGO") != -1) {
					optionValueCode =  productOptionService2.getOptionValueCode();
				}else {
					optionValueCode = source.getCode();
				}
			}
			

			System.out.println("optionValueCode"+optionValueCode);
			destination.setCode(optionValueCode );
			destination.setMerchantStore(store);
			destination.setProductOptionValueSortOrder(source.getSortOrder());


			return destination;
		} catch (Exception e) {
			throw new ServiceRuntimeException("Error while converting product option", e);
		}
	}

	@Override
	public ProductOptionValue convert(PersistableProductOptionValue source, MerchantStore store,
			Language language) {
		ProductOptionValue destination = new ProductOptionValue();
		return merge(source, destination, store, language);
	}


}