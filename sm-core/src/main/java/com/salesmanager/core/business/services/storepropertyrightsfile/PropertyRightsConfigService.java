package com.salesmanager.core.business.services.storepropertyrightsfile;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsConfigEntity;

public interface PropertyRightsConfigService {

	void save(PropertyRightsConfigEntity configEntity) throws ServiceException;

	void deleteTemplete(MerchantStore store, String userName, Long id);

	List<PropertyRightsConfigEntity> listByStoreId(Integer storeId);

	void updateTemplete(PropertyRightsConfigEntity configEntity) throws ServiceException;

}
