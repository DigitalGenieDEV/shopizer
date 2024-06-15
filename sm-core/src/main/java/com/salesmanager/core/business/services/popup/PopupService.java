package com.salesmanager.core.business.services.popup;

import java.util.List;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.popup.Popup;
import com.salesmanager.core.model.popup.ReadPopup;
import com.salesmanager.core.model.popup.ReadUserPopup;

public interface PopupService extends SalesManagerEntityService<Integer, Popup> {
	
	List<ReadUserPopup> getPopupUserList(String site) throws ServiceException;
	
	Page<ReadPopup> getPopupList(String site, String keyword, int page, int count) throws ServiceException;
	
	void saveOrUpdate(Popup popup) throws ServiceException;
	
	

}
