package com.salesmanager.shop.store.facade.popup;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.popup.PopupService;
import com.salesmanager.core.model.popup.Popup;
import com.salesmanager.core.model.popup.ReadPopup;
import com.salesmanager.shop.model.popup.PersistablePopup;
import com.salesmanager.shop.model.popup.PopupEntity;
import com.salesmanager.shop.model.popup.ReadablePopup;
import com.salesmanager.shop.model.popup.ReadablePopupList;
import com.salesmanager.shop.populator.popup.PersistablePopupPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.popup.facade.PopupFacade;

@Service
public class PopupFacadeImpl  implements PopupFacade {
	
	@Inject
	private ContentFacade contentFacade;

	@Inject
	private PopupService popupService;
	
	@Inject
	private PersistablePopupPopulator persistablePopupPopulator;
	
	@Inject
	private ObjectMapper objectMapper;
	
	public ReadablePopupList getPopupList(String site, String keyword, int page, int count) throws Exception {
		try {
			List<ReadPopup> popup = null;
			List<PopupEntity> targetList = new ArrayList<PopupEntity>();
			ReadablePopupList returnList = new ReadablePopupList();
			Page<ReadPopup> pageable = popupService.getPopupList(site, keyword, page, count);
			popup = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(popup.size());
			if (popup.size() > 0) {
				for (ReadPopup data : popup) {
					objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
					PopupEntity targetData = objectMapper.convertValue(data, PopupEntity.class);
					targetList.add(targetData);

				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	public PersistablePopup savePopup(PersistablePopup popup)  throws Exception {
		try {

			int popupId = popup.getId();
			Popup target = Optional.ofNullable(popupId)
					.filter(id -> id > 0)
					.map(popupService::getById)
					.orElse(new Popup());
			
		
			Popup dbPopup = populatePopup(popup, target);
			popupService.saveOrUpdate(dbPopup);
			
			return popup;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Popup", e);
		}
	}
	
	private Popup populatePopup(PersistablePopup popup, Popup target) {
		try {
			return persistablePopupPopulator.populate(popup, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}
	
	public ReadablePopup getById(int id) throws Exception {
		Popup data = popupService.getById(id);
		ReadablePopup targetData = objectMapper.convertValue(data, ReadablePopup.class);
		return targetData;
	}
	
	public void deletePopup(int id) throws Exception{
		Popup popup  =  new Popup();
		popup.setId(id);
		popupService.delete(popup);
	}
}
