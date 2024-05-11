package com.salesmanager.core.business.services.manager;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.manager.ManagerGroupRepository;
import com.salesmanager.core.business.repositories.manager.PageableManagerGroupRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.manager.Manager;
import com.salesmanager.core.model.manager.ManagerGroup;

@Service("managerGroupService")
public class ManagerGroupServiceImpl  extends SalesManagerEntityServiceImpl<Integer, ManagerGroup> implements ManagerGroupService {

	@Inject
	private ManagerGroupRepository managerGroupRepository; 

	@Inject
	private PageableManagerGroupRepository pageableManagerGroupRepository;
	
	@Inject
	public ManagerGroupServiceImpl(ManagerGroupRepository managerGroupRepository) {
		super(managerGroupRepository);
		this.managerGroupRepository = managerGroupRepository;
	}
	
	public Page<ManagerGroup> getManagerGroupList(String keyword, int page, int count)throws ServiceException{
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableManagerGroupRepository.getManagerGroupList(keyword, pageRequest);
	}
}
