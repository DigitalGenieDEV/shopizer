package com.salesmanager.shop.populator.store.image;


import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.image.MerchantStoreImage;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.store.PersistableMerchantStore;
import com.salesmanager.shop.model.store.PersistableMerchantStoreImage;

@Component
public class PersistableMerchantStoreImagePopulator extends AbstractDataPopulator<PersistableMerchantStoreImage, MerchantStoreImage> {

	@Override
	public MerchantStoreImage populate(PersistableMerchantStoreImage source, MerchantStoreImage target,
			MerchantStore store, Language language) throws ConversionException {
		// TODO Auto-generated method stub
		
		if(target == null) {
			target = new MerchantStoreImage();
		}
		
		target.setId(source.getId());
		target.setMerchantImage(source.getMerchantImage());
		target.setMerchantImageUrl(source.getMerchantImageUrl());
		target.setMerchantStore(source.getMerchantStore());
		target.setSortOrder(source.getSortOrder());
		
		return target;
	}

	@Override
	protected MerchantStoreImage createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
