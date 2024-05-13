package com.salesmanager.shop.store.api.v1.manager;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.shop.model.manager.ReadableManagerGroupList;
import com.salesmanager.shop.model.manager.ReadableManagerList;
import com.salesmanager.shop.store.controller.manager.facade.ManagerGroupFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/** Api for managing admin group */
@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Manager Group management resource (Manager Group Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Manager Group management resource", description = "Manager Group") })
public class ManagerGroupApi {

	@Inject
	private ManagerGroupFacade managerGroupFacade;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/manager/groups" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of manager", notes = "", response = ReadableManagerList.class)
	public ReadableManagerGroupList list(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword)  throws Exception{

		/*String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}*/

		/*UserCriteria criteria = new UserCriteria();
		if (!StringUtils.isBlank(emailAddress)) {
			criteria.setAdminEmail(emailAddress);
		}

		criteria.setStoreCode(merchantStore.getCode());

		if (!userFacade.userInRoles(authenticatedUser, Arrays.asList(Constants.GROUP_SUPERADMIN))) {
			if (!userFacade.authorizedStore(authenticatedUser, merchantStore.getCode())) {
				throw new UnauthorizedException("Operation unauthorized for user [" + authenticatedUser
						+ "] and store [" + merchantStore + "]");
			}
		}

		userFacade.authorizedGroup(authenticatedUser,
				Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN, Constants.GROUP_ADMIN_RETAIL)
						.collect(Collectors.toList()));*/
		return managerGroupFacade.getManagerGroupList(keyword, page, count);
	}
}
