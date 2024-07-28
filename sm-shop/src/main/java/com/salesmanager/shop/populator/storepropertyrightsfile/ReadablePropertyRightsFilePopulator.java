package com.salesmanager.shop.populator.storepropertyrightsfile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storepropertyrightsfile.ReadablePropertyRightsFile;

@Component
public class ReadablePropertyRightsFilePopulator extends AbstractDataPopulator<PropertyRightsFileEntity, ReadablePropertyRightsFile> {
	
	protected final Log logger = LogFactory.getLog(getClass());

	@Override
	public ReadablePropertyRightsFile populate(PropertyRightsFileEntity source, ReadablePropertyRightsFile target,
			MerchantStore store, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		target.setId(source.getId());
		target.setTempleteId(source.getTempleteId());
		target.setFileUrl(source.getFileUrl());
		target.setStoragePath(source.getStoragePath());
		target.setReadFileName(source.getReadFileName());
		target.setFileSize(source.getFileSize());
		target.setFileExt(source.getFileExt());
		target.setBaseYn(source.getBaseYn());
		
		return target;
	}

	@Override
	protected ReadablePropertyRightsFile createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
