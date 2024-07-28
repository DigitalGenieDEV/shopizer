package com.salesmanager.core.business.services.storelibrary;

import java.util.List;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibraryList;

public interface StoreLibraryService {

	void save(StoreLibrary storeLibrary) throws ServiceException;

	Page<StoreLibrary> listByStoreId(Integer storeId, String fileType, Integer page, Integer count, String sortType, String keywordType, String keyword);

	void deleteLibrary(Long id, String userName) throws ServiceException;

}
