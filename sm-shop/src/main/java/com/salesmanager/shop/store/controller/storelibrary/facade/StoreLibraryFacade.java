package com.salesmanager.shop.store.controller.storelibrary.facade;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibraryCriteria;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storelibrary.ReadableStoreLibrary;
import com.salesmanager.shop.populator.storelibrary.ReadableStoreLibraryList;

public interface StoreLibraryFacade {

	void registerLibrary(StoreLibrary storeLibrary) throws ServiceException;

	ReadableStoreLibraryList listByStoreId(StoreLibraryCriteria criteria, Language language, Integer storeId, String fileType, Integer page, Integer count, String sortType, String keywordType, String keyword);

	void deleteLibrary(Long id, String userNamer) throws ServiceException;

}
