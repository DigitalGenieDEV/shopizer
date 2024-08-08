package com.salesmanager.shop.store.controller.store.facade;

import com.salesmanager.shop.model.store.PersistableMerchantStore;
import com.salesmanager.shop.model.store.PersistableMerchantStoreImage;

public interface StoreImageFacade {
	void save(PersistableMerchantStoreImage persistableMerchantStoreImage);
	void delete(Long id);
	void deleteByStoreId(Integer storeId);
}
