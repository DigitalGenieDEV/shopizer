package com.salesmanager.core.business.services.catalog.product.information;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.information.PageableProductInformationRepository;
import com.salesmanager.core.business.repositories.catalog.product.information.ProductInformationRepository;
import com.salesmanager.core.business.repositories.popup.PopupRepository;
import com.salesmanager.core.business.services.catalog.product.ProductServiceImpl;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.information.ProductInformation;
import com.salesmanager.core.model.catalog.product.information.ReadProductInformation;
import com.salesmanager.core.model.popup.Popup;

@Service("productInformationService")
public class ProductInformationServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductInformation> implements ProductInformationService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);
	
	@Inject
	private ProductInformationRepository productInformationRepository;

	@Inject
	private PageableProductInformationRepository pageableProductInformationRepository;
	
	@Inject
	public ProductInformationServiceImpl(ProductInformationRepository productInformationRepository) {
		super(productInformationRepository);
		this.productInformationRepository = productInformationRepository;
	}

	
	public Page<ReadProductInformation> getList(int store, int languageId, int page, int count, String division ) throws ServiceException{
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableProductInformationRepository.getList(store, languageId, division, pageRequest);
	}
	
	public void saveOrUpdate(ProductInformation data) throws ServiceException{

		// save or update (persist and attach entities
		if (data.getId() != null && data.getId() > 0) {
			super.update(data);
		} else {
			this.create(data);
		}
	}

}
