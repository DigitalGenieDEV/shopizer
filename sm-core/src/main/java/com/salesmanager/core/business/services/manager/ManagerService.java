package com.salesmanager.core.business.services.manager;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.manager.Manager;
import com.salesmanager.core.model.manager.ReadableManager;

public interface ManagerService extends SalesManagerEntityService<Long, Manager> {
	
	Page<Manager> getManagerList(String keyword, String gbn, int deptId, int page, int count) throws ServiceException;
	
	ReadableManager getByUserName(String userName) throws ServiceException;

	Manager getId(Long id) throws ServiceException;
	
	int getDupEmplIdCount(String userId) throws ServiceException;
	
	void saveOrUpdate(Manager manager) throws ServiceException;
	
	void deleteManager(int grpId) throws ServiceException;
	
	void updateLoginFailCount(String emplId) throws ServiceException;
	
	void updateLoginDate(String emplId)throws ServiceException;

}
