package com.salesmanager.shop.populator.storepropertyrightsfile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsConfigEntity;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storelibrary.ReadableStoreLibrary;
import com.salesmanager.shop.model.storepropertyrightsfile.ReadablePropertyRightsConfig;

@Component
public class ReadablePropertyRightsConfigPopulator extends AbstractDataPopulator<PropertyRightsConfigEntity, ReadablePropertyRightsConfig> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public ReadablePropertyRightsConfig populate(PropertyRightsConfigEntity source, ReadablePropertyRightsConfig target,
			MerchantStore store, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		target.setId(source.getId());
		target.setStoreId(source.getStoreId());
		target.setTitle(source.getTitle());
		target.setDeleteYn(source.getDeleteYn());
		target.setAuditSection(source.getAuditSection());
		return target;
	}

	@Override
	protected ReadablePropertyRightsConfig createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
