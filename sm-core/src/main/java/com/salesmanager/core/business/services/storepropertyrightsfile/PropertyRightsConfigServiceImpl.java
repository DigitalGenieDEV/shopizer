package com.salesmanager.core.business.services.storepropertyrightsfile;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.storepropertyrightsfile.PropertyRightsConfigRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerServiceImpl;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsConfigEntity;

@Service("propertyRightsConfigService")
public class PropertyRightsConfigServiceImpl extends SalesManagerEntityServiceImpl<Long, PropertyRightsConfigEntity> implements PropertyRightsConfigService {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Inject
	private PropertyRightsConfigRepository configRepository;
	
	public PropertyRightsConfigServiceImpl(JpaRepository<PropertyRightsConfigEntity, Long> repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.configRepository = configRepository;
	}
	
	@Override
	public void save(PropertyRightsConfigEntity configEntity) throws ServiceException {
		// TODO Auto-generated method stub
		LOGGER.debug("Creating cetification file's templete");
		super.save(configEntity);
	}

	@Override
	public void deleteTemplete(MerchantStore store, String userName, Long id) {
		// TODO Auto-generated method stub 
		configRepository.deleteTemplete(id, userName);
	}

	@Override
	public List<PropertyRightsConfigEntity> listByStoreId(Integer storeId) {
		// TODO Auto-generated method stub
		return configRepository.listByStoreId(storeId);
	}

	@Override
	public void updateTemplete(PropertyRightsConfigEntity configEntity) throws ServiceException {
		// TODO Auto-generated method stub
		super.update(configEntity);
	}

}
