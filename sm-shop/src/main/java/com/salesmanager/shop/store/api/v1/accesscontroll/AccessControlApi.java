package com.salesmanager.shop.store.api.v1.accesscontroll;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

import com.salesmanager.shop.model.accesscontrol.PersistableAccessControl;
import com.salesmanager.shop.model.accesscontrol.ReadableAccessControl;
import com.salesmanager.shop.model.accesscontrol.ReadableAccessControlList;
import com.salesmanager.shop.model.dept.ReadableDept;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.accesscontrol.facade.AccessControlFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "AccessControl management resource (AccessControl Management Api)" })
public class AccessControlApi {

	@Inject
	private AccessControlFacade accessControlFacade;
	
	@Inject
	private ManagerFacade managerFacade;
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/access" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of AccessControl", notes = "", response = ReadableAccessControlList.class)
	public ReadableAccessControlList list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "keyword", required = false) String keyword,
			HttpServletRequest request) throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		return accessControlFacade.getAccessControlList(keyword, page, count);
	}
	
	@GetMapping(value = "/private/access/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get AccessControll list for an given AccessControll id", notes = "List current AccessControll and child access")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of dept found", response = ReadableDept.class) })
	public ReadableAccessControl get(@PathVariable(name = "id") int id) throws Exception {
		ReadableAccessControl data = accessControlFacade.getById(id);
		return data;
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/access", produces = { APPLICATION_JSON_VALUE })
	public PersistableAccessControl create(@Valid @RequestBody PersistableAccessControl accessControl, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		accessControl.setUserId(authenticatedManager);
		accessControl.setUserIp(CommonUtils.getRemoteIp(request));

		return accessControlFacade.saveAccessControl(accessControl);
	}
	
	@PutMapping(value = "/private/access/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableAccessControl update(@PathVariable int id, @Valid @RequestBody PersistableAccessControl accessControl, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		accessControl.setId(id);
		accessControl.setUserId(authenticatedManager);
		accessControl.setUserIp(CommonUtils.getRemoteIp(request));
		return accessControlFacade.saveAccessControl(accessControl);
	}

	@DeleteMapping(value = "/private/access/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		accessControlFacade.deleteAccessControl(id);
	}
	
}
