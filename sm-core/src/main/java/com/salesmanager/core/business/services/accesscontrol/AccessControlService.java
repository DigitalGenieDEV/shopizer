package com.salesmanager.core.business.services.accesscontrol;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.accesscontrol.AccessControl;
import com.salesmanager.core.model.accesscontrol.ReadAccessControl;

public interface AccessControlService extends SalesManagerEntityService<Integer, AccessControl> {

	Page<ReadAccessControl> getAccessControlList(String keyword, int page, int count) throws ServiceException;
	
	void saveOrUpdate(AccessControl accessControl) throws ServiceException;
}
