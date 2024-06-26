package com.salesmanager.shop.store.facade.ipSafetyCenter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.ipSafetyCenter.IpSafetyCenterService;
import com.salesmanager.core.model.board.Board;
import com.salesmanager.core.model.iprsafecenter.IpSafetyCenter;
import com.salesmanager.core.model.iprsafecenter.ReadIpSafetyCenter;
import com.salesmanager.shop.model.common.AllDeleteIdEntity;
import com.salesmanager.shop.model.common.ChangeStateEntity;
import com.salesmanager.shop.model.ipSafetyCenter.IpSafetyCenterEntity;
import com.salesmanager.shop.model.ipSafetyCenter.PersistableIpSafetyCenter;
import com.salesmanager.shop.model.ipSafetyCenter.ReadableIpSafetyCenterList;
import com.salesmanager.shop.populator.ipSafetyCenter.PersistableIpsafetyCenterPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.ipSafetyCenter.facade.IpSafetyCenterFacade;

@Service
public class IpSafetyCenterFacadeImpl implements IpSafetyCenterFacade {

	
	@Inject
	private IpSafetyCenterService ipSafetyCenterService;
	
	@Inject
	private PersistableIpsafetyCenterPopulator persistableIpsafetyCenterPopulator;
	
	@Inject
	private ObjectMapper objectMapper;
	
	public ReadableIpSafetyCenterList getIpSafetyList(String type, String gbn, String sdate, String edate, String keyword,
			int page, int count) throws Exception{
		
		try {
			List<ReadIpSafetyCenter> ipsafeCenter = null;
			List<IpSafetyCenterEntity> targetList = new ArrayList<IpSafetyCenterEntity>();
			ReadableIpSafetyCenterList returnList = new ReadableIpSafetyCenterList();
			Page<ReadIpSafetyCenter> pageable = ipSafetyCenterService.getIpSafetyList(type,gbn, sdate,edate, keyword, page, count);
			ipsafeCenter = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(ipsafeCenter.size());
			if (ipsafeCenter.size() > 0) {
				for (ReadIpSafetyCenter data : ipsafeCenter) {
					objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
					IpSafetyCenterEntity targetData = objectMapper.convertValue(data, IpSafetyCenterEntity.class);
					targetList.add(targetData);

				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	public PersistableIpSafetyCenter saveIpSafeCenter(PersistableIpSafetyCenter data) throws Exception {
		try {

			int dataId = data.getId();
			IpSafetyCenter target = Optional.ofNullable(dataId).filter(id -> id > 0).map(ipSafetyCenterService::getById)
					.orElse(new IpSafetyCenter());

			IpSafetyCenter dbBanner = populateIpSafeCenter(data, target);
			ipSafetyCenterService.saveOrUpdate(dbBanner);

			return data;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating IpSafetyCenter", e);
		}
	}
	
	private IpSafetyCenter populateIpSafeCenter(PersistableIpSafetyCenter data, IpSafetyCenter target) {
		try {
			return persistableIpsafetyCenterPopulator.populate(data, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	public IpSafetyCenterEntity getById(int id) throws Exception{
		IpSafetyCenter data = ipSafetyCenterService.getById(id);
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		IpSafetyCenterEntity targetData = objectMapper.convertValue(data, IpSafetyCenterEntity.class);
		return targetData;
	}
	
	public void deleteIpSafeCenter(int id) throws Exception{
		IpSafetyCenter data =  new IpSafetyCenter();
		data.setId(id);
		ipSafetyCenterService.delete(data);
	}
	
	public void updateReplyContent(PersistableIpSafetyCenter data) throws Exception{
		IpSafetyCenter dbIpSafeCenter = new IpSafetyCenter();
		dbIpSafeCenter.setId(data.getId());
		dbIpSafeCenter.setReplyContent(data.getReplyContent());
		dbIpSafeCenter.getAuditSection().setModId(data.getUserId());
		dbIpSafeCenter.getAuditSection().setModIp(data.getUserIp());
		ipSafetyCenterService.updateReplyContent(dbIpSafeCenter );
	}
	
	public void updateChangeState(ChangeStateEntity data) throws Exception{
		String arrayIds[] = data.getSelectedIds().split(",");
		if(arrayIds.length > 0 ) {
			for(String ids : arrayIds) {
				IpSafetyCenter dbIpSafeCenter = new IpSafetyCenter();
				dbIpSafeCenter.setId(Integer.parseInt(ids));
				dbIpSafeCenter.setState(data.getState());
				dbIpSafeCenter.getAuditSection().setModId(data.getUserId());
				dbIpSafeCenter.getAuditSection().setModIp(data.getUserIp());
				ipSafetyCenterService.updateChangeState(dbIpSafeCenter );
			}
		}
	}
	
	public void deleteAll(AllDeleteIdEntity data) throws Exception{
		String arrayIds[] = data.getSelectedIds().split(",");
		if(arrayIds.length > 0 ) {
			for(String ids : arrayIds) {
				IpSafetyCenter dbIpSafeCenter =  new IpSafetyCenter();
				dbIpSafeCenter.setId(Integer.parseInt(ids));
				ipSafetyCenterService.delete(dbIpSafeCenter);
			}
		}
		
	}
}
