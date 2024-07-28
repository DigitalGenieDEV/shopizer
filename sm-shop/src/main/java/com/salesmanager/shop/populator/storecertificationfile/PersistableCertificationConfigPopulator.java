package com.salesmanager.shop.populator.storecertificationfile;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationConfig;
import com.salesmanager.core.model.reference.language.Language;

@Component
public class PersistableCertificationConfigPopulator extends AbstractDataPopulator<PersistableCertificationConfig, CertificationConfigEntity> {

	@Override
	public CertificationConfigEntity populate(PersistableCertificationConfig source,
			CertificationConfigEntity target, MerchantStore store, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		Validate.notNull(source);
		
		if(target == null) {
			target = new CertificationConfigEntity();
		}
		
		target.setId(source.getId());
		target.setAuditSection(source.getAuditSection());
		target.setStoreId(source.getStoreId());
		target.setTitle(source.getTitle());
		target.setDeleteYn(source.getDeleteYn()==null?"N":source.getDeleteYn());
		
		return target;
	}

	@Override
	protected CertificationConfigEntity createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
