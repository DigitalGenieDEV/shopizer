package com.salesmanager.core.business.services.adminmenu;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.adminmenu.AdminMenu;
import com.salesmanager.core.model.adminmenu.ReadAdminMenu;

public interface AdminMenuService extends SalesManagerEntityService<Integer, AdminMenu> {

	List<ReadAdminMenu> getListAdminMenu(int visible) throws ServiceException;

	int getOrder(int parentId) throws ServiceException;

	String getNamePath(int id) throws ServiceException;

	ReadAdminMenu getById(int id) throws ServiceException;

	void saveOrUpdate(AdminMenu paramVO) throws ServiceException;
	
	int getMaxId() throws ServiceException;

	void deleteAdminMenu(int id) throws ServiceException;

	void updateChangeOrd(AdminMenu adminMenu) throws ServiceException;
}
