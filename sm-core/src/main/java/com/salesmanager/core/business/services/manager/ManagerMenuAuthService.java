package com.salesmanager.core.business.services.manager;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.manager.MenuAuth;
import com.salesmanager.core.model.manager.ReadMenuAuth;

public interface ManagerMenuAuthService extends SalesManagerEntityService<Integer, MenuAuth> {

	List<ReadMenuAuth> getManagerAdminMenuAuthList(int grpId) throws ServiceException;

	void deleteMenuAuth(int grpId) throws ServiceException;

	void saveOrUpdate(MenuAuth menuAuth) throws ServiceException;
	
	int getMenuAuthCnt(int grpId, int menuId)  throws ServiceException;
}
