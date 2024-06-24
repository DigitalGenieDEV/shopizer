package com.salesmanager.core.business.services.file;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.file.CommonFileRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.common.file.CommonFile;
import com.salesmanager.core.model.common.file.ReadFile;

@Service("fileService")
public class FileServiceImpl extends SalesManagerEntityServiceImpl<Integer, CommonFile> implements FileService {
 
	@Inject
	private CommonFileRepository commonFileRepository;

	@Inject
	public FileServiceImpl(CommonFileRepository commonFileRepository) {
		super(commonFileRepository);
		this.commonFileRepository = commonFileRepository;
	}
	
	public void saveOrUpdate(CommonFile commonFile) throws ServiceException{
		this.create(commonFile);
	}
	
	public List<ReadFile> getFileList(int id, String prgCode) throws ServiceException {
		return commonFileRepository.getFileList(id, prgCode);
	}
}
