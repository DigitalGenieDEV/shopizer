package com.salesmanager.shop.store.facade.file;

import static org.hamcrest.CoreMatchers.is;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.business.services.content.ContentService;
import com.salesmanager.core.business.services.file.FileService;
import com.salesmanager.core.model.common.file.CommonFile;
import com.salesmanager.core.model.common.file.ReadFile;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.OutputContentFile;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.controller.FilesController;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.file.facade.FileFacade;
import com.salesmanager.shop.utils.ImageFilePath;

@Service
public class FileFacadeImpl implements FileFacade {
	private static final Logger LOGGER = LoggerFactory.getLogger(FileFacadeImpl.class);
	
	@Inject
	private ContentFacade contentFacade;
	
	@Inject
	private FileService fileService;
	
	
	@Inject
	private ContentService contentService;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

	
	public void saveFile(int id, Map<String, MultipartFile> files, MerchantStore merchantStore)  throws Exception{
		
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;
		int i=1;
		while (itr.hasNext()) {
		    Entry<String, MultipartFile> entry = itr.next();

		    file = entry.getValue();
		    String orginFileName = file.getOriginalFilename();
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
			commnFile.setOrd(i);
			commnFile.setFileName(file.getOriginalFilename());
			commnFile.setFileUrl(fileUrl);
			commnFile.setFileType(fileNameWithoutExtension.toUpperCase());
			commnFile.setFileSize(file.getSize());
			commnFile.setDelYn("N");
			commnFile.setDownCnt(0);
			commnFile.setDataId(id);
			commnFile.setRegDate(new Date());
			
			fileService.saveOrUpdate(commnFile);
			
			i++;
			
		}
	}
	
	public byte[]  getById(int id,MerchantStore merchantStore)  throws Exception {
		CommonFile commnFile =  fileService.getById(id);
		if(commnFile != null) {
			// example -> /files/<store code>/myfile.css
			FileContentType fileType = FileContentType.STATIC_FILE;
			System.out.println("commnFile.getFileName()"+commnFile.getFileName());
			// needs to query the new API
			OutputContentFile file =contentService.getContentFile(merchantStore.getCode(), fileType, commnFile.getFileName());
			System.out.println("file"+file);
			
			if(file!=null) {
				return file.getFile().toByteArray();
			} else {
				LOGGER.debug("File not found " + commnFile.getFileName());
			
				return null;
			}
		}else {
			return null;
		}
       
	}
	
	public List<ReadFile> getFileList(int id) throws Exception {
		return fileService.getFileList(id, Constants.FILE_PRG_CODE_BOARD);
	}
	
	public void deleteFile(int id) throws Exception{
		CommonFile commnFile =  fileService.getById(id);
		if(commnFile != null) {
			fileService.delete(commnFile);
		}else {
			throw new ResourceNotFoundException("CommonFile with id [" + id + "] not found ");
		}
	}
}
