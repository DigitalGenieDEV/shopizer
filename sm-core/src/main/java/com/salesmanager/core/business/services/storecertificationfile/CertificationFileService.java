package com.salesmanager.core.business.services.storecertificationfile;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationFile;

public interface CertificationFileService {

	void save(CertificationFileEntity entity) throws ServiceException;

	List<CertificationFileEntity> listByTempletId(Long templetId);

	void updateFileState(PersistableCertificationFile file);

}
