package com.salesmanager.shop.store.controller.cetificationconfig.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.storecertificationfile.CertificationConfigService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationConfig;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storecertificationfile.ReadableCertificationConfig;
import com.salesmanager.shop.populator.storecertificationfile.PersistableCertificationConfigPopulator;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationConfigList;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationConfigPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;

@Service("cetificationConfigFacade")
public class CetificationConfigFacadeImpl implements CetificationConfigFacade {

	@Autowired
	private ReadableCertificationConfigPopulator readableCertificationPopulator;
	
	@Inject
	private CertificationConfigService cetificationConfigService;
	
	@Inject
	private LanguageService languageService;
	
	@Inject 
	private PersistableCertificationConfigPopulator persistableCertificationConfigPopulator; 
	
	@Override
	public void registerTemplete(PersistableCertificationConfig persistableCertificationConfig) throws Exception {
		// TODO Auto-generated method stub
		Validate.notNull(persistableCertificationConfig);
		CertificationConfigEntity certificationConfigEntity = persistableCertificationConfigPopulator.populate(persistableCertificationConfig, null, languageService.defaultLanguage());
		cetificationConfigService.save(certificationConfigEntity);
	}

	@Override
	public void deleteTemplete(MerchantStore store, String userName, Long id) throws Exception {
		// TODO Auto-generated method stub
		cetificationConfigService.deleteTemplete(store, userName, id);
	}

	@Override
	public ReadableCertificationConfigList listByStoreId(Language language,
			Integer storeId) {
		// TODO Auto-generated method stub
		
		List<CertificationConfigEntity> list = cetificationConfigService.listByStoreId(storeId);
		List<ReadableCertificationConfig> readableCertificationConfigs = new ArrayList<ReadableCertificationConfig>();
		ReadableCertificationConfigList readableCertificationConfigList = new ReadableCertificationConfigList();
		
		if(!list.isEmpty()) {
			for (CertificationConfigEntity entity : list)
				readableCertificationConfigs.add(convertCertificationConfigEntityToReadableCertificationConfig(language, entity)); 
		}
		
		readableCertificationConfigList.setRecordsTotal(list.size());
		readableCertificationConfigList.setData(readableCertificationConfigs);
		
		return readableCertificationConfigList;
	}

	private ReadableCertificationConfig convertCertificationConfigEntityToReadableCertificationConfig(Language language,
		CertificationConfigEntity entity) {
		// TODO Auto-generated method stub
		ReadableCertificationConfig readable = new ReadableCertificationConfig();
		
		try {			
			readableCertificationPopulator.populate(entity, readable, null, language);
		} catch (Exception e) {
			throw new ConversionRuntimeException("Error while populating Certification Templet " + e.getMessage());
		}
		return readable;
		
	}

	@Override
	public void updateTemplete(Integer id, PersistableCertificationConfig persistableCertificationConfig) throws Exception {
		// TODO Auto-generated method stub
		Validate.notNull(persistableCertificationConfig);
		CertificationConfigEntity certificationConfigEntity = persistableCertificationConfigPopulator.populate(persistableCertificationConfig, null, languageService.defaultLanguage());
		cetificationConfigService.updateTemplete(certificationConfigEntity);
	}

}
