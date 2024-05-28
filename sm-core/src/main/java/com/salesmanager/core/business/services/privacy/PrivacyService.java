package com.salesmanager.core.business.services.privacy;

import java.util.List;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.privacy.Privacy;
import com.salesmanager.core.model.privacy.ReadPrivacy;

public interface PrivacyService extends SalesManagerEntityService<Integer, Privacy>  {

	Page<Privacy> getListPrivacy(int visible, String division, String keyword, int  page, int count)  throws ServiceException;
	
	void saveOrUpdate(Privacy privacy) throws ServiceException;
	
	ReadPrivacy getUserPrivacy(String division, int id) throws ServiceException;
	
	List<Privacy> getListUserPrivacy(String division) throws ServiceException;
}
