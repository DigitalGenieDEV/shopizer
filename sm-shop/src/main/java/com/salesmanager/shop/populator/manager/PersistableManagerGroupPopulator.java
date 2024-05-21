package com.salesmanager.shop.populator.manager;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.manager.ManagerGroup;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.manager.PersistableManagerGroup;

@Component
public class PersistableManagerGroupPopulator  extends AbstractDataPopulator<PersistableManagerGroup, ManagerGroup> {
	
	public ManagerGroup populate(PersistableManagerGroup source, ManagerGroup target)
			throws ConversionException {

		try {

			Validate.notNull(target, "ManagerGroup target cannot be null");
			
			target.setId(source.getId());
			target.setGrpName(source.getGrpName());
			target.setRegId(source.getUserId());
			target.setRegIp(source.getUserIp());
			target.setModId(source.getUserId());
			target.setModIp(source.getUserIp());
			
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public ManagerGroup populate(PersistableManagerGroup source, ManagerGroup target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected ManagerGroup createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
