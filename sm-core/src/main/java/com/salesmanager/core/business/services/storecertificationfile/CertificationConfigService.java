package com.salesmanager.core.business.services.storecertificationfile;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationConfig;

public interface CertificationConfigService {

	void save(CertificationConfigEntity certificationConfigEntity) throws ServiceException;

	void deleteTemplete(MerchantStore store, String userName, Long id);

	List<CertificationConfigEntity> listByStoreId(Integer storeId);

	void updateTemplete(CertificationConfigEntity certificationConfigEntity) throws ServiceException;

}
