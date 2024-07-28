package com.salesmanager.shop.populator.storelibrary;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.reference.zone.ZoneService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.store.ReadableMerchantStore;
import com.salesmanager.shop.model.storelibrary.ReadableStoreLibrary;
import com.salesmanager.shop.utils.ImageFilePath;

@Component
public class ReadableStoreLibraryPopulator extends AbstractDataPopulator<StoreLibrary, ReadableStoreLibrary> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Autowired
	private CountryService countryService;
	
	@Autowired
	private ZoneService zoneService;
	
	@Autowired
	@Qualifier("img")
	private ImageFilePath filePath;
	
	@Autowired
	private LanguageService languageService;

	@Override
	public ReadableStoreLibrary populate(StoreLibrary source, ReadableStoreLibrary target, MerchantStore store,
			Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		target.setId(source.getId());
		target.setStoreId(source.getStoreId());
		target.setFileUrl(source.getFileUrl());
		target.setStoragePath(source.getStoragePath());
		target.setReadFileName(source.getReadFileName());
		target.setFileType(source.getFileType());
		target.setFileSize(source.getFileSize());
		target.setFileExt(source.getFileExt());
		target.setDeleteYn(source.getDeleteYn());
		
		return target;
	}

	@Override
	protected ReadableStoreLibrary createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
	
}
