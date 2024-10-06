package com.salesmanager.shop.store.api.v1.customer;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.CustomerCriteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.model.customer.PersistableCustomer;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.term.ReadableCustomerTerms;
import com.salesmanager.shop.populator.customer.ReadableCustomerList;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.term.facade.CustomerTermsFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.utils.ImageFilePath;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Customer management resource (Customer Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Customer management resource", description = "Manage customers") })
public class CustomerApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerApi.class);

	@Inject
	private CustomerFacade customerFacade;

	@Autowired
	private UserFacade userFacade;
	
	@Inject
	private ManagerFacade managerFacade;
	
	@Inject
	private CustomerTermsFacade customerTermsFacade;
	
	@Autowired
	private ContentFacade contentFacade;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

	/** Create new customer for a given MerchantStore */
	@PostMapping(value = "/private/customer", consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.MULTIPART_FORM_DATA_VALUE})
	@ApiOperation(
			httpMethod = "POST", value = "Creates a customer", notes = "Requires administration access", produces = "application/json", response = ReadableCustomer.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public ReadableCustomer create(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@Valid @RequestPart PersistableCustomer customer,
			@RequestPart MultipartFile businessRegistrationFile
	) {
		MultipartFile file = businessRegistrationFile;
		if(file != null) {
			ContentFile f = new ContentFile();
			f.setContentType(file.getContentType());
			f.setName(file.getOriginalFilename());
			try {
				f.setFile(file.getBytes());
			} catch (IOException e) {
				throw new ServiceRuntimeException("Error while getting file bytes");
			}
	
			contentFacade.addContentFile(
					f,
					merchantStore.getCode(),
					FileContentType.valueOf(customer.getFileContentType())
			);
			
			String imageValue = imageUtils.buildStaticImageUtils(
					merchantStore,
					f.getName()
			);
			
			customer.setBusinessRegistration(imageValue);
		}
				
		return customerFacade.create(customer, merchantStore, language);

	}

	@PutMapping("/private/customer/{id}")
	@ApiOperation(
			httpMethod = "PUT", value = "Updates a customer", notes = "Requires administration access", produces = "application/json", response = PersistableCustomer.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public PersistableCustomer update(
			@PathVariable Long id,
			@ApiIgnore MerchantStore merchantStore,
			@Valid @RequestBody PersistableCustomer customer
	) {

		customer.setId(id);
		return customerFacade.update(customer, merchantStore);
	}

	@PatchMapping("/private/customer/{id}/address")
	@ApiOperation(
			httpMethod = "PATCH", value = "Updates a customer", notes = "Requires administration access", produces = "application/json", response = Void.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public void updateAddress(
			@PathVariable Long id,
			@ApiIgnore MerchantStore merchantStore,
			@RequestBody PersistableCustomer customer
	) {

		customer.setId(id);
		customerFacade.updateAddress(customer, merchantStore);
	}

	@DeleteMapping("/private/customer/{id}")
	@ApiOperation(httpMethod = "DELETE", value = "Deletes a customer", notes = "Requires administration access")
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public void delete(
			@PathVariable Long id,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore HttpServletRequest request
	) throws Exception {

		// superadmin, admin and admin_catalogue
		/*
		 * String authenticatedUser = userFacade.authenticatedUser(); if
		 * (authenticatedUser == null) { throw new UnauthorizedException(); }
		 * 
		 * userFacade.authorizedGroup(authenticatedUser,
		 * Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
		 * Constants.GROUP_ADMIN_CATALOGUE,
		 * Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
		 */

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		customerFacade.deleteById(id);
	}

	/**
	 * Get all customers
	 *
	 * @param start
	 * @param count
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@GetMapping("/private/customers")
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCustomerList list(
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "count", required = false) Integer count,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		CustomerCriteria customerCriteria = createCustomerCriteria(page, count);
		return customerFacade.getListByStore(merchantStore, customerCriteria, language);
	}

	private CustomerCriteria createCustomerCriteria(
			Integer start,
			Integer count
	) {
		CustomerCriteria customerCriteria = new CustomerCriteria();
		Optional.ofNullable(start).ifPresent(customerCriteria::setStartIndex);
		Optional.ofNullable(count).ifPresent(customerCriteria::setMaxCount);
		return customerCriteria;
	}

	@GetMapping("/private/customer/{id}")
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCustomer get(
			@PathVariable Long id,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		LOGGER.info("METHOD: GET, PATH : /private/customer/{id}");
		return customerFacade.getCustomerById(id, merchantStore, language);
	}

	/**
	 * Get logged in customer profile
	 * 
	 * @param merchantStore
	 * @param language
	 * @param request
	 * @return
	 */
	@GetMapping({ "/private/customer/profile", "/auth/customer/profile" })
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCustomer getAuthUser(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletRequest request
	) {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		/*
		ReadableCustomer result = customerFacade.getCustomerByNick(userName, merchantStore, language);
		String fileName = result.getBusinessRegistration();
		result.setBusinessRegistrationFile(contentFacade.download(merchantStore, null, fileName));
		*/
		return customerFacade.getCustomerByNick(userName, merchantStore, language);
	}

	@PatchMapping("/auth/customer/address")
	@ApiOperation(
			httpMethod = "PATCH", value = "Updates a loged in customer address", notes = "Requires authentication", produces = "application/json", response = Void.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public void updateAuthUserAddress(
			@ApiIgnore MerchantStore merchantStore,
			@RequestBody PersistableCustomer customer,
			HttpServletRequest request
	) {
		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();

		customerFacade.updateAddress(userName, customer, merchantStore);

	}

	@PatchMapping("/auth/customer/")
	@ApiOperation(
			httpMethod = "PATCH", value = "Updates a loged in customer profile", notes = "Requires authentication", produces = "application/json", response = PersistableCustomer.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public PersistableCustomer update(
			@ApiIgnore MerchantStore merchantStore,
			@Valid @RequestBody PersistableCustomer customer,
			HttpServletRequest request
	) {

		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();

		return customerFacade.update(userName, customer, merchantStore);
	}

	@DeleteMapping({"/auth/customer/", "/private/customer"})
	@ApiOperation(
			httpMethod = "DELETE", value = "Deletes a loged in customer profile", notes = "Requires authentication", produces = "application/json", response = Void.class
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public void delete(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@RequestParam(value="withdrawalReason") List<String> withdrawalReason,
			@RequestParam(defaultValue = "") String withdrawalReasonDetail,			
			HttpServletRequest request
	) {

		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();

		Customer customer;
		try {
			customer = customerFacade.getCustomerByUserName(userName);
			if (customer == null) {
				throw new ResourceNotFoundException("Customer [" + userName + "] not found");
			}
			customer.setWithdrawalReason(new HashSet<>(withdrawalReason));
			customer.setWithdrawalResonDetail(withdrawalReasonDetail);
			customer.setWithdrawalAt(LocalDateTime.now());
			
			customerFacade.delete(customer);
		} catch (Exception e) {
			throw new ServiceRuntimeException("An error occured while deleting customer [" + userName + "]");
		}
	}
	
	@GetMapping(value = { "/auth/customer/{id}/terms", "/customer/{id}/terms" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get Customer Terms", notes = "", response = ReadableCustomerTerms.class, responseContainer = "List")
	public List<ReadableCustomerTerms> customerTerms(
			@PathVariable Long id,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
			) {
		
		List<ReadableCustomerTerms> values =  customerTermsFacade.findByCustomerId(id, merchantStore, language);
		return values;
	}
}
