package com.salesmanager.shop.store.controller.file.facade;

import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.common.file.ReadFile;
import com.salesmanager.core.model.merchant.MerchantStore;

public interface FileFacade {
	
	void saveFile(int id, Map<String, MultipartFile> files, MerchantStore merchantStore)  throws Exception;
	
	byte[]  getById(int id,MerchantStore merchantStore)  throws Exception;
	
	List<ReadFile> getFileList(int id) throws Exception;
	
	void deleteFile(int id) throws Exception;
}
