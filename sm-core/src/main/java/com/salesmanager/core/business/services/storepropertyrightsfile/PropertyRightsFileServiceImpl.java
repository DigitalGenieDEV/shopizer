package com.salesmanager.core.business.services.storepropertyrightsfile;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.storepropertyrightsfile.PropertyRightsFileRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.customer.CustomerServiceImpl;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsFile;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;

@Service("propertyRightsFileService")
public class PropertyRightsFileServiceImpl extends SalesManagerEntityServiceImpl<Long, PropertyRightsFileEntity> implements PropertyRightsFileService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	
	@Inject
	private PropertyRightsFileRepository fileRepository;
	
	public PropertyRightsFileServiceImpl(JpaRepository<PropertyRightsFileEntity, Long> repository) {
		super(repository);
		// TODO Auto-generated constructor stub
		this.fileRepository = fileRepository;
	}
	
	@Override
	public void save(PropertyRightsFileEntity entity) throws ServiceException {
		// TODO Auto-generated method stub
		LOGGER.debug("Creating cetification files in templete");
		super.save(entity);
	}

	@Override
	public List<PropertyRightsFileEntity> listByTempletId(Long templetId) {
		// TODO Auto-generated method stub
		return fileRepository.listByTempletId(templetId);
	}

	@Override
	public void updateFileState(PersistablePropertyRightsFile file) {
		// TODO Auto-generated method stub
		fileRepository.updateFileState(file.getId(), file.getBaseYn(), file.getAuditSection().getModifiedBy(), file.getAuditSection().getDateModified());
	}

}
