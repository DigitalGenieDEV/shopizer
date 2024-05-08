package com.salesmanager.shop.store.controller.dept.facade;

import com.salesmanager.shop.model.dept.PersistableChangeOrdDept;
import com.salesmanager.shop.model.dept.PersistableDept;
import com.salesmanager.shop.model.dept.ReadableDept;

public interface DeptFacade {
	/**
	 *
	 * @param visible
	 * @return ReadableDept
	 */
	ReadableDept getListDept(int visible) throws Exception;

	/**
	 * Check if Dept code already exist
	 * 
	 * @param code
	 * @return
	 * @throws Exception
	 */
	boolean existByCode(String code) throws Exception;

	/**
	 *
	 * @param dept
	 * @return PersistableDept
	 * @throws Exception
	 */
	PersistableDept saveDept(PersistableDept dept) throws Exception;

	/**
	 *
	 * @param deptId
	 * @return PersistableDept
	 * @throws Exception
	 */
	ReadableDept getById(int deptId) throws Exception;

	/**
	 *
	 * @param deptId
	 * @return
	 * @throws Exception
	 */
	void deleteDept(int deptId) throws Exception;

	/**
	 *
	 * @param PersistableChangeOrdDept
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	void updateChangeOrd(PersistableChangeOrdDept dept, String ip) throws Exception;
}