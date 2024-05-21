package com.salesmanager.shop.populator.adminmenu;

import java.util.Date;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.adminmenu.AdminMenuService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.adminmenu.AdminMenu;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.adminmenu.PersistableAdminMenu;

@Component
public class PersistableAdminMenuPopulator extends AbstractDataPopulator<PersistableAdminMenu, AdminMenu> {

	@Inject
	private AdminMenuService adminMenuSerivce;

	public AdminMenu populate(PersistableAdminMenu source, AdminMenu target) throws ConversionException {

		try {

			Validate.notNull(target, "AdminMenu target cannot be null");
			target.setId(source.getId());
			target.setParentId(source.getParentId());
			target.setMenuName(source.getMenuName());
			target.setMenuDesc(source.getMenuDesc());
			target.setMenuUrl(source.getMenuUrl());
			target.setApiUrl(source.getApiUrl());
			target.setVisible(source.getVisible());
			if (source.getOrd() == 0) {
				target.setOrd(adminMenuSerivce.getOrder(source.getParentId()));
			} else {
				target.setOrd(source.getOrd());
			}
			target.setReg_id(source.getUserId());
			target.setReg_ip(source.getUserIp());
			target.setReg_date(new Date());
			target.setMod_id(source.getUserId());
			target.setMod_ip(source.getUserIp());
			target.setMod_date(new Date());

			return target;

		} catch (Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public AdminMenu populate(PersistableAdminMenu source, AdminMenu target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AdminMenu createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
