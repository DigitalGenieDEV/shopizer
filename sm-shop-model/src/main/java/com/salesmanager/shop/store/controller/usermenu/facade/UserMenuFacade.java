package com.salesmanager.shop.store.controller.usermenu.facade;

import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.model.usermenu.PersistableUserMenu;
import com.salesmanager.shop.model.usermenu.ReadableUserMenu;

public interface UserMenuFacade {

	/**
	 *
	 * @param visible
	 * @return ReadableUserMenu
	 */
	ReadableUserMenu getListUserMenu(int visible) throws Exception;
	
	/**
	 *
	 * @param userMenu
	 * @return PersistableUserMenu
	 * @throws Exception
	 */
	PersistableUserMenu saveUserMenu(PersistableUserMenu adminMenu) throws Exception;
	
	/**
	 *
	 * @param id
	 * @return ReadableAdminMenu
	 */
	ReadableUserMenu getById(int id) throws Exception;
	
	/**
	 *
	 * @param id
	 * @return
	 */
	void deleteUserMenu(int id) throws Exception;
	
	/**
	 *
	 * @param usermenu
	 * @param ip
	 * @return
	 */
	void updateChangeOrd(PersistableChangeOrd usermenu, String ip, String userId) throws Exception;

}
