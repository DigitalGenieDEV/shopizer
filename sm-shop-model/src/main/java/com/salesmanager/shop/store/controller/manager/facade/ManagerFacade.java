package com.salesmanager.shop.store.controller.manager.facade;

import java.util.List;

import com.salesmanager.core.model.merchant.MerchantStore;
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
	
	
	/**

	 *  @param emplId
	 * @return 
	 * @throws Exception
	 */
	void updateLoginFailCount(String emplId) throws Exception;
	
	
	/**

	 *  @param emplId
	 * @return 
	 * @throws Exception
	 */
	void updateLoginDate(String emplId) throws Exception;
	
	
  /**
   * Retrieve authenticated user
   * @return
   */
  String authenticatedManager() throws Exception;
  
  /**
   * Retrieve authenticated user
   * @return
 * @throws Exception 
   */
  String authorizedMenu(String authenticatedManager, String url) throws Exception;
  
  /**
   * Method to be used in argument resolver.
   * @param store
   * @return
   */
  boolean authorizeStore(MerchantStore store, String path)  throws Exception;
  
  /**
   * Check if user is in specific list of roles
   * @param userName
   * @param groupNames
   * @return
   */
  boolean userInRoles(String userName)  throws Exception;
  
  /**
   * Determines if a user is authorized to perform an action on a specific store
   * 
   * @param userName
   * @param merchantStoreCode
   * @return
   * @throws Exception
   */
  boolean authorizedStore(String userName, String merchantStoreCode);
}
