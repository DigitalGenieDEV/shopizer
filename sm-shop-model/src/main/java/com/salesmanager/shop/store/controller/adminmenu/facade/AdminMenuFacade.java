package com.salesmanager.shop.store.controller.adminmenu.facade;

import com.salesmanager.shop.model.adminmenu.PersistableAdminMenu;
import com.salesmanager.shop.model.adminmenu.PersistableChangeOrdAdminMenu;
import com.salesmanager.shop.model.adminmenu.ReadableAdminMenu;

public interface AdminMenuFacade {

	/**
	 *
	 * @param visible
	 * @return ReadableAdminMenu
	 */
	ReadableAdminMenu getListAdminMenu(int visible) throws Exception;

	/**
	 *
	 * @param adminMenu
	 * @return PersistableAdminMenu
	 * @throws Exception
	 */
	PersistableAdminMenu saveAdminMenu(PersistableAdminMenu adminMenu) throws Exception;

	/**
	 *
	 * @param adminMenuId
	 * @return ReadableAdminMenu
	 */
	ReadableAdminMenu getById(int adminMenuId) throws Exception;

	/**
	 *
	 * @param adminMenuId
	 * @return
	 */
	void deleteAdminMenu(int adminMenuId) throws Exception;

	/**
	 *
	 * @param adminMenu
	 * @param ip
	 * @return
	 */
	void updateChangeOrd(PersistableChangeOrdAdminMenu adminMenu, String ip) throws Exception;

}
