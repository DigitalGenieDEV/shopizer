package com.salesmanager.shop.store.controller.propertyrightsfile.facade;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.services.storepropertyrightsfile.PropertyRightsFileService;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsFile;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storepropertyrightsfile.ReadablePropertyRightsFile;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsFileList;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsFilePopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;

@Service("propertyRightsFileFacade")
public class PropertyRightsFileFacadeImpl implements PropertyRightsFileFacade {
	
	@Autowired
	private ReadablePropertyRightsFilePopulator readableFilePopulator;
	
	@Inject
	private PropertyRightsFileService propertyRightsFileService;
	
	@Override
	public void registerFiles(PropertyRightsFileEntity entity) throws Exception {
		// TODO Auto-generated method stub
		propertyRightsFileService.save(entity);
	}

	@Override
	public ReadablePropertyRightsFileList listByTempletId(Language language, Integer storeId, Long templetId) {
		// TODO Auto-generated method stub
		List<PropertyRightsFileEntity> fileList = propertyRightsFileService.listByTempletId(templetId);
		List<ReadablePropertyRightsFile> readableList = new ArrayList<ReadablePropertyRightsFile>();
		ReadablePropertyRightsFileList readableFileList = new ReadablePropertyRightsFileList();
		
		if(!fileList.isEmpty()) {
			for(PropertyRightsFileEntity entity : fileList) {
				readableList.add(convertEntityToReadable(language, entity));
			}
		}
		
		readableFileList.setData(readableList);
		
		return readableFileList;
	}

	private ReadablePropertyRightsFile convertEntityToReadable(Language language, PropertyRightsFileEntity entity) {
		// TODO Auto-generated method stub
		ReadablePropertyRightsFile readableCertificationFile = new ReadablePropertyRightsFile();
		
		try {
			readableFilePopulator.populate(entity, readableCertificationFile, null, language);
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionRuntimeException("Error while populating Certification File " + e.getMessage());
		}
		
		return readableCertificationFile;
	}

	@Override
	public void updateFiles(String userName, MerchantStore merchantStore,
			List<PersistablePropertyRightsFile> persistableFileList) throws Exception {
		// TODO Auto-generated method stub
		AuditSection auditSection = new AuditSection();
		auditSection.setModifiedBy(userName);
		auditSection.setDateModified(new Date());
		for(PersistablePropertyRightsFile file : persistableFileList) {
			file.setAuditSection(auditSection);
			propertyRightsFileService.updateFileState(file);
		}
	}

}
