package com.salesmanager.core.business.services.code;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.code.CodeRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.code.Code;
import com.salesmanager.core.model.code.ReadCode;

@Service("codeService")
public class CodeServiceImpl extends SalesManagerEntityServiceImpl<Integer, Code> implements CodeService {

	private CodeRepository codeRepository;

	@Inject
	public CodeServiceImpl(CodeRepository codeRepository) {
		super(codeRepository);
		this.codeRepository = codeRepository;
	}

	public List<ReadCode> getListCode(int visible) throws ServiceException {
		return codeRepository.getListCode(visible);
	}

	public String getCode() throws ServiceException{
		return codeRepository.getCode();
	}
	public int getOrder(int parentId) throws ServiceException {
		return codeRepository.getOrder(parentId);
	}

	public String getNamePath(int id) throws ServiceException {
		return codeRepository.getNamePath(id);
	}

	@Override
	public void saveOrUpdate(Code code) throws ServiceException {
		// save or update (persist and attach entities
		if (code.getId() != null && code.getId() > 0) {
			super.update(code);
		} else {
			this.create(code);
		}

	}

	public ReadCode getById(int id) throws ServiceException {
		ReadCode code = codeRepository.getById(id);
		return code;
	}

	public void deleteCode(int id) {
		codeRepository.deleteCode(id);
	}

	public void updateChangeOrd(Code code) throws ServiceException {
		codeRepository.updateChangeOrd(code);
	}

}
