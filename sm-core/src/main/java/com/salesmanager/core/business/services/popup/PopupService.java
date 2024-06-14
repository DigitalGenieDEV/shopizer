package com.salesmanager.core.business.services.popup;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.popup.Popup;
import com.salesmanager.core.model.popup.ReadPopup;

public interface PopupService extends SalesManagerEntityService<Integer, Popup> {
	
	Page<ReadPopup> getPopupList(String site, String keyword, int page, int count) throws ServiceException;
	
	void saveOrUpdate(Popup popup) throws ServiceException;
	
	

}
