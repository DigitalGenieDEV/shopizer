package com.salesmanager.core.business.services.manager;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.manager.CategoryAuth;
import com.salesmanager.core.model.manager.ReadCategoryAuth;

public interface ManagerCategoryAuthService extends SalesManagerEntityService<Integer, CategoryAuth> {
	
	List<ReadCategoryAuth> getCategoryAuthFullList(int grpId) throws ServiceException;
	
	List<ReadCategoryAuth> getCategoryList() throws ServiceException;
	
	List<CategoryAuth> getCategoryAuthList(int grpId) throws ServiceException;
	
	void deleteCategoryAuth(int grpId) throws ServiceException;
	
	void saveOrUpdate(CategoryAuth categoryAuth) throws ServiceException;
}
