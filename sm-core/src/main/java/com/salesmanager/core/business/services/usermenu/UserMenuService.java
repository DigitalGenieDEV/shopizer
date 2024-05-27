package com.salesmanager.core.business.services.usermenu;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.usermenu.ReadUserMenu;
import com.salesmanager.core.model.usermenu.UserMenu;

public interface UserMenuService extends SalesManagerEntityService<Integer, UserMenu> {
	
	List<ReadUserMenu> getListUserMenu(int visible) throws ServiceException;
	
	int getOrder(int parentId) throws ServiceException;
	
	void saveOrUpdate(UserMenu paramVO) throws ServiceException;
	
	int getMaxId() throws ServiceException;
	
	ReadUserMenu getById(int id) throws ServiceException;
	
	String getNamePath(int id) throws ServiceException;
	
	void deleteUserMenu(int id) throws ServiceException;
	
	void updateChangeOrd(UserMenu userMenu) throws ServiceException;
	
	public void resetAllEntriesInCash() throws Exception;
}
