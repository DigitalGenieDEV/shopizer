package com.salesmanager.shop.store.controller.cetificationfile.facade;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationFile;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationFileList;

public interface CetificationFileFacade {
	void registerFiles(CertificationFileEntity entity) throws Exception;
	ReadableCertificationFileList listByTempletId(Language language, Integer storeId, Long templetId);
	void updateFiles(String userName, MerchantStore merchantStore,
			List<PersistableCertificationFile> persistableFileList) throws Exception;
	CertificationFileEntity getById(Long id, MerchantStore merchantStore) throws Exception;
	byte[] getContent(MerchantStore merchantStore, FileContentType fileType, String fileName) throws Exception;
}
