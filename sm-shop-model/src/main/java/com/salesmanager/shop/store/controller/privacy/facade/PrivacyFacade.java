package com.salesmanager.shop.store.controller.privacy.facade;

import com.salesmanager.shop.model.privacy.PersistablePrivacy;
import com.salesmanager.shop.model.privacy.PrivacyEntity;
import com.salesmanager.shop.model.privacy.ReadablePrivacy;
import com.salesmanager.shop.model.privacy.ReadableUserPrivacy;

public interface PrivacyFacade {
	
	ReadableUserPrivacy getListUserPrivacy(String division, int id) throws Exception;

	ReadablePrivacy getListPrivacy(int visible, String division, String keyword, int page, int count) throws Exception;

	PersistablePrivacy savePrivacy(PersistablePrivacy privacy) throws Exception;

	PrivacyEntity getById(int id) throws Exception;
	
	void deletePrivacy(int id) throws Exception;
}
