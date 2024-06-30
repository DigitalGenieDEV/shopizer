package com.salesmanager.shop.mapper.catalog;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableAnnouncement;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component
public class PersistableProductAnnouncementAttributeMapper implements Mapper<PersistableAnnouncement, ProductAnnouncementAttribute> {

	@Inject
	private ProductOptionService productOptionService;
	@Inject
	private ProductOptionValueService productOptionValueService;
	@Inject
	private ProductService productService;
	@Inject
	private PersistableProductOptionValueMapper persistableProductOptionValueMapper;
	
	@Override
	public ProductAnnouncementAttribute convert(PersistableAnnouncement source, MerchantStore store, Language language) {
		ProductAnnouncementAttribute attribute = new ProductAnnouncementAttribute();
		return merge(source,attribute,store,language);
	}

	@Override
	public ProductAnnouncementAttribute merge(PersistableAnnouncement source, ProductAnnouncementAttribute destination,
								  MerchantStore store, Language language) {

		
		ProductOption productOption = null;
		
		if(!StringUtils.isBlank(source.getOption().getCode())) {
			productOption = productOptionService.getByCode(store, source.getOption().getCode());
		} else {
			Validate.notNull(source.getOption().getId(),"Product option id is null");
			productOption = productOptionService.getById(source.getOption().getId());
		}

		if(productOption==null) {
			throw new ConversionRuntimeException("Product option id " + source.getOption().getId() + " does not exist");
		}
		
		ProductOptionValue productOptionValue = null;
		
		if(!StringUtils.isBlank(source.getValue().getCode())) {
			productOptionValue = productOptionValueService.getByCode(store, source.getValue().getCode());
		} else if(source.getValue() != null && source.getValue().getId().longValue()>0) {
			productOptionValue = productOptionValueService.getById(source.getValue().getId());
		} else {
			//ProductOption value is text
			productOptionValue = new ProductOptionValue();
			productOptionValue.setProductOptionDisplayOnly(true);
			productOptionValue.setCode(UUID.randomUUID().toString());
			productOptionValue.setMerchantStore(store);
		}
		
		if(!CollectionUtils.isEmpty((source.getValue().getDescriptions()))) {
			productOptionValue =  persistableProductOptionValueMapper.merge(source.getValue(),productOptionValue, store, language);
			try {
				productOptionValueService.saveOrUpdate(productOptionValue);
			} catch (ServiceException e) {
				throw new ConversionRuntimeException("Error converting ProductOptionValue",e); 
			}
		}
		
		/**
			productOptionValue
			.getDescriptions().stream()
			.map(val -> this.persistableProductOptionValueMapper.convert(val, store, language)).collect(Collectors.toList());
			
		}**/
		
		if(productOption.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			throw new ConversionRuntimeException("Invalid product option id ");
		}
		
		if(productOptionValue!=null && productOptionValue.getMerchantStore().getId().intValue()!=store.getId().intValue()) {
			throw new ConversionRuntimeException("Invalid product option value id ");
		}
		

		if(destination.getId()!=null && destination.getId().longValue()>0) {
			destination.setId(destination.getId());
		} else {
			destination.setId(null);
		}

		destination.setProductOption(productOption);
		destination.setProductOptionValue(productOptionValue);
		destination.setProductId(source.getProductId());
		
		return destination;
	}

}
