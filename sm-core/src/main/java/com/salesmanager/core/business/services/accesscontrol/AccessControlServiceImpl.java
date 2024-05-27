package com.salesmanager.core.business.services.accesscontrol;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.accesscontrol.AccessControlRepository;
import com.salesmanager.core.business.repositories.accesscontrol.PageableAccessControlRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.accesscontrol.AccessControl;
import com.salesmanager.core.model.accesscontrol.ReadAccessControl;

@Service("accessControlService")
public class AccessControlServiceImpl extends SalesManagerEntityServiceImpl<Integer, AccessControl> implements AccessControlService {
	
	@Inject
	private AccessControlRepository accessControlRepository; 

	@Inject
	private PageableAccessControlRepository pageableAccessControlRepository;
	
	
	@Inject
	public AccessControlServiceImpl(AccessControlRepository accessControlRepository) {
		super(accessControlRepository);
		this.accessControlRepository = accessControlRepository;
	}
	
	public Page<ReadAccessControl> getAccessControlList(String keyword,int page, int count)throws ServiceException{
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableAccessControlRepository.getAccessControlList(keyword, pageRequest);
	}
	
	public void saveOrUpdate(AccessControl accessControl) throws ServiceException{
		// save or update (persist and attach entities
		if (accessControl.getId() != null && accessControl.getId() > 0) {
			super.update(accessControl);
		} else {
			this.create(accessControl);
		}
	}
}
