package com.salesmanager.shop.store.controller.accesscontrol.facade;

import com.salesmanager.shop.model.accesscontrol.PersistableAccessControl;
import com.salesmanager.shop.model.accesscontrol.ReadableAccessControl;
import com.salesmanager.shop.model.accesscontrol.ReadableAccessControlList;

public interface AccessControlFacade {

	/**
	 *
	 * @param keyword

	 * @param page
	 * @param count
	 * @return ReadableAccessControlList
	 */
	ReadableAccessControlList getAccessControlList(String keyword, int page, int count) throws Exception;
	
	
	/**
	 *
	 * @param id

	 * @return ReadableAccessControl
	 */
	ReadableAccessControl getById(int id)  throws Exception;
	
	
	/**
	 *
	 * @param accessControl

	 * @return PersistableAccessControl
	 */
	PersistableAccessControl saveAccessControl(PersistableAccessControl accessControl) throws Exception;
	
	/**
	 *
	 * @param int id

	 * @return 
	 * @throws Exception 
	 */
	void deleteAccessControl(int id) throws Exception;
}
