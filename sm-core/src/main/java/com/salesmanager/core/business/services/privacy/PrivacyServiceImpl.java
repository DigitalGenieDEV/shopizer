package com.salesmanager.core.business.services.privacy;

import java.util.List;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.privacy.PageablePrivacyRepository;
import com.salesmanager.core.business.repositories.privacy.PrivacyRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.privacy.Privacy;
import com.salesmanager.core.model.privacy.ReadPrivacy;

@Service("privacyService")
public class PrivacyServiceImpl  extends SalesManagerEntityServiceImpl<Integer, Privacy> implements PrivacyService {
	
	@Inject
	private PrivacyRepository privacyRepository;

	@Inject
	private PageablePrivacyRepository pageablePrivacyRepository;
	
	@Inject
	public PrivacyServiceImpl(PrivacyRepository privacyRepository) {
		super(privacyRepository);
		this.privacyRepository = privacyRepository;
	}
	
	
	public Page<Privacy> getListPrivacy(int visible, String division, String keyword, int  page, int count)  throws ServiceException{
		Pageable pageRequest = PageRequest.of(page, count);
		return pageablePrivacyRepository.getListPrivacy(visible,division,keyword, pageRequest);
	}
	
	public void saveOrUpdate(Privacy privacy) throws ServiceException{
		// save or update (persist and attach entities
		if (privacy.getId() != null && privacy.getId() > 0) {
			super.update(privacy);
		} else {
			this.create(privacy);
		}
	}
	
	public ReadPrivacy getUserPrivacy(String division, int id) throws ServiceException {
		return privacyRepository.getUserPrivacy(division, id);
	}
	
	public List<Privacy> getListUserPrivacy(String division) throws ServiceException{
		return privacyRepository.getListUserPrivacy(division);
	}
	
}
