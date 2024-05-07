package com.salesmanager.core.business.services.dept;

import java.util.List;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.dept.DeptRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.dept.Dept;
import com.salesmanager.core.model.dept.ReadDept;

@Service("deptervice")
@SuppressWarnings("unused")
public class DeptServiceImpl extends SalesManagerEntityServiceImpl<Integer, Dept> implements DeptService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DeptServiceImpl.class);
	private DeptRepository deptRepository;

	@Inject
	public DeptServiceImpl(DeptRepository deptRepository) {
		super(deptRepository);
		this.deptRepository = deptRepository;
	}

	public List<ReadDept> getListDept(int visible) throws ServiceException {
		return deptRepository.getListDept(visible);
	}

	public int getByCode(String code) throws ServiceException {
		return deptRepository.getByCode(code);
	}

	public int getOrder(int parentId) throws ServiceException {
		return deptRepository.getOrder(parentId);
	}

	public String getNamePath(int id) throws ServiceException {
		return deptRepository.getNamePath(id);
	}

	@Override
	public void saveOrUpdate(Dept dept) throws ServiceException {
		// save or update (persist and attach entities
		if (dept.getId() != null && dept.getId() > 0) {
			super.update(dept);
		} else {
			this.create(dept);
		}

	}

	public ReadDept getById(int id) throws ServiceException {
		ReadDept dept = deptRepository.getById(id);
		return dept;

	}

	public void deleteDept(int id) {
		deptRepository.deleteDept(id);
	}

	public void updateChangeOrd(Dept dept) throws ServiceException {
		deptRepository.updateChangeOrd(dept);
	}
}
