package com.salesmanager.core.business.services.storelibrary;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.merchant.PageableMerchantRepository;
import com.salesmanager.core.business.repositories.storelibrary.PageableStoreLibraryRepository;
import com.salesmanager.core.business.repositories.storelibrary.StoreLibraryRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerServiceImpl;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibraryList;

@Service("storeLibraryService")
public class StoreLibraryServiceImpl extends SalesManagerEntityServiceImpl<Long, StoreLibrary> implements StoreLibraryService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Inject
	private StoreLibraryRepository storeLibraryRepository;
	
	@Autowired
	private PageableStoreLibraryRepository pageableStoreLibraryRepository;
	
	public StoreLibraryServiceImpl(JpaRepository<StoreLibrary, Long> repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.storeLibraryRepository = storeLibraryRepository;
	}
	
	@Override
	public void save(StoreLibrary entity) throws ServiceException {
		// TODO Auto-generated method stub
		LOGGER.debug("Creating storeLibrary");
		super.save(entity);
	}

	@Override
	public Page<StoreLibrary> listByStoreId(Integer storeId, String fileType, Integer page, Integer count, String sortType, String keywordType, String keyword) {
		// TODO Auto-generated method stub
		// page, count null 처리
		if(page == null) {
			page = 0;
		}
		if(count == null) {
			count = Integer.MAX_VALUE;
		}
		if(sortType == null || sortType.equalsIgnoreCase("")) {
			sortType = "DATE_CREATED";
		}
		if(fileType == null) {
			fileType = "";
		}
		Pageable pageRequest = PageRequest.of(page, count, Sort.by(sortType));
		return pageableStoreLibraryRepository.listByStoreId(storeId, fileType, keywordType, keyword, pageRequest);
	}

	@Override
	public void deleteLibrary(Long id, String userName) throws ServiceException {
		// TODO Auto-generated method stub
		LOGGER.debug("Deleting storeLibrary");
		storeLibraryRepository.deleteLibrary(id, userName, "Y");
	}
	
}
