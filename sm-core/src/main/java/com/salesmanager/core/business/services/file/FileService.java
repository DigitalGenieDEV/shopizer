package com.salesmanager.core.business.services.file;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.common.file.CommonFile;
import com.salesmanager.core.model.common.file.ReadFile;

public interface FileService extends SalesManagerEntityService<Integer, CommonFile> {

	void saveOrUpdate(CommonFile commonFile) throws ServiceException;

	List<ReadFile> getFileList(int id, String prgCode) throws ServiceException;
}
