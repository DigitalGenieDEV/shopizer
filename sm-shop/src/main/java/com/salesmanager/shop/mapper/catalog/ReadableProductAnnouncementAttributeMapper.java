package com.salesmanager.shop.mapper.catalog;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductOption;
import com.salesmanager.core.model.catalog.product.attribute.ProductOptionValue;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.Mapper;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableAnnouncement;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductAnnouncement;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductAttributeEntity;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionEntity;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValue;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.UUID;

@Component
public class ReadableProductAnnouncementAttributeMapper implements Mapper<ProductAnnouncementAttribute, ReadableProductAnnouncement> {

	@Inject
	private ProductOptionService productOptionService;
	@Inject
	private ProductOptionValueService productOptionValueService;
	@Inject
	private ProductService productService;
	@Inject
	private PersistableProductOptionValueMapper persistableProductOptionValueMapper;

	@Autowired
	private ReadableProductOptionMapper readableProductOptionMapper;
	@Autowired
	private ReadableProductOptionValueMapper readableProductOptionValueMapper;



	@Override
	public ReadableProductAnnouncement convert(ProductAnnouncementAttribute source, MerchantStore store, Language language) {
		ReadableProductAnnouncement attribute = new ReadableProductAnnouncement();
		return merge(source,attribute,store,language);
	}

	@Override
	public ReadableProductAnnouncement merge(ProductAnnouncementAttribute source, ReadableProductAnnouncement destination,
								  MerchantStore store, Language language) {


		ReadableProductAnnouncement attr = new ReadableProductAnnouncement();
		if(destination !=null) {
			attr = destination;
		}
		try {
			attr.setId(source.getId());

			if(source.getProductOption()!=null) {
				ReadableProductOptionEntity option = readableProductOptionMapper.convert(source.getProductOption(), store, language);
				attr.setOption(option);
			}

			if(source.getProductOptionValue()!=null) {
				ReadableProductOptionValue optionValue = readableProductOptionValueMapper.convert(source.getProductOptionValue(), store, language);
				attr.setValue(optionValue);
			}

		} catch (Exception e) {
			throw new ConversionRuntimeException("Exception while product attribute conversion",e);
		}


		return attr;
	}

}
