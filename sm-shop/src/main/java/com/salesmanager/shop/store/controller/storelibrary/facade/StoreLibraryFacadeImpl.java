package com.salesmanager.shop.store.controller.storelibrary.facade;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.storelibrary.StoreLibraryService;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibraryCriteria;
import com.salesmanager.core.model.merchant.library.StoreLibraryList;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.store.ReadableMerchantStore;
import com.salesmanager.shop.model.storelibrary.ReadableStoreLibrary;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.populator.storelibrary.ReadableStoreLibraryList;
import com.salesmanager.shop.populator.storelibrary.ReadableStoreLibraryPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;

@Service("storeLibraryFacade")
public class StoreLibraryFacadeImpl implements StoreLibraryFacade {

	@Inject
	private StoreLibraryService storeLibraryService;
	
	@Autowired
	private ReadableStoreLibraryPopulator readableStoreLibraryPopulator;
	
	@Override
	public void registerLibrary(StoreLibrary storeLibrary) throws ServiceException {
		// TODO Auto-generated method stub
		storeLibraryService.save(storeLibrary);
	}

	@Override
	public ReadableStoreLibraryList listByStoreId(StoreLibraryCriteria criteria, Language language, Integer storeId, String fileType, Integer page, Integer count, String sortType, String keywordType, String keyword) {
		
		Page<StoreLibrary> libraries = null;
		List<ReadableStoreLibrary> readableStoreLibraries = new ArrayList<ReadableStoreLibrary>();
		ReadableStoreLibraryList readableList = new ReadableStoreLibraryList();
		
		libraries = storeLibraryService.listByStoreId(storeId, fileType, page, count, sortType, keywordType, keyword);
		
		if (!CollectionUtils.isEmpty(libraries.getContent())) {
			for (StoreLibrary storeLibrary : libraries)
				readableStoreLibraries.add(convertStoreLibraryToReadableStoreLibrary(language, storeLibrary)); 
				
		}
		
		readableList.setData(readableStoreLibraries);
		readableList.setRecordsTotal(libraries.getTotalElements());
		readableList.setTotalPages(libraries.getTotalPages());
		readableList.setNumber(libraries.getSize());
		readableList.setRecordsFiltered(libraries.getSize());
		return readableList;

	}
 	
	private ReadableStoreLibrary convertStoreLibraryToReadableStoreLibrary(Language language, StoreLibrary storeLibrary) {
		ReadableStoreLibrary readable = new ReadableStoreLibrary();
		
		/**
		 * Language is not important for this conversion using default language
		 */
		try {			
			readableStoreLibraryPopulator.populate(storeLibrary, readable, null, language);
		} catch (Exception e) {
			throw new ConversionRuntimeException("Error while populating MerchantStore " + e.getMessage());
		}
		return readable;
	}

	@Override
	public void deleteLibrary(Long id, String userName) throws ServiceException {
		// TODO Auto-generated method stub
		storeLibraryService.deleteLibrary(id, userName);
	}

}
