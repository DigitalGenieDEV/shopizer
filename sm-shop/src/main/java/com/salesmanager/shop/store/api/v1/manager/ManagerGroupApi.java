package com.salesmanager.shop.store.api.v1.manager;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.shop.model.manager.PersistableManagerGroup;
import com.salesmanager.shop.model.manager.ReadableManagerGroupList;
import com.salesmanager.shop.model.manager.ReadableManagerList;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerGroupFacade;
import com.salesmanager.shop.utils.CommonUtils;

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
	

	@Inject
	private ManagerFacade managerFacade;
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/manager/groups" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of manager", notes = "", response = ReadableManagerList.class)
	public ReadableManagerGroupList list(
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			@RequestParam(value = "visible", required = false, defaultValue = "0") int visible)  throws Exception{

		return managerGroupFacade.getManagerGroupList(keyword, page, count,visible);
	}
	
	
	/**
	 * Creates a new Manager Group
	 * @param user
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = { "/private/manager/groups" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "Creates a new Manager Groups", notes = "", response = PersistableManagerGroup.class)
	public PersistableManagerGroup create(@Valid @RequestBody PersistableManagerGroup managerGroup, HttpServletRequest request)  throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		managerGroup.setUserId(authenticatedManager);
		managerGroup.setUserIp(CommonUtils.getRemoteIp(request));
		return managerGroupFacade.create(managerGroup);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = {"/private/manager/groups/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "PUT", value = "Updates a Manager Group", notes = "", response = PersistableManagerGroup.class)
	public PersistableManagerGroup update(@Valid @RequestBody PersistableManagerGroup managerGroup, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		managerGroup.setUserId(authenticatedManager);
		managerGroup.setUserIp(CommonUtils.getRemoteIp(request));
		return managerGroupFacade.update(managerGroup);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = { "/private/manager/groups/{id}"})
	@ApiOperation(httpMethod = "DELETE", value = "Deletes Managers Group", notes = "", response = Void.class)
	public void delete(@PathVariable int id,  HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		managerGroupFacade.delete(id);
	}
}
