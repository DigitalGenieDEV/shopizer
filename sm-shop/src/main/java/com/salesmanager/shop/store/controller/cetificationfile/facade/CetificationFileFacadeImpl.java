package com.salesmanager.shop.store.controller.cetificationfile.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.services.storecertificationfile.CertificationConfigService;
import com.salesmanager.core.business.services.storecertificationfile.CertificationFileService;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationConfig;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationFile;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storecertificationfile.ReadableCertificationFile;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationFileList;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationFilePopulator;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationConfigPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;

@Service("cetificationFileFacade")
public class CetificationFileFacadeImpl implements CetificationFileFacade {
	
	@Autowired
	private ReadableCertificationFilePopulator readableCertificationFilePopulator;
	
	@Inject
	private CertificationFileService cetificationFileService;
	
	@Override
	public void registerFiles(CertificationFileEntity entity) throws Exception {
		// TODO Auto-generated method stub
		cetificationFileService.save(entity);
	}

	@Override
	public ReadableCertificationFileList listByTempletId(Language language, Integer storeId, Long templetId) {
		// TODO Auto-generated method stub
		List<CertificationFileEntity> fileList = cetificationFileService.listByTempletId(templetId);
		List<ReadableCertificationFile> readableFileList = new ArrayList<ReadableCertificationFile>();
		ReadableCertificationFileList readableCertificationFileList = new ReadableCertificationFileList();
		
		if(!fileList.isEmpty()) {
			for(CertificationFileEntity entity : fileList) {
				readableFileList.add(convertEntityToReadable(language, entity));
			}
		}
		
		readableCertificationFileList.setData(readableFileList);
		
		return readableCertificationFileList;
	}

	private ReadableCertificationFile convertEntityToReadable(Language language, CertificationFileEntity entity) {
		// TODO Auto-generated method stub
		ReadableCertificationFile readableCertificationFile = new ReadableCertificationFile();
		
		try {
			readableCertificationFilePopulator.populate(entity, readableCertificationFile, null, language);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionRuntimeException("Error while populating Certification File " + e.getMessage());
		}
		
		return readableCertificationFile;
	}

	@Override
	public void updateFiles(String userName, MerchantStore merchantStore,
			List<PersistableCertificationFile> persistableFileList) throws Exception {
		// TODO Auto-generated method stub
		AuditSection auditSection = new AuditSection();
		auditSection.setModifiedBy(userName);
		auditSection.setDateModified(new Date());
		for(PersistableCertificationFile file : persistableFileList) {
			file.setAuditSection(auditSection);
			cetificationFileService.updateFileState(file);
		}
	}

}
