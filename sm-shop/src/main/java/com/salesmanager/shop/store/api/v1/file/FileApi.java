package com.salesmanager.shop.store.api.v1.file;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.cetificationfile.facade.CetificationFileFacade;
import com.salesmanager.shop.store.controller.file.facade.FileFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.utils.AesUtil;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "CommonFile management resource (CommonFile Management Api)" })
public class FileApi {
	@Inject
	private FileFacade fileFacade;
	
	@Inject
	private CetificationFileFacade cetificationFileFacade;
	
	@Inject
	private ManagerFacade managerFacade;

	@GetMapping(value = "/file/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "CommonFile Download", notes = "CommonFile ")
	public @ResponseBody byte[] get(@PathVariable(name = "id") int id,@ApiIgnore MerchantStore merchantStore) throws Exception {
		byte[] bytes = fileFacade.getById(id,merchantStore);
		return bytes;
	}
	
	@GetMapping(value = {"/auth/certification/file/{id}", "/private/certification/file/{id}"})
	@ApiOperation(httpMethod = "GET", value = "Certification Download With AES", notes = "CommonFile ")
	public @ResponseBody byte[] getCertification(@PathVariable(name = "id") Long id,@ApiIgnore MerchantStore merchantStore,
			 HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		CertificationFileEntity certificationFile = cetificationFileFacade.getById(id,merchantStore);
		FileContentType fileType = FileContentType.CERTIFICATION_INFORMATION;
		byte[] bytes = cetificationFileFacade.getContent(merchantStore, fileType, certificationFile.getStoragePath());
		if(bytes != null) {
			response.setHeader("Content-Disposition", "attachment; filename=\"" + certificationFile.getReadFileName() + "\"");
			response.setHeader("content-type", "application/octet-stream");
		} 
		return bytes;
	}
	
	@DeleteMapping(value = "/private/file/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		fileFacade.deleteFile(id);
	}
}
