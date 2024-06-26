package com.salesmanager.shop.store.controller.ipSafetyCenter.facade;

import com.salesmanager.shop.model.common.AllDeleteIdEntity;
import com.salesmanager.shop.model.common.ChangeStateEntity;
import com.salesmanager.shop.model.ipSafetyCenter.IpSafetyCenterEntity;
import com.salesmanager.shop.model.ipSafetyCenter.PersistableIpSafetyCenter;
import com.salesmanager.shop.model.ipSafetyCenter.ReadableIpSafetyCenterList;

public interface IpSafetyCenterFacade {
	/**
	 *
	 * @param gbn
	 * @param keyword
	 * @param type
	 * @param sdate
	 * @param edate
	 * @param page
	 * @param count
	 * @return ReadableBoardList
	 */
	ReadableIpSafetyCenterList getIpSafetyList(String type, String gbn, String sdate, String edate, String keyword,
			int page, int count) throws Exception;
	
	PersistableIpSafetyCenter saveIpSafeCenter(PersistableIpSafetyCenter data) throws Exception;
	
	IpSafetyCenterEntity getById(int id) throws Exception;
	
	void deleteIpSafeCenter(int id) throws Exception;
	
	void updateReplyContent(PersistableIpSafetyCenter data) throws Exception;
	
	void updateChangeState(ChangeStateEntity data) throws Exception;
	
	void deleteAll(AllDeleteIdEntity data) throws Exception;
}
