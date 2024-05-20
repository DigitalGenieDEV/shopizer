package com.salesmanager.shop.store.api.v1.manager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.shop.model.manager.PersistableManagerAuthList;
import com.salesmanager.shop.model.manager.PersistableManagerGroup;
import com.salesmanager.shop.model.manager.ReadableManagerList;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerMenuAuthFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/** Api for managing manager menu auth */
@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Manager AdminMenu Auth management resource (Manager AdminMenu Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Manager AdminMenu Auth management resource", description = "Manager AdminMenu Auth") })
public class ManagerMenuAuthApi {

	@Inject
	private ManagerMenuAuthFacade managerMenuAuthFacade;
	
	@Inject
	private ManagerFacade managerFacade;
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/manager/menu/auth" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of Manager AdminMenu Auth", notes = "", response = PersistableManagerAuthList.class)
	public PersistableManagerAuthList list(@RequestParam(value = "grpId", required = false, defaultValue = "0") int grpId) throws Exception {
	
		return managerMenuAuthFacade.getManagerAdminMenuAuthList(grpId);
	}
	
	
	/**
	 * Creates a new Manager Group
	 * @param user
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = { "/private/manager/menu/auth" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "Creates a new Manager Groups", notes = "", response = PersistableManagerGroup.class)
	public PersistableManagerAuthList create(@Valid @RequestBody PersistableManagerAuthList menuAuthList, HttpServletRequest request)  throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		String userIp = CommonUtils.getRemoteIp(request);
		return managerMenuAuthFacade.create(menuAuthList, userIp);
	}
	
}
