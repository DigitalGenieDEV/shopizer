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
import com.salesmanager.core.business.services.manager.ManagerMenuAuthService;
import com.salesmanager.core.model.manager.MenuAuth;
import com.salesmanager.core.model.manager.ReadMenuAuth;
import com.salesmanager.shop.model.manager.ManagerAuthEntity;
import com.salesmanager.shop.model.manager.PersistableManagerAuthList;
import com.salesmanager.shop.populator.manager.PersistableManagerMenuAuthPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerMenuAuthFacade;

@Service
public class ManagerMenuAuthFacadeImpl implements ManagerMenuAuthFacade {

	@Inject
	private ManagerMenuAuthService managerMenuAuthService;
	
	@Inject
	private PersistableManagerMenuAuthPopulator persistableManagerMenuAuthPopulator;
	
	
	@Inject
	private ObjectMapper objectMapper;
	
	
	public PersistableManagerAuthList getManagerAdminMenuAuthList (int grpId)  throws Exception {
		PersistableManagerAuthList target = new PersistableManagerAuthList();
		ArrayList<ManagerAuthEntity> authList =  new ArrayList<ManagerAuthEntity>();
		List<ReadMenuAuth> dataList = managerMenuAuthService.getManagerAdminMenuAuthList(grpId);
		if(dataList.size() > 0 ) {
			for(ReadMenuAuth auth : dataList) {
				objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				ManagerAuthEntity targetData = objectMapper.convertValue(auth, ManagerAuthEntity.class);
				authList.add(targetData);
			}
		}
		target.setData(authList);
		
		return target;
		
	}

	public PersistableManagerAuthList create(PersistableManagerAuthList menuAuthList, String userIp) throws Exception {
		try {
			if (menuAuthList.getData().size() > 0) {
				managerMenuAuthService.deleteMenuAuth(menuAuthList.getData().get(0).getGrpId());

				for (ManagerAuthEntity data : menuAuthList.getData()) {
					data.setUserIp(userIp);
					int authId = data.getId();
					MenuAuth target = Optional.ofNullable(authId).filter(id -> id > 0)
							.map(managerMenuAuthService::getById).orElse(new MenuAuth());
					MenuAuth dbManagerMenuAuth = populateManagerAdminMenuAuth(data, target);
					managerMenuAuthService.saveOrUpdate(dbManagerMenuAuth);

				}

			}
			return menuAuthList;

		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while Create Manager AdminMenu Auth", e);
		}

	}
	
	private MenuAuth populateManagerAdminMenuAuth( ManagerAuthEntity data, MenuAuth target) {
		try {
			return persistableManagerMenuAuthPopulator.populate(data, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
}
