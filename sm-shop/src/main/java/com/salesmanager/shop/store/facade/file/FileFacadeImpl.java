package com.salesmanager.shop.store.facade.file;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.services.file.FileService;
import com.salesmanager.core.model.common.file.CommonFile;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.file.facade.FileFacade;
import com.salesmanager.shop.utils.ImageFilePath;

@Service
public class FileFacadeImpl implements FileFacade {

	@Inject
	private ContentFacade contentFacade;
	
	@Inject
	private FileService fileService;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

	
	public void saveFile(int id, Map<String, MultipartFile> files, MerchantStore merchantStore)  throws Exception{
		
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;

		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();

		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
		    System.out.println("orginFileName"+orginFileName);
		    //--------------------------------------
		    // 원 파일명이 null인 경우 처리
		    //--------------------------------------
		    if (orginFileName == null) {
		    	orginFileName = "";
		    }
		    ////------------------------------------

		    //--------------------------------------
		    // 원 파일명이 없는 경우 처리
		    // (첨부가 되지 않은 input file type)
		    //--------------------------------------
		    if ("".equals(orginFileName)) {
			continue;
		    }
		    
		    merchantStore.setCode(Constants.FILE_PRG_CODE_BOARD);
		    ContentFile f = new ContentFile();
		    int index = orginFileName.lastIndexOf(".");
		    //String fileName = orginFileName.substring(0, index);
		    String fileNameWithoutExtension = orginFileName.substring(index + 1);
		    System.out.println(fileNameWithoutExtension);
			f.setContentType(file.getContentType());
			f.setName(file.getOriginalFilename());
			f.setFile(file.getBytes());
			contentFacade.addContentFile(f, merchantStore.getCode());
			
			String fileUrl = imageUtils.buildStaticImageUtils(merchantStore, f.getName());
			
			CommonFile commnFile = new CommonFile();
			commnFile.setPrgCode(Constants.FILE_PRG_CODE_BOARD);
			commnFile.setDataId(id);
			commnFile.setFileName(file.getOriginalFilename());
			commnFile.setFileUrl(fileUrl);
			commnFile.setFileType(fileNameWithoutExtension.toUpperCase());
			commnFile.setFileSize(file.getSize());
			commnFile.setDelYn("N");
			commnFile.setDownCnt(0);
			
			fileService.saveOrUpdate(commnFile);
			
		}
	}
}
