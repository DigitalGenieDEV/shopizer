package com.salesmanager.core.business.services.dept;

import java.util.List;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.dept.Dept;
import com.salesmanager.core.model.dept.ReadDept;

public interface DeptService extends SalesManagerEntityService<Integer, Dept>  {
	
	List<ReadDept> getListDept(int visible) throws ServiceException;
	
	int getByCode(String code) throws ServiceException;
	
	int getOrder(int parentId) throws ServiceException;
	
	String getNamePath(int id) throws ServiceException;
	
	ReadDept getById(int id) throws ServiceException;
	
	void saveOrUpdate(Dept dept) throws ServiceException;
	
	void deleteDept(int id) throws ServiceException;
	
	void updateChangeOrd(Dept dept) throws ServiceException;
	
	
}
