package com.salesmanager.shop.store.controller.manager.facade;

import com.salesmanager.shop.model.manager.PersistableManager;
import com.salesmanager.shop.model.manager.PersistableManagerGroup;
import com.salesmanager.shop.model.manager.ReadableManagerGroupList;

public interface ManagerGroupFacade {

	/**
	 *
	 * @param keyword
	 * @param page
	 * @param count
	 * @return ReadableManagerGroupList
	 */
	ReadableManagerGroupList getManagerGroupList(String keyword, int page, int count, int visible) throws Exception;
	
	
	/**
	 * manager Group create
	 *  @param PersistableManagerGroup
	 * @return PersistableManagerGroup
	 * @throws Exception
	 */
	PersistableManagerGroup create(PersistableManagerGroup managerGroup) throws Exception;
	
	
	/**
	 * manager Group update
	 *  @param PersistableManagerGroup
	 * @return PersistableManagerGroup
	 * @throws Exception
	 */
	PersistableManagerGroup update(PersistableManagerGroup managerGroup) throws Exception;
	
	
	/**
	 * manager Group delete
	 *  @param id
	 * @return 
	 * @throws Exception
	 */
	void delete(int id) throws Exception;
	
	
}
