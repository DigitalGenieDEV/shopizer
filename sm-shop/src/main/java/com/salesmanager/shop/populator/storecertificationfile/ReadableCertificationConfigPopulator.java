package com.salesmanager.shop.populator.storecertificationfile;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storecertificationfile.ReadableCertificationConfig;
import com.salesmanager.shop.model.storelibrary.ReadableStoreLibrary;

@Component
public class ReadableCertificationConfigPopulator extends AbstractDataPopulator<CertificationConfigEntity, ReadableCertificationConfig> {
	
	protected final Log logger = LogFactory.getLog(getClass());
	
	@Override
	public ReadableCertificationConfig populate(CertificationConfigEntity source, ReadableCertificationConfig target,
			MerchantStore store, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		target.setId(source.getId());
		target.setStoreId(source.getStoreId());
		target.setTitle(source.getTitle());
		target.setDeleteYn(source.getDeleteYn());
		target.setAuditSection(source.getAuditSection());
		
		return target;
	}

	@Override
	protected ReadableCertificationConfig createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
