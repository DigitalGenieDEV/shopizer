package com.salesmanager.shop.store.controller.manager.facade;

import com.salesmanager.shop.model.manager.ReadableManagerGroupList;

public interface ManagerGroupFacade {

	/**
	 *
	 * @param keyword
	 * @param page
	 * @param count
	 * @return ReadableManagerGroupList
	 */
	ReadableManagerGroupList getManagerGroupList(String keyword, int page, int count) throws Exception;
}
