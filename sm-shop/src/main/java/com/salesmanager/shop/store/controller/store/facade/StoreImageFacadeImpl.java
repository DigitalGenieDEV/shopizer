package com.salesmanager.shop.store.controller.store.facade;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.merchant.image.MerchantStoreImageService;
import com.salesmanager.core.model.merchant.image.MerchantStoreImage;
import com.salesmanager.shop.model.store.PersistableMerchantStore;
import com.salesmanager.shop.model.store.PersistableMerchantStoreImage;
import com.salesmanager.shop.populator.store.PersistableMerchantStorePopulator;
import com.salesmanager.shop.populator.store.image.PersistableMerchantStoreImagePopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;

@Service("storeImageFacade")
public class StoreImageFacadeImpl implements StoreImageFacade {
	
	@Inject
	private PersistableMerchantStoreImagePopulator persistableMerchantStoreImagePopulator;
	
	@Inject
	private MerchantStoreImageService merchantStoreImageService;
	
	@Override
	public void save(PersistableMerchantStoreImage persistableMerchantStoreImage) {
		// TODO Auto-generated method stub
		Validate.notNull(persistableMerchantStoreImage);
		try {
			MerchantStoreImage image = persistableMerchantStoreImagePopulator.populate(persistableMerchantStoreImage, null, null);
			merchantStoreImageService.save(image);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionRuntimeException(e);
		}
		
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		merchantStoreImageService.delete(id);
	}

	@Override
	public void deleteByStoreId(Integer storeId) {
		// TODO Auto-generated method stub
		merchantStoreImageService.deleteByStoreId(storeId);
	}

}
