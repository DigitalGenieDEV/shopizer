package com.salesmanager.shop.store.facade.privacy;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.privacy.PrivacyService;
import com.salesmanager.core.model.privacy.Privacy;
import com.salesmanager.core.model.privacy.ReadPrivacy;
import com.salesmanager.shop.model.privacy.PersistablePrivacy;
import com.salesmanager.shop.model.privacy.PrivacyEntity;
import com.salesmanager.shop.model.privacy.PrivacyUserEntity;
import com.salesmanager.shop.model.privacy.ReadablePrivacy;
import com.salesmanager.shop.model.privacy.ReadableUserPrivacy;
import com.salesmanager.shop.populator.privacy.PersistablePrivacyPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.privacy.facade.PrivacyFacade;

@Service
public class PrivacyFacadeImpl implements PrivacyFacade {

	@Inject
	private PrivacyService privacyService;
	
	@Inject
	private PersistablePrivacyPopulator persistablePrivacyPopulator;
	
	@Inject
	private ObjectMapper objectMapper;
	
	public ReadableUserPrivacy getListUserPrivacy(String division, int id) throws Exception{
		ReadableUserPrivacy targetData =  new ReadableUserPrivacy();
		ReadPrivacy data = privacyService.getUserPrivacy(division, id);
		if(data.getTitle() != null) {
			targetData.setId(data.getId());
			targetData.setTitle(data.getTitle());
			targetData.setContent(data.getContent());
			List<PrivacyUserEntity> list =  new  ArrayList<PrivacyUserEntity>();
			List<Privacy> dataList =  privacyService.getListUserPrivacy(division);
			if(dataList.size() > 0 ) {
				for(Privacy listData : dataList) {
					PrivacyUserEntity sendData = new PrivacyUserEntity();
					sendData.setId(listData.getId());
					sendData.setTitle(listData.getTitle());
					list.add(sendData);
				}
				targetData.setData(list);
			}else {
				targetData.setData(new  ArrayList<PrivacyUserEntity>());
			}
			return targetData;
		}else {
			return new ReadableUserPrivacy();
		}
		
	}
	
	public ReadablePrivacy getListPrivacy(int visible, String division, String keyword, int  page, int count) throws Exception{
		try{
			List<Privacy> privacy = null;
			List<PrivacyEntity> targetList = new ArrayList<PrivacyEntity>();
			ReadablePrivacy returnList = new ReadablePrivacy();
			org.springframework.data.domain.Page<Privacy> pageable =  privacyService.getListPrivacy(visible, division, keyword, page,count);
			privacy = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(privacy.size());
			if(privacy.size() > 0) {
				for(Privacy data : privacy) {
					PrivacyEntity targetData = objectMapper.convertValue(data, PrivacyEntity.class);
					targetList.add(targetData);
					
				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e){
			throw new ServiceRuntimeException(e);
		}
	}
	
	public PersistablePrivacy savePrivacy(PersistablePrivacy privacy) throws Exception {
		try {

			int privacyId = privacy.getId();
			Privacy target = Optional.ofNullable(privacyId)
					.filter(id -> id > 0)
					.map(privacyService::getById)
					.orElse(new Privacy());
			
		
			Privacy dbPrivacy = populatePrivacy(privacy, target);
			privacyService.saveOrUpdate(dbPrivacy);

			
			return privacy;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Privacy", e);
		}
	}
	
	public PrivacyEntity getById(int id)  throws Exception{
		Privacy data = privacyService.getById(id);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		PrivacyEntity targetData = objectMapper.convertValue(data, PrivacyEntity.class);
		return targetData;
	}
	
	public void deletePrivacy(int id) throws Exception{
		Privacy data = new Privacy();
		data.setId(id);
		privacyService.delete(data);
	}
	
	private Privacy populatePrivacy( PersistablePrivacy privacy, Privacy target) {
		try {
			return persistablePrivacyPopulator.populate(privacy, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
}
