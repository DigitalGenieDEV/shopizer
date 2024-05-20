package com.salesmanager.core.business.services.manager;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.manager.ManagerGroup;
import com.salesmanager.core.model.manager.ReadManagerGroup;

public interface ManagerGroupService extends SalesManagerEntityService<Integer, ManagerGroup> {
	
	Page<ReadManagerGroup> getManagerGroupList(String keyword, int page, int count, int visible) throws ServiceException;
	
	void saveOrUpdate(ManagerGroup managerGroup) throws ServiceException;
	
	ReadManagerGroup getById(int id) throws ServiceException;
	
}
