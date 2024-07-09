package com.salesmanager.core.business.services.catalog.product.information;

import java.util.List;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.information.ProductInformation;
import com.salesmanager.core.model.catalog.product.information.ReadProductInformation;
import com.salesmanager.core.model.popup.Popup;

public interface ProductInformationService  extends SalesManagerEntityService<Long, ProductInformation> {

	Page<ReadProductInformation> getList(int store, int languageId, int page, int count, String division ) throws ServiceException;
	
	void saveOrUpdate(ProductInformation data) throws ServiceException;
}
