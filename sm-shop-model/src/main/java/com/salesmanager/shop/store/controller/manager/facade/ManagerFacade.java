package com.salesmanager.shop.store.controller.manager.facade;

import com.salesmanager.shop.model.manager.PersistableManager;
import com.salesmanager.shop.model.manager.ReadableManager;
import com.salesmanager.shop.model.manager.ReadableManagerList;

public interface ManagerFacade {

	/**
	 *
	 * @param keyword
	 * @param gbn
	 * @param deptId
	 * @param page
	 * @param count
	 * @return ReadableManagerList
	 */
	ReadableManagerList getManagerList(String keyword, String gbn, int deptId, int page, int count) throws Exception;

	/**
	 *
	 * @param manager
	 * 
	 * @return
	 */
	void updateEnabled(PersistableManager manager) throws Exception;

	/**
	 * emplId Dup Exist
	 * @param userId
	 * @return boolean
	 * @throws Exception
	 */
	boolean isManagerExist(String userId) throws Exception;
	
	
	/**
	 * manager create
	 *  @param PersistableManager
	 * @return ReadableManager
	 * @throws Exception
	 */
	PersistableManager create(PersistableManager manager) throws Exception;
	
	
	/**

	 *  @param id
	 * @return ReadableManager
	 * @throws Exception
	 */
	ReadableManager getById(Long id) throws Exception;
	
	
	/**

	 *  @param PersistableManager
	 * @return PersistableManager
	 * @throws Exception
	 */
	PersistableManager update(PersistableManager manager) throws Exception;
	
	
	/**
	 *  @param id
	 * @return 
	 * @throws Exception
	 */
	void delete(Long id) throws Exception;
}
