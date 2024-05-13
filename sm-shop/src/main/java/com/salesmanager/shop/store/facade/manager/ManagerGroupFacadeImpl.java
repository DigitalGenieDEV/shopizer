package com.salesmanager.shop.store.facade.manager;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.manager.ManagerGroupService;
import com.salesmanager.core.model.manager.ManagerGroup;
import com.salesmanager.shop.model.manager.ReadableManagerGroup;
import com.salesmanager.shop.model.manager.ReadableManagerGroupList;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerGroupFacade;

@Service
public class ManagerGroupFacadeImpl implements ManagerGroupFacade {

	@Inject
	private ManagerGroupService managerGroupService;
	
	@Inject
	private ObjectMapper objectMapper;
	
	
	@SuppressWarnings("null")
	@Override
	public ReadableManagerGroupList getManagerGroupList(String keyword, int page, int count) throws Exception{
		try{
			List<ManagerGroup> managerGroup = null;
			List<ReadableManagerGroup> targetList = new ArrayList<ReadableManagerGroup>();
			ReadableManagerGroupList returnList = new ReadableManagerGroupList();
			org.springframework.data.domain.Page<ManagerGroup> pageable =  managerGroupService.getManagerGroupList(keyword,  page,count);
			managerGroup = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(managerGroup.size());
			if(managerGroup.size() > 0) {
				for(ManagerGroup data : managerGroup) {
					ReadableManagerGroup targetData = objectMapper.convertValue(data, ReadableManagerGroup.class);
					targetList.add(targetData);
					
				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e){
			throw new ServiceRuntimeException(e);
		}
	}
}
