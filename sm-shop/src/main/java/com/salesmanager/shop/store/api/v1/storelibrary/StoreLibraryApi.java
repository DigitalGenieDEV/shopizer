package com.salesmanager.shop.store.api.v1.storelibrary;

import java.io.IOException;
import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.MerchantStoreCriteria;
import com.salesmanager.core.model.merchant.library.PersistableStoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibrary;
import com.salesmanager.core.model.merchant.library.StoreLibraryCriteria;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.store.ReadableMerchantStore;
import com.salesmanager.shop.model.storelibrary.ReadableStoreLibrary;
import com.salesmanager.shop.populator.storelibrary.ReadableStoreLibraryList;
import com.salesmanager.shop.store.api.exception.RestApiException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.api.v1.customer.AuthenticateCustomerApi;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.store.facade.StoreFacade;
import com.salesmanager.shop.store.controller.storelibrary.facade.StoreLibraryFacade;
import com.salesmanager.shop.utils.ImageFilePath;
import com.salesmanager.shop.utils.ServiceRequestCriteriaBuilderUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"StoreLibrary Api"})
@SwaggerDefinition(tags = {
		@Tag(name = "Customer authentication resource", description = "Authenticates customer, register customer's library files")
})
public class StoreLibraryApi {
	private static final Logger LOGGER = LoggerFactory.getLogger(AuthenticateCustomerApi.class);
	
	private static final Map<String, String> MAPPING_FIELDS = ImmutableMap.<String, String>builder()
			.put("name", "name").put("readableAudit.user", "auditSection.modifiedBy").build();
	
	@Inject
	private ContentFacade contentFacade;
	
	@Inject
	private ManagerFacade managerFacade;
	
	@Inject
	private CustomerFacade customerFacade;
	
	@Inject
	private StoreFacade storeFacade;
	
	@Inject
	private StoreLibraryFacade storeLibraryFacade;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;
	
	
	@GetMapping({"/private/storeLibrary/files", "/auth/storeLibrary/files"})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", dataType = "Long")
	  , @ApiImplicitParam(name = "keywordType", dataType = "String")
	  , @ApiImplicitParam(name = "keyword", dataType = "String")
	  , @ApiImplicitParam(name = "sortType", dataType = "String")
	})
	public ReadableStoreLibraryList libraryFileList(HttpServletRequest request,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "count", required = false) Integer count,
			@RequestParam(value = "fileType", required = false) String fileType,
			@RequestParam(value = "keywordType", required = false) String keywordType,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "sortType", required = false) String sortType,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		StoreLibraryCriteria criteria = createStoreLibraryCriteria(request);
		
		ReadableStoreLibraryList list = storeLibraryFacade.listByStoreId(criteria, language, merchantStore.getId(), fileType, page, count, sortType, keywordType, keyword);
		
		return list;
	}
	
	@PostMapping(value = {"/auth/storeLibrary/files", "/private/storeLibrary/files"}, produces ={ "application/json" })
	@ApiOperation(httpMethod = "POST", value = "Sends a request to insert libarary images", notes = "Image Library insert request is {\"username\":\"test@email.com\"}",response = ResponseEntity.class)
	public ResponseEntity<?> register(HttpServletRequest request
                                              , @RequestPart(value = "param") PersistableStoreLibrary persistableStoreLibrary
                                              , @ApiIgnore MerchantStore merchantStore
			                                  , @RequestPart List<MultipartFile> libraryFiles) throws Exception {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			for(MultipartFile file : libraryFiles) {
				
				StoreLibrary storeLibrary = new StoreLibrary(merchantStore.getId(), file.getOriginalFilename(), persistableStoreLibrary.getFileType(), userName, file.getSize());
				
				ContentFile f = new ContentFile();
				f.setContentType(file.getContentType());
				f.setName(file.getOriginalFilename());
				
				try {
					f.setFile(file.getBytes());
				} catch (IOException e) {
					throw new ServiceRuntimeException("Error while getting file bytes");
				}
				
				// 파일저장
				String fileName = contentFacade.addLibraryFile(f
						                   , merchantStore.getCode()
						                   , FileContentType.valueOf(persistableStoreLibrary.getFileContentType())
				);
				
				storeLibrary.setFileUrl(imageUtils.buildLibraryFileUtils(merchantStore, fileName));
				
				storeLibraryFacade.registerLibrary(storeLibrary);
				
			}
			
			return ResponseEntity.ok(Void.class);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().body("Exception when saving library files "+e.getMessage());
		}
	}
	
	
	
	@DeleteMapping({"/private/storeLibrary/files/{ids}", "/auth/storeLibrary/files/{ids}"})
	@ApiImplicitParams({
		@ApiImplicitParam(name = "id", dataType = "Long")
	})
	public ResponseEntity<?> deleteLibraries(HttpServletRequest request, 
			@RequestParam(value = "lang", required = false) String lang,
			@ApiIgnore MerchantStore merchantStore,
			@PathVariable("ids") String ids) {
		
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		
		try {
			String[] idAry = ids.split(",");
			for(String id : idAry) {
				storeLibraryFacade.deleteLibrary(Long.parseLong(id) , userName);
			}
			
			return ResponseEntity.ok(Void.class);
			
		} catch(Exception e) {
			return ResponseEntity.badRequest().body("Exception when reseting password "+e.getMessage());
		}

	}
	
	private StoreLibraryCriteria createStoreLibraryCriteria(HttpServletRequest request) {
		try {
			return (StoreLibraryCriteria)ServiceRequestCriteriaBuilderUtils.buildRequestCriterias(new StoreLibraryCriteria(), MAPPING_FIELDS,
					request);
		} catch (Exception e) {
			throw new RestApiException("Error while binding request parameters");
		}
	}
}
