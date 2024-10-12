package com.salesmanager.shop.store.api.v1.user;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.user.UserCriteria;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.catalog.product.product.definition.PersistableProductDefinition;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.model.entity.UniqueEntity;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.model.user.PersistableUser;
import com.salesmanager.shop.model.user.ReadableUser;
import com.salesmanager.shop.model.user.ReadableUserList;
import com.salesmanager.shop.model.user.UserPassword;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.api.v2.product.ProductApiV2;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.user.facade.UserAddressFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "User address Api" })
@SwaggerDefinition(tags = { @Tag(name = "User address resource", description = "User address resource") })
public class UserAddressApi {

	@Autowired
	private UserAddressFacade userAddressFacade;

	@Autowired
	private CustomerService customerService;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductApiV2.class);

	@ResponseStatus(HttpStatus.OK)
	@GetMapping({ "/auth/user/default/address" })
	@ApiOperation(httpMethod = "GET", value = "Get default address", notes = "", produces = MediaType.APPLICATION_JSON_VALUE, response = ReadableUser.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<ReadableAddress> getDefaultUserAddress(
			@RequestParam("lang") String lang,@ApiIgnore Language language,
			HttpServletRequest request) throws Exception {
		try {
			Principal userPrincipal = request.getUserPrincipal();
			String userName = userPrincipal.getName();
			Customer customer = customerService.getByNick(userName);
			if (customer == null){
				return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), ErrorCodeEnums.USER_ERROR.getErrorMessage());
			}
			return CommonResultDTO.ofSuccess(userAddressFacade.findDefaultAddressByUserId(customer.getId(), language));
		} catch (Exception e) {
			LOGGER.error("getDefaultUserAddress is error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping({ "/auth/user/addresses" })
	@ApiOperation(httpMethod = "GET", value = "Get user addresses", notes = "", produces = MediaType.APPLICATION_JSON_VALUE, response = ReadableUser.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<List<ReadableAddress>> getUserAddresses(
			@RequestParam("lang") String lang,@ApiIgnore Language language,
													  HttpServletRequest request) throws Exception {
		try {
			Principal userPrincipal = request.getUserPrincipal();
			String userName = userPrincipal.getName();
			Customer customer = customerService.getByNick(userName);
			if (customer == null){
				return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), ErrorCodeEnums.USER_ERROR.getErrorMessage());
			}
			return CommonResultDTO.ofSuccess(userAddressFacade.findByUserId(customer.getId(), language));
		} catch (Exception e) {
			LOGGER.error("getUserAddresses is error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}


	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = { "/auth/user/address" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "Creates a new user address", notes = "", response = PersistableAddress.class)
	public CommonResultDTO<ReadableAddress> create(
			@Valid @RequestBody PersistableAddress persistableAddress, HttpServletRequest request) {
		try {
			Principal userPrincipal = request.getUserPrincipal();
			String userName = userPrincipal.getName();
			Customer customer = customerService.getByNick(userName);
			if (customer == null){
				return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), ErrorCodeEnums.USER_ERROR.getErrorMessage());
			}
			persistableAddress.setUserId(customer.getId());
			return CommonResultDTO.ofSuccess(userAddressFacade.saveOrUpdate(persistableAddress));
		} catch (Exception e) {
			LOGGER.error("create user address is error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}

	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = { "/auth/user/address/{addressId}" })
	@ApiOperation(httpMethod = "DELETE", value = "Deletes a user address", notes = "", response = Void.class)
	public CommonResultDTO<Void> delete( @PathVariable Long addressId,
			HttpServletRequest request) {
		try {
			userAddressFacade.delete(addressId);
			return CommonResultDTO.ofSuccess();
		} catch (Exception e) {
			LOGGER.error("delete user address is error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}


	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = { "/auth/user/address/to/default/{addressId}" })
	public CommonResultDTO<Void> updateAddressToDefault(@PathVariable Long addressId, HttpServletRequest request) {
		try {
			Principal userPrincipal = request.getUserPrincipal();
			String userName = userPrincipal.getName();
			Customer customer = customerService.getByNick(userName);
			if (customer == null){
				return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), ErrorCodeEnums.USER_ERROR.getErrorMessage());
			}
			userAddressFacade.updateAddressToDefault(customer.getId(), addressId);
			return CommonResultDTO.ofSuccess();
		} catch (Exception e) {
			LOGGER.error("update Address To Default  is error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}


}
