package com.salesmanager.shop.populator.manager;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.manager.MenuAuth;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.manager.ManagerAuthEntity;

@Component
public class PersistableManagerMenuAuthPopulator  extends AbstractDataPopulator<ManagerAuthEntity, MenuAuth> {
	
	public MenuAuth populate(ManagerAuthEntity source, MenuAuth target)
			throws ConversionException {

		try {

			Validate.notNull(target, "MenuAuth target cannot be null");
			
			target.setId(source.getId());
			target.setMenuId(source.getMenuId());
			target.setGrpId(source.getGrpId());
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
	public MenuAuth populate(ManagerAuthEntity source, MenuAuth target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected MenuAuth createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
