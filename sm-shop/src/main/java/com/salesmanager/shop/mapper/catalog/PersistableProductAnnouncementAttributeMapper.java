package com.salesmanager.shop.mapper.catalog;

import com.alibaba.fastjson.JSON;
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

		if (!CollectionUtils.isEmpty(source.getAnnouncementFields())){
			destination.setText(JSON.toJSONString(source));
		}
		destination.setProductId(source.getProductId());
		
		return destination;
	}

}
