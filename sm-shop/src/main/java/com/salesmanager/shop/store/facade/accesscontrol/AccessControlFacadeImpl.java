package com.salesmanager.shop.store.facade.accesscontrol;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.accesscontrol.AccessControlService;
import com.salesmanager.core.model.accesscontrol.AccessControl;
import com.salesmanager.core.model.accesscontrol.ReadAccessControl;
import com.salesmanager.shop.model.accesscontrol.PersistableAccessControl;
import com.salesmanager.shop.model.accesscontrol.ReadableAccessControl;
import com.salesmanager.shop.model.accesscontrol.ReadableAccessControlList;
import com.salesmanager.shop.populator.accesscontrol.PesistableAccessControlPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.accesscontrol.facade.AccessControlFacade;

@Service
public class AccessControlFacadeImpl implements AccessControlFacade {
	
	@Inject
	private AccessControlService accessControlService;
	
	@Inject
	private PesistableAccessControlPopulator persistableAccessControlPopulator;
	

	@Inject
	private ObjectMapper objectMapper;
	

	public ReadableAccessControlList getAccessControlList(String keyword, int page, int count) throws Exception{
		try{
			List<ReadAccessControl> access = null;
			List<ReadableAccessControl> targetList = new ArrayList<ReadableAccessControl>();
			ReadableAccessControlList returnList = new ReadableAccessControlList();
			org.springframework.data.domain.Page<ReadAccessControl> pageable =  accessControlService.getAccessControlList(keyword,page,count);
			access = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(access.size());
			if(access.size() > 0) {
				for(ReadAccessControl data : access) {
					ReadableAccessControl targetData = objectMapper.convertValue(data, ReadableAccessControl.class);
					targetList.add(targetData);
					
				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e){
			throw new ServiceRuntimeException(e);
		}
	}
	
	public ReadableAccessControl getById(int id)  throws Exception{
		AccessControl data = accessControlService.getById(id);
		ReadableAccessControl targetData = objectMapper.convertValue(data, ReadableAccessControl.class);
		return targetData;
	}
	
	
	public PersistableAccessControl saveAccessControl(PersistableAccessControl accessControl) throws Exception{
		try {

			int accessControlId = accessControl.getId();
			AccessControl target = Optional.ofNullable(accessControlId)
					.filter(id -> id > 0)
					.map(accessControlService::getById)
					.orElse(new AccessControl());
			
		
			AccessControl dbCode = populateAccessControl(accessControl, target);
			accessControlService.saveOrUpdate(dbCode);

			
			return accessControl;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating AccessControl", e);
		}
	}
	
	public void deleteAccessControl(int id) throws Exception{
		AccessControl accessControl =  new AccessControl();
		accessControl.setId(id);
		accessControlService.delete(accessControl);
	}
	
	private AccessControl populateAccessControl(PersistableAccessControl accessControl, AccessControl target) {
		try {
			return persistableAccessControlPopulator.populate(accessControl, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}

}
