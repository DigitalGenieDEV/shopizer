package com.salesmanager.core.business.services.code;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.code.Code;
import com.salesmanager.core.model.code.ReadCode;

public interface CodeService extends SalesManagerEntityService<Integer, Code> {

	List<ReadCode> getListCode(int visible) throws ServiceException;

	String getCode() throws ServiceException;

	int getOrder(int parentId) throws ServiceException;

	ReadCode getById(int id) throws ServiceException;

	String getNamePath(int id) throws ServiceException;

	void saveOrUpdate(Code code) throws ServiceException;

	int getMaxId() throws ServiceException;

	void deleteCode(int id) throws ServiceException;

	void updateChangeOrd(Code code) throws ServiceException;
}
