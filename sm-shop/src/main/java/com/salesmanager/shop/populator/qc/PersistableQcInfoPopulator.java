package com.salesmanager.shop.populator.qc;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.fulfillment.service.QcInfoService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.business.utils.ObjectConvert;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.privacy.Privacy;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.PersistableQcInfo;
import com.salesmanager.shop.model.privacy.PersistablePrivacy;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PersistableQcInfoPopulator extends AbstractDataPopulator<PersistableQcInfo, QcInfo> {

	@Autowired
	private QcInfoService qcInfoService;


	public QcInfo populate(PersistableQcInfo source)
			throws ConversionException {

		try {
			return ObjectConvert.convert(source, QcInfo.class);
		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public QcInfo populate(PersistableQcInfo source, QcInfo target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected QcInfo createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
