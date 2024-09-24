package com.salesmanager.core.business.services.ipSafetyCenter;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.iprsafecenter.IpSafetyCenter;
import com.salesmanager.core.model.iprsafecenter.ReadIpSafetyCenter;

public interface IpSafetyCenterService extends SalesManagerEntityService<Integer, IpSafetyCenter> {
	
	 Page<ReadIpSafetyCenter> getIpSafetyList(String type, String gbn, String sdate, String edate, String keyword,
			int page, int count, String userId) throws ServiceException;
	 
	 void saveOrUpdate(IpSafetyCenter data) throws ServiceException;
	 
	 void updateReplyContent(IpSafetyCenter data) throws ServiceException;
	 
	 void updateChangeState(IpSafetyCenter data) throws ServiceException;
}
