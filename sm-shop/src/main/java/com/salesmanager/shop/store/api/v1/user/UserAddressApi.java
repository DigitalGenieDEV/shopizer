package com.salesmanager.shop.store.api.v1.user;

import com.salesmanager.core.business.exception.ServiceException;
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

/** Api for managing admin users */
@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "User address Api" })
@SwaggerDefinition(tags = { @Tag(name = "User address resource", description = "User address resource") })
public class UserAddressApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(UserAddressApi.class);


	@Autowired
	private UserAddressFacade userAddressFacade;


	@ResponseStatus(HttpStatus.OK)
	@GetMapping({ "/auth/user/default/address/{id}" })
	@ApiOperation(httpMethod = "GET", value = "Get default address", notes = "", produces = MediaType.APPLICATION_JSON_VALUE, response = ReadableUser.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<ReadableAddress> getDefaultUserAddress( @ApiIgnore Language language, @PathVariable Long userId,
			HttpServletRequest request) throws Exception {
		try {
			return CommonResultDTO.ofSuccess(userAddressFacade.findDefaultAddressByUserId(userId, language));
		} catch (Exception e) {
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping({ "/auth/user/addresses/{id}" })
	@ApiOperation(httpMethod = "GET", value = "Get user addresses", notes = "", produces = MediaType.APPLICATION_JSON_VALUE, response = ReadableUser.class)
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<List<ReadableAddress>> getUserAddresses(@ApiIgnore Language language, @PathVariable Long userId,
													  HttpServletRequest request) throws Exception {
		try {
			return CommonResultDTO.ofSuccess(userAddressFacade.findByUserId(userId, language));
		} catch (Exception e) {
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}


	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = { "/auth/user/address" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "Creates a new user address", notes = "", response = PersistableAddress.class)
	public CommonResultDTO<ReadableAddress> create(
			@Valid @RequestBody PersistableAddress persistableAddress, HttpServletRequest request) {
		try {
			return CommonResultDTO.ofSuccess(userAddressFacade.saveOrUpdate(persistableAddress));
		} catch (ServiceException e) {
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}

	}

	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = { "/auth/user/address/{id}" })
	@ApiOperation(httpMethod = "DELETE", value = "Deletes a user address", notes = "", response = Void.class)
	public CommonResultDTO<Void> delete( @PathVariable Long id,
			HttpServletRequest request) {
		try {
			userAddressFacade.delete(id);
			return CommonResultDTO.ofSuccess();
		} catch (ServiceException e) {
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}


//	@ResponseStatus(HttpStatus.OK)
//	@PutMapping(value = { "/auth/user/default/address/{userId}" })
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
//	public CommonResultDTO<Void> updateV2(@PathVariable Long userId,
//						 @Valid @RequestBody PersistableProductDefinition product,
//						 @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
//
//		try {
//			userAddressFacade.updateDefaultAddress(userId);
//			return CommonResultDTO.ofSuccess();
//		} catch (ServiceException e) {
//			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
//		}
//	}


}
