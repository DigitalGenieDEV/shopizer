package com.salesmanager.shop.store.controller.propertyrightsconfig.facade;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsConfig;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsConfigList;

public interface PropertyRightsConfigFacade {

	void registerTemplete(PersistablePropertyRightsConfig persistableConfig) throws Exception;

	void deleteTemplete(MerchantStore store, String userName, Long id) throws Exception;

	ReadablePropertyRightsConfigList listByStoreId(Language language, Integer storeId);

	void updateTemplete(Integer id, PersistablePropertyRightsConfig persistablePropertyRightsConfig) throws Exception;

}
