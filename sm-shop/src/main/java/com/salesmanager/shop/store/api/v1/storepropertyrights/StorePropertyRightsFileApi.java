package com.salesmanager.shop.store.api.v1.storepropertyrights;

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
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsConfig;
import com.salesmanager.core.model.merchant.propertyrights.PersistablePropertyRightsFile;
import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsConfigList;
import com.salesmanager.shop.populator.storepropertyrightsfile.ReadablePropertyRightsFileList;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.api.v1.customer.AuthenticateCustomerApi;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.propertyrightsconfig.facade.PropertyRightsConfigFacade;
import com.salesmanager.shop.store.controller.propertyrightsfile.facade.PropertyRightsFileFacade;
import com.salesmanager.shop.store.controller.store.facade.StoreFacade;
import com.salesmanager.shop.utils.AesUtil;
import com.salesmanager.shop.utils.CertificationFilePath;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Store Property Rights File Api"})
@SwaggerDefinition(tags = {
		@Tag(name = "Customer authentication resource", description = "Authenticates customer, register customer's basic document for property rights documents")
})
public class StorePropertyRightsFileApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateCustomerApi.class);
	private static final Map<String, String> MAPPING_FIELDS = ImmutableMap.<String, String>builder()
			.put("name", "name").put("readableAudit.user", "auditSection.modifiedBy").build();
	
	@Inject
	private PropertyRightsConfigFacade configFacade;
	
	@Inject
	private PropertyRightsFileFacade fileFacade;
	
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
	
	@GetMapping(value = {"/auth/storePropertyRightsFile/templetes", "/private/storePropertyRightsFile/templetes"}, produces ={ "application/json" })
	public ReadablePropertyRightsConfigList templeteList(HttpServletRequest request,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		ReadablePropertyRightsConfigList configList = configFacade.listByStoreId(language, merchantStore.getId());
		
		return configList;
	}
	
	@PostMapping(value = {"/auth/storePropertyRightsFile/templete", "/private/storePropertyRightsFile/templete"}, produces ={ "application/json" })
	public ResponseEntity<?> registerTemplete(HttpServletRequest request, 
			@Valid @RequestBody PersistablePropertyRightsConfig persistableConfig,
			@ApiIgnore MerchantStore merchantStore) throws Exception {
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			persistableConfig.setStoreId(merchantStore.getId());
			persistableConfig.getAuditSection().setModifiedBy(userName);
			persistableConfig.getAuditSection().setDateCreated(new Date());
			persistableConfig.getAuditSection().setDateModified(new Date());
			persistableConfig.setStoreId(merchantStore.getId());
			configFacade.registerTemplete(persistableConfig);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving templet "+e.getMessage());
		}
		
		return ResponseEntity.ok(Void.class);
	}
	
	@PutMapping(value = {"/auth/storePropertyRightsFile/templete/{id}", "/private/storePropertyRightsFile/templete/{id}"}, produces ={ "application/json" })
	public ResponseEntity<?> updateTemplete(HttpServletRequest request, 
			@RequestBody PersistablePropertyRightsConfig persistableConfig,
			@ApiIgnore MerchantStore merchantStore,
			@PathVariable("id") Long id) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		persistableConfig.setId(id);
		
		configFacade.updateTemplete(merchantStore.getId(), persistableConfig);
		
		// 하위 파일 설정 update
		
		return ResponseEntity.ok(Void.class);
	}
	
	@DeleteMapping(value = {"/auth/storePropertyRightsFile/templete/{id}", "/private/storePropertyRightsFile//templete/{id}"}, produces ={ "application/json" })
	public ResponseEntity<?> deleteTemplete(HttpServletRequest request, 
			@PathVariable("id") Long id,
			@ApiIgnore MerchantStore merchantStore) throws Exception {
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			configFacade.deleteTemplete(merchantStore, userName, id);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving templet "+e.getMessage());
		}
		
		return ResponseEntity.ok(Void.class);
	}
	
	// save documents
	@PostMapping(value = {"/auth/storePropertyRightsFile/files", "/private/storePropertyRightsFile/files"}, produces ={ "application/json" })
	public ResponseEntity<?> registerFiles(HttpServletRequest request
            , @RequestPart(value = "param") PersistablePropertyRightsConfig persistableConfig
            , @RequestPart(value = "fileContentType") String fileContentType
            , @RequestPart List<MultipartFile> documentList
            , @ApiIgnore MerchantStore merchantStore) throws Exception {
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			for(MultipartFile file : documentList) {
				PropertyRightsFileEntity entity = new PropertyRightsFileEntity(persistableConfig.getId(), file.getOriginalFilename(), userName, file.getSize());
				
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
				fileFacade.registerFiles(entity);
			
			}
			
			
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving templet' document "+e.getMessage());
		}
		
		return ResponseEntity.ok(Void.class);
	}
	
	@GetMapping(value = {"/auth/storePropertyRightsFile/files", "/private/storePropertyRightsFile/files"}, produces ={ "application/json" })
	public ReadablePropertyRightsFileList fileList(HttpServletRequest request,
			@RequestParam(value = "templeteId", required = false) Long templeteId,
			@ApiIgnore MerchantStore merchantStore, 
			@ApiIgnore Language language) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		ReadablePropertyRightsFileList fileList = fileFacade.listByTempletId(language, merchantStore.getId(), templeteId);
		
		return fileList;
	}
		
	// update document's base state
	@PutMapping(value = {"/auth/storePropertyRightsFile/files", "/private/storePropertyRightsFile/files"}, produces ={ "application/json" })
	public ResponseEntity<?> updateFiles(HttpServletRequest request,
			@RequestBody List<PersistablePropertyRightsFile> persistableFileList,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			fileFacade.updateFiles(userName, merchantStore, persistableFileList);
			
			return ResponseEntity.ok(Void.class);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when reseting password "+e.getMessage());
		}
		
	}
	
}
