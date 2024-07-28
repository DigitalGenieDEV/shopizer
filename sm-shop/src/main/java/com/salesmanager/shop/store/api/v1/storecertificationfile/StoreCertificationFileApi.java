package com.salesmanager.shop.store.api.v1.storecertificationfile;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigCriteria;
import com.salesmanager.core.model.merchant.certificationfile.CertificationConfigEntity;
import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationConfig;
import com.salesmanager.core.model.merchant.certificationfile.PersistableCertificationFile;
import com.salesmanager.core.model.merchant.library.PersistableStoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibraryCriteria;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationConfigList;
import com.salesmanager.shop.populator.storecertificationfile.ReadableCertificationFileList;
import com.salesmanager.shop.store.api.exception.RestApiException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.api.v1.customer.AuthenticateCustomerApi;
import com.salesmanager.shop.store.controller.cetificationconfig.facade.CetificationConfigFacade;
import com.salesmanager.shop.store.controller.cetificationfile.facade.CetificationFileFacade;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.store.facade.StoreFacade;
import com.salesmanager.shop.utils.AesUtil;
import com.salesmanager.shop.utils.CertificationFilePath;
import com.salesmanager.shop.utils.CertificationFilePathUtils;
import com.salesmanager.shop.utils.ImageFilePath;
import com.salesmanager.shop.utils.ServiceRequestCriteriaBuilderUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Store Certification File Api"})
@SwaggerDefinition(tags = {
		@Tag(name = "Customer authentication resource", description = "Authenticates customer, register customer's basic document for cetification documents")
})
public class StoreCertificationFileApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateCustomerApi.class);
	private static final Map<String, String> MAPPING_FIELDS = ImmutableMap.<String, String>builder()
			.put("name", "name").put("readableAudit.user", "auditSection.modifiedBy").build();
	
	@Inject
	private CetificationConfigFacade cetificationConfigFacade;
	
	@Inject
	private CetificationFileFacade cetificationFileFacade;
	
	@Inject
	private ContentFacade contentFacade;
	
	@Inject
	@Qualifier("certificationFile")
	private CertificationFilePath certificationFilePathUtils;
	
	@Inject
	private AesUtil aesUtil;
	
	@Inject
	private ManagerFacade managerFacade;
	
	@Inject
	private StoreFacade storeFacade;
	
	// templete list
	@GetMapping(value = {"/auth/storeCettificationFile/templetes", "/private/storeCettificationFile/templetes"}, produces ={ "application/json" })
	public ReadableCertificationConfigList templeteList(HttpServletRequest request,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		ReadableCertificationConfigList certificationConfigList = cetificationConfigFacade.listByStoreId(language, merchantStore.getId());
		return certificationConfigList;
	}
	
	@PostMapping(value = {"/auth/storeCettificationFile/templete", "/private/storeCettificationFile/templete"}, produces ={ "application/json" })
	@ApiOperation(httpMethod = "Post", value = "Sends request to create Certification Document's Templete", notes = "")
	public ResponseEntity<?> registerTemplete(HttpServletRequest request, 
			@Valid @RequestBody PersistableCertificationConfig persistableCertificationConfig, 
			@ApiIgnore MerchantStore merchantStore) throws Exception {
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			persistableCertificationConfig.setStoreId(merchantStore.getId());
			persistableCertificationConfig.getAuditSection().setModifiedBy(userName);
			persistableCertificationConfig.getAuditSection().setDateCreated(new Date());
			persistableCertificationConfig.getAuditSection().setDateModified(new Date());
			cetificationConfigFacade.registerTemplete(persistableCertificationConfig);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving templet "+e.getMessage());
		}
		
		return ResponseEntity.ok(Void.class);
	}
	
	@PutMapping(value = {"/auth/storeCettificationFile/templete/{id}", "/private/storeCettificationFile/templete/{id}"}, produces ={ "application/json" })
	public ResponseEntity<?> updateTemplete(HttpServletRequest request,
			@RequestBody PersistableCertificationConfig persistableCertificationConfig,
			@ApiIgnore MerchantStore merchantStore,
			@PathVariable("id") Long id) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		cetificationConfigFacade.updateTemplete(merchantStore.getId(), persistableCertificationConfig);
		
		// 하위 파일 설정 update
		
		return ResponseEntity.ok(Void.class);
	}
	
	@DeleteMapping(value = {"/auth/storeCettificationFile/templete/{id}", "/private/storeCettificationFile/templete/{id}"}, produces ={ "application/json" })
	public ResponseEntity<?> deleteTemplete(HttpServletRequest request,
			@PathVariable("id") Long id,
			@ApiIgnore MerchantStore merchantStore) throws Exception {
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			cetificationConfigFacade.deleteTemplete(merchantStore, userName, id);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving templet "+e.getMessage());
		}
		
		return ResponseEntity.ok(Void.class);
	}
	
	@PostMapping(value = {"/auth/storeCettificationFile/files", "/private/storeCettificationFile/files"}, produces ={ "application/json" })
	public ResponseEntity<?> registerFiles(HttpServletRequest request
            , @RequestPart(value = "param") PersistableCertificationConfig persistableCertificationConfig
            , @RequestPart(value = "fileContentType") String fileContentType
            , @RequestPart List<MultipartFile> documentList
            , @ApiIgnore MerchantStore merchantStore) throws Exception {
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			for(MultipartFile file : documentList) {
				CertificationFileEntity entity = new CertificationFileEntity(persistableCertificationConfig.getId(), file.getOriginalFilename(), userName, file.getSize());
				
				ContentFile f = new ContentFile();
				f.setContentType(file.getContentType());
				f.setName(file.getOriginalFilename());
				
				try {
					f.setFile(aesUtil.encode(file.getBytes()));
//					f.setFile(aesUtil.decode(file.getBytes()));
				} catch (IOException e) {
					throw new ServiceRuntimeException("Error while getting file bytes");
				}
				
				// 파일저장
				String fileName = contentFacade.addLibraryFile(f
															 , merchantStore.getCode()
															 , FileContentType.valueOf(fileContentType));
				
				entity.setFileUrl(certificationFilePathUtils.buildCertificationFileUtils(merchantStore, fileName));
				cetificationFileFacade.registerFiles(entity);
			
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving templet' document "+e.getMessage());
		}
		
		return ResponseEntity.ok(Void.class);
	}
	
	@GetMapping(value = {"/auth/storeCettificationFile/files", "/private/storeCettificationFile/files"}, produces ={ "application/json" })
	public ReadableCertificationFileList fileList(HttpServletRequest request, 
			@RequestParam(value = "templeteId", required = false) Long templeteId,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		ReadableCertificationFileList certificationFileList = cetificationFileFacade.listByTempletId(language, merchantStore.getId(), templeteId);
		
		return certificationFileList;
	}
		
	@PutMapping(value = {"/auth/storeCettificationFile/files", "/private/storeCettificationFile/files"}, produces ={ "application/json" })
	public ResponseEntity<?> updateFiles(HttpServletRequest request, 
			@RequestBody List<PersistableCertificationFile> persistableFileList,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			cetificationFileFacade.updateFiles(userName, merchantStore, persistableFileList);
			return ResponseEntity.ok(Void.class);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when reseting password "+e.getMessage());
		}
		
	}
	
}
