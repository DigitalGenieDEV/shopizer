package com.salesmanager.core.business.services.manager;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.manager.ManagerGroup;

public interface ManagerGroupService extends SalesManagerEntityService<Integer, ManagerGroup> {
	
	Page<ManagerGroup> getManagerGroupList(String keyword, int page, int count) throws ServiceException;
}
