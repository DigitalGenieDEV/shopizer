package com.salesmanager.shop.store.controller.manager.facade;

import com.salesmanager.shop.model.manager.ReadableManagerCategoryAuth;
import com.salesmanager.shop.model.manager.ReadableManagerCategoryAuthList;

public interface ManagerCategoryAuthFacade {

	ReadableManagerCategoryAuth getCategoryAuthFullList(int grpId) throws Exception;

	ReadableManagerCategoryAuthList getCategoryAuthList(int grpId) throws Exception;

}
