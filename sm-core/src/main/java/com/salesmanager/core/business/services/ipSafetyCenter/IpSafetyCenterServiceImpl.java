package com.salesmanager.core.business.services.ipSafetyCenter;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.ipSafetyCenter.IpSafetyCenterRepository;
import com.salesmanager.core.business.repositories.ipSafetyCenter.PageableIpSafetyCenterRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.iprsafecenter.IpSafetyCenter;
import com.salesmanager.core.model.iprsafecenter.ReadIpSafetyCenter;

@Service("ipSafetyCenterService")
public class IpSafetyCenterServiceImpl  extends SalesManagerEntityServiceImpl<Integer, IpSafetyCenter> implements IpSafetyCenterService {
	@Inject
	private IpSafetyCenterRepository ipSafetyCenterRepository;

	@Inject
	private PageableIpSafetyCenterRepository pageableIpSafetyCenterRepository;

	@Inject
	public IpSafetyCenterServiceImpl(IpSafetyCenterRepository ipSafetyCenterRepository) {
		super(ipSafetyCenterRepository);
		this.ipSafetyCenterRepository = ipSafetyCenterRepository;
	}
	
	public Page<ReadIpSafetyCenter> getIpSafetyList(String type, String gbn, String sdate, String edate, String keyword,
			int page, int count, String userId) throws ServiceException{
		Pageable pageRequest = PageRequest.of(page, count);
		System.out.println("userId"+userId);
		System.out.println("sdate"+sdate);
		System.out.println("edate"+edate);
		System.out.println("type"+type);
		System.out.println("gbn"+gbn);
		System.out.println("keyword"+keyword);
		return pageableIpSafetyCenterRepository.getIpSafetyList(type, gbn, sdate, edate, keyword,userId, pageRequest);
	}
	
	public void saveOrUpdate(IpSafetyCenter data) throws ServiceException {
		// save or update (persist and attach entities
		if (data.getId() != null && data.getId() > 0) {
			super.update(data);
		} else {
			this.create(data);
		}
	}
	
	public void updateReplyContent(IpSafetyCenter data) throws ServiceException{
		ipSafetyCenterRepository.updateReplyContent(data);
	}
	
	public void updateChangeState(IpSafetyCenter data) throws ServiceException{
		ipSafetyCenterRepository.updateChangeState(data);
	}
}
