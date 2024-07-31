package com.salesmanager.shop.store.controller.propertyrightsfile.facade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsFile;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storepropertyrightsfile.ReadablePropertyRightsFile;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsFileList;

public interface PropertyRightsFileFacade {
	void registerFiles(PropertyRightsFileEntity entity) throws Exception;
	ReadablePropertyRightsFileList listByTempletId(Language language, Integer storeId, Long templetId);
	void updateFiles(String userName, MerchantStore merchantStore,
	List<PersistablePropertyRightsFile> persistableFileList) throws Exception;
	PropertyRightsFileEntity getById(Long id, MerchantStore merchantStore);
}
