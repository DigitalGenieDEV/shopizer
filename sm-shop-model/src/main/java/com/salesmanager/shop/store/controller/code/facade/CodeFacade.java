package com.salesmanager.shop.store.controller.code.facade;

import com.salesmanager.shop.model.code.PersistableCode;
import com.salesmanager.shop.model.code.ReadableCode;
import com.salesmanager.shop.model.common.PersistableChangeOrd;

public interface CodeFacade {
	
	/**
	 *
	 * @param visible
	 * @return ReadableCode
	 */
	ReadableCode getListCode(int visible) throws Exception;
	
	
	/**
	 *
	 * @param code
	 * @return PersistableCode
	 * @throws Exception
	 */
	PersistableCode saveCode(PersistableCode code) throws Exception;
	
	/**
	 *
	 * @param codeId
	 * @return ReadableCode
	 * @throws Exception
	 */
	ReadableCode getById(int codeId) throws Exception;
	
	/**
	 *
	 * @param codeId
	 * @return
	 * @throws Exception
	 */
	void deleteCode(int codeId) throws Exception;
	
	/**
	 *
	 * @param PersistableChangeOrd
	 * @param ip
	 * @return
	 * @throws Exception
	 */
	void updateChangeOrd(PersistableChangeOrd code, String ip) throws Exception;
}
