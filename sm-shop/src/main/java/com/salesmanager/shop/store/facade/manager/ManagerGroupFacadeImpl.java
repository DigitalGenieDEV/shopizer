package com.salesmanager.shop.store.facade.manager;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.manager.ManagerGroupService;
import com.salesmanager.core.business.services.manager.ManagerService;
import com.salesmanager.core.model.manager.ManagerGroup;
import com.salesmanager.core.model.manager.ReadManagerGroup;
import com.salesmanager.shop.model.manager.PersistableManagerGroup;
import com.salesmanager.shop.model.manager.ReadableManagerGroup;
import com.salesmanager.shop.model.manager.ReadableManagerGroupList;
import com.salesmanager.shop.populator.manager.PersistableManagerGroupPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerGroupFacade;

@Service
public class ManagerGroupFacadeImpl implements ManagerGroupFacade {

	@Inject
	private ManagerGroupService managerGroupService;
	
	@Inject
	private ManagerService managerService;
	
	@Inject
	private ObjectMapper objectMapper;
	
	
	@Inject
	private PersistableManagerGroupPopulator persistableManagerGroupPopulator;
	

	@Override
	public ReadableManagerGroupList getManagerGroupList(String keyword, int page, int count, int visible) throws Exception{
		try{
			List<ReadManagerGroup> managerGroup = null;
			List<ReadableManagerGroup> targetList = new ArrayList<ReadableManagerGroup>();
			ReadableManagerGroupList returnList = new ReadableManagerGroupList();
			org.springframework.data.domain.Page<ReadManagerGroup> pageable =  managerGroupService.getManagerGroupList(keyword, page,count,visible);
			managerGroup = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(managerGroup.size());
			if(managerGroup.size() > 0) {
				for(ReadManagerGroup data : managerGroup) {
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
	
	public PersistableManagerGroup create(PersistableManagerGroup managerGroup) throws Exception{
		try {
			int groupId = managerGroup.getId();
			ManagerGroup target = Optional.ofNullable(groupId)
					.filter(id -> id > 0)
					.map(managerGroupService::getById)
					.orElse(new ManagerGroup());
			
			ManagerGroup dbManagerGroup = populateManagerGroup(managerGroup, target);
			managerGroupService.saveOrUpdate(dbManagerGroup);

			// set category id
			managerGroup.setId(managerGroup.getId());
			
			return managerGroup;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while Create Manager Group", e);
		}
	}
	
	
	public ReadableManagerGroup getById(int id) throws Exception {
		ReadManagerGroup managerGroup = managerGroupService.getById(id);

		if (managerGroup == null) {
			throw new ResourceNotFoundException("Manager Group [" + id + "] not found");
		}

		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		ReadableManagerGroup readableManager = objectMapper.convertValue(managerGroup, ReadableManagerGroup.class);
		
		return readableManager;
		
	}
	
	public PersistableManagerGroup update(PersistableManagerGroup managerGroup) throws Exception{
		try {
			int groupId = managerGroup.getId();
			ManagerGroup target = Optional.ofNullable(groupId)
					.filter(id -> id > 0)
					.map(managerGroupService::getById)
					.orElse(new ManagerGroup());
			
			ManagerGroup dbManagerGroup = populateManagerGroup(managerGroup, target);
			managerGroupService.saveOrUpdate(dbManagerGroup);

			// set category id
			managerGroup.setId(managerGroup.getId());
			
			return managerGroup;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Manager Group", e);
		}
		
	}
	
	private ManagerGroup populateManagerGroup( PersistableManagerGroup managerGroup, ManagerGroup target) {
		try {
			return persistableManagerGroupPopulator.populate(managerGroup, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	public void delete(int id) throws Exception{
		ReadManagerGroup data = managerGroupService.getById(id);
		System.out.println(data.getId());
		if (data == null) {
			throw new ResourceNotFoundException("Manager Group[" + id + "] not found");
		}
		managerService.deleteManager(data.getId());
		ManagerGroup managerGroup =  new ManagerGroup();
		managerGroup.setId(data.getId());
		managerGroupService.delete(managerGroup);
	}
}
