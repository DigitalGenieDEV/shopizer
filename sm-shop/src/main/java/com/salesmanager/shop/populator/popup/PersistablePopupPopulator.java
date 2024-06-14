package com.salesmanager.shop.populator.popup;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.popup.Popup;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.popup.PersistablePopup;


@Component
public class PersistablePopupPopulator extends AbstractDataPopulator<PersistablePopup, Popup> {
	public Popup populate(PersistablePopup source, Popup target)
			throws ConversionException {

		try {
			
			Validate.notNull(target, "Popup target cannot be null");
			target.setId(source.getId());
			target.setType(source.getType());
			target.setName(source.getName());
			target.setImage(source.getImage());
			target.setSite(source.getSite());
			target.setSdate(source.getSdate());
			target.setEdate(source.getEdate());
			target.setTarget(source.getTarget());
			target.setOrd(source.getOrd());
			target.setVisible(source.getVisible());
			target.setUrl(source.getUrl());
			target.setWidth(source.getWidth());
			target.setHeight(source.getHeight());
			target.setLeft(source.getLeft());
			target.setTop(source.getTop());
			target.getAuditSection().setRegId(source.getUserId());
			target.getAuditSection().setRegIp(source.getUserIp());
			target.getAuditSection().setModId(source.getUserId());
			target.getAuditSection().setModIp(source.getUserIp());
		
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public Popup populate(PersistablePopup source, Popup target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Popup createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
