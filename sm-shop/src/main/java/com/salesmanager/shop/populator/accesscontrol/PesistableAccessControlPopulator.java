package com.salesmanager.shop.populator.accesscontrol;

import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.accesscontrol.AccessControl;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.accesscontrol.PersistableAccessControl;

@Component
public class PesistableAccessControlPopulator extends AbstractDataPopulator<PersistableAccessControl, AccessControl> {
	
	public AccessControl populate(PersistableAccessControl source, AccessControl target) throws ConversionException {

		try {
			target.setId(source.getId());
			target.setTitle(source.getTitle());
			target.setTargetSite(source.getTargetSite());
			target.setRegType(source.getRegType());
			target.setIpAddress(source.getIpAddress());
			target.setVisible(source.getVisible());
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
	public AccessControl populate(PersistableAccessControl source, AccessControl target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected AccessControl createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
