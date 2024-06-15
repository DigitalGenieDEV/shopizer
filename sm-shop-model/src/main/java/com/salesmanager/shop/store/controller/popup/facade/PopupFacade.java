package com.salesmanager.shop.store.controller.popup.facade;

import com.salesmanager.shop.model.popup.PersistablePopup;
import com.salesmanager.shop.model.popup.ReadablePopup;
import com.salesmanager.shop.model.popup.ReadablePopupList;
import com.salesmanager.shop.model.popup.ReadableUserPopupList;

public interface PopupFacade {
	
	ReadableUserPopupList getPopupUserList(String site) throws Exception;
	
	ReadablePopupList getPopupList(String site, String keyword, int page, int count) throws Exception;

	PersistablePopup savePopup(PersistablePopup popup) throws Exception;

	ReadablePopup getById(int id) throws Exception;
	
	void deletePopup(int id) throws Exception;
}
