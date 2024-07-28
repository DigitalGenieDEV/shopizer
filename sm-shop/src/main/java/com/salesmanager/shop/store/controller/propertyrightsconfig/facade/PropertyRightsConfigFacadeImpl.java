package com.salesmanager.shop.store.controller.propertyrightsconfig.facade;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.storepropertyrightsfile.PropertyRightsConfigService;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsConfig;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsConfigEntity;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.storepropertyrightsfile.ReadablePropertyRightsConfig;
import com.salesmanager.shop.populator.storepropertyrightsfile.PersistablePropertyRightsConfigPopulator;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsConfigList;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsConfigPopulator;
import com.salesmanager.shop.store.api.exception.ConversionRuntimeException;

@Service("propertyRightsConfigFacade")
public class PropertyRightsConfigFacadeImpl implements PropertyRightsConfigFacade {

	@Autowired
	private ReadablePropertyRightsConfigPopulator readablePopulator;
	
	@Inject
	private PropertyRightsConfigService configService;
	
	@Inject
	private LanguageService languageService;
	
	@Inject 
	private PersistablePropertyRightsConfigPopulator persistablePopulator; 
	
	@Override
	public void registerTemplete(PersistablePropertyRightsConfig persistableConfig) throws Exception {
		// TODO Auto-generated method stub
		Validate.notNull(persistableConfig);
		PropertyRightsConfigEntity entity = persistablePopulator.populate(persistableConfig, null, languageService.defaultLanguage());
		configService.save(entity);
	}

	@Override
	public void deleteTemplete(MerchantStore store, String userName, Long id) throws Exception {
		// TODO Auto-generated method stub
		configService.deleteTemplete(store, userName, id);
	}

	@Override
	public ReadablePropertyRightsConfigList listByStoreId(Language language,
			Integer storeId) {
		// TODO Auto-generated method stub
		
		List<PropertyRightsConfigEntity> list = configService.listByStoreId(storeId);
		List<ReadablePropertyRightsConfig> readableCertificationConfigs = new ArrayList<ReadablePropertyRightsConfig>();
		ReadablePropertyRightsConfigList readableConfigList = new ReadablePropertyRightsConfigList();
		
		if(!list.isEmpty()) {
			for (PropertyRightsConfigEntity entity : list)
				readableCertificationConfigs.add(convertCertificationConfigEntityToReadableCertificationConfig(language, entity)); 
		}
		
		readableConfigList.setData(readableCertificationConfigs);
		
		return readableConfigList;
	}

	private ReadablePropertyRightsConfig convertCertificationConfigEntityToReadableCertificationConfig(Language language,
			PropertyRightsConfigEntity entity) {
		// TODO Auto-generated method stub
		ReadablePropertyRightsConfig readable = new ReadablePropertyRightsConfig();
		
		try {			
			readablePopulator.populate(entity, readable, null, language);
		} catch (Exception e) {
			throw new ConversionRuntimeException("Error while populating Certification Templet " + e.getMessage());
		}
		return readable;
		
	}

	@Override
	public void updateTemplete(Integer id, PersistablePropertyRightsConfig persistableConfig) throws Exception {
		// TODO Auto-generated method stub
		Validate.notNull(persistableConfig);
		PropertyRightsConfigEntity entity = persistablePopulator.populate(persistableConfig, null, languageService.defaultLanguage());
		configService.updateTemplete(entity);
	}

}
