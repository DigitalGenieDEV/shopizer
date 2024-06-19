package com.salesmanager.core.business.services.file;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.common.file.CommonFile;

public interface FileService  extends SalesManagerEntityService<Integer, CommonFile> {
	
	void saveOrUpdate(CommonFile commonFile) throws ServiceException;
}
