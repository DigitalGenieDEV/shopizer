package com.salesmanager.core.business.services.storepropertyrightsfile;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsFile;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;

public interface PropertyRightsFileService {

	void save(PropertyRightsFileEntity entity) throws ServiceException;

	List<PropertyRightsFileEntity> listByTempletId(Long templetId);

	void updateFileState(PersistablePropertyRightsFile file);

}
