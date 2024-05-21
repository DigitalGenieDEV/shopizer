package com.salesmanager.shop.store.controller.manager.facade;

import com.salesmanager.shop.model.manager.PersistableManagerAuthList;

public interface ManagerMenuAuthFacade {

	PersistableManagerAuthList getManagerAdminMenuAuthList(int grpId) throws Exception;
	 
	PersistableManagerAuthList create(PersistableManagerAuthList menuAuthList, String userIp) throws Exception;
}
