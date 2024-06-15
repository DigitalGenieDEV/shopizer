package com.salesmanager.core.business.services.popup;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.popup.PageablePopupRepository;
import com.salesmanager.core.business.repositories.popup.PopupRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.popup.Popup;
import com.salesmanager.core.model.popup.ReadPopup;

@Service("popupService")
public class PopupServiceImpl extends SalesManagerEntityServiceImpl<Integer, Popup> implements PopupService {

	@Inject
	private PopupRepository popupRepository;

	@Inject
	private PageablePopupRepository pageablePopupRepository;

	@Inject
	public PopupServiceImpl(PopupRepository popupRepository) {
		super(popupRepository);
		this.popupRepository = popupRepository;
	}
	
	public Page<ReadPopup> getPopupList(String site, String keyword, int page, int count)
			throws ServiceException {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageablePopupRepository.getPopupList(site, keyword, pageRequest);
	}
	
	public void saveOrUpdate(Popup popup) throws ServiceException {
		// save or update (persist and attach entities
		if (popup.getId() != null && popup.getId() > 0) {
			super.update(popup);
		} else {
			this.create(popup);
		}
	}

}
