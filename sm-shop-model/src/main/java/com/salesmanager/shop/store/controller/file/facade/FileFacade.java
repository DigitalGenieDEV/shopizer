package com.salesmanager.shop.store.controller.file.facade;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.merchant.MerchantStore;

public interface FileFacade {
	
	void saveFile(int id, Map<String, MultipartFile> files, MerchantStore merchantStore)  throws Exception;
}
