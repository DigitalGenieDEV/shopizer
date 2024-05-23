package com.salesmanager.shop.populator.usermenu;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.usermenu.UserMenuService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.usermenu.UserMenu;
import com.salesmanager.shop.model.usermenu.PersistableUserMenu;

@Component
public class PersistableUserMenuPopulator extends AbstractDataPopulator<PersistableUserMenu, UserMenu> {
	@Inject
	private UserMenuService userMenuSerivce;

	public UserMenu populate(PersistableUserMenu source, UserMenu target) throws ConversionException {

		try {

			Validate.notNull(target, "UserMenu target cannot be null");
			target.setId(source.getId());
			target.setParentId(source.getParentId());
			target.setMenuName(source.getMenuName());
			target.setMenuDesc(source.getMenuDesc());
			target.setUrl(source.getUrl());
			target.setTop(source.getTop());
			target.setSide(source.getSide());
			target.setNavi(source.getNavi());
			target.setTab(source.getTab());
			target.setMemberTarget(source.getMemberTarget());
			target.setLinkTarget(source.getLinkTarget());
			target.setVisible(source.getVisible());
			if (source.getOrd() == 0) {
				target.setOrd(userMenuSerivce.getOrder(source.getParentId()));
			} else {
				target.setOrd(source.getOrd());
			}
			target.getAuditSection().setRegId(source.getUserId());
			target.getAuditSection().setRegIp(source.getUserIp());
			target.getAuditSection().setModId(source.getUserId());
			target.getAuditSection().setModIp(source.getUserIp());
	

			return target;

		} catch (Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public UserMenu populate(PersistableUserMenu source, UserMenu target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected UserMenu createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
