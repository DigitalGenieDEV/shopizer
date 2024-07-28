package com.salesmanager.shop.store.controller.cetificationconfig.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigCriteria;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationConfig;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationConfigList;

public interface CetificationConfigFacade {

	void registerTemplete(PersistableCertificationConfig persistableCertificationConfig) throws Exception;

	void deleteTemplete(MerchantStore store, String userName, Long id) throws Exception;

	ReadableCertificationConfigList listByStoreId(Language language, Integer storeId);

	void updateTemplete(Integer id, PersistableCertificationConfig persistableCertificationConfig) throws Exception;

}
