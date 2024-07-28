package com.salesmanager.shop.populator.storepropertyrightsfile;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsConfig;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsConfigEntity;
import com.salesmanager.core.model.reference.language.Language;

@Component
public class PersistablePropertyRightsConfigPopulator extends AbstractDataPopulator<PersistablePropertyRightsConfig, PropertyRightsConfigEntity> {

	@Override
	public PropertyRightsConfigEntity populate(PersistablePropertyRightsConfig source,
			PropertyRightsConfigEntity target, MerchantStore store, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		Validate.notNull(source);
		
		if(target == null) {
			target = new PropertyRightsConfigEntity();
		}
		
		target.setId(source.getId());
		target.setAuditSection(source.getAuditSection());
		target.setStoreId(source.getStoreId());
		target.setTitle(source.getTitle());
		target.setDeleteYn(source.getDeleteYn()==null?"N":source.getDeleteYn());
		
		return target;
	}

	@Override
	protected PropertyRightsConfigEntity createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
