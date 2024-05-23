package com.salesmanager.shop.store.api.v1.adminmenu;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
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

import com.salesmanager.shop.model.adminmenu.PersistableAdminMenu;
import com.salesmanager.shop.model.adminmenu.ReadableAdminMenu;
import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.adminmenu.facade.AdminMenuFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "AdminMenu management resource (AdminMenu Management Api)" })
@SwaggerDefinition(tags = {
		@Tag(name = "AdminMenu management resource", description = "Administrator menu management") })
public class AdminMenuApi {

	@Inject
	private AdminMenuFacade adminMenuFacde;
	
	@Inject
	private ManagerFacade managerFacade;


	@GetMapping(value = "/private/admin/menu")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get admin by menu", notes = "")
	public ReadableAdminMenu getListAdminMenu(
			@RequestParam(value = "visible", required = false, defaultValue = "0") int visible,
			@RequestParam(value = "grpId", required = false, defaultValue = "1") int grpId , HttpServletRequest request) throws Exception {
		return adminMenuFacde.getListAdminMenu(visible, grpId);

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/admin/menu", produces = { APPLICATION_JSON_VALUE })
	public PersistableAdminMenu create(@Valid @RequestBody PersistableAdminMenu adminMenu, HttpServletRequest request)
			throws Exception {

	
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		adminMenu.setUserId(authenticatedManager);
		adminMenu.setUserIp(CommonUtils.getRemoteIp(request));

		return adminMenuFacde.saveAdminMenu(adminMenu);
	}

	@GetMapping(value = "/private/admin/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get Admin menu list for an given Admin Menu id", notes = "List current AdminMenu and child menu")
	public ReadableAdminMenu get(@PathVariable(name = "id") int adminMenuId) throws Exception {
		return adminMenuFacde.getById(adminMenuId);
	}

	@PutMapping(value = "/private/admin/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableAdminMenu update(@Valid @RequestBody PersistableAdminMenu adminMenu, HttpServletRequest request)
			throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		adminMenu.setUserId(authenticatedManager);
		adminMenu.setUserIp(CommonUtils.getRemoteIp(request));

		return adminMenuFacde.saveAdminMenu(adminMenu);
	}

	@DeleteMapping(value = "/private/admin/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		adminMenuFacde.deleteAdminMenu(id);
	}

	@PostMapping(value = "/private/admin/menu/changeOrd", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void changeOrd(@Valid @RequestBody PersistableChangeOrd adminMenu, HttpServletRequest request)
			throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		adminMenuFacde.updateChangeOrd(adminMenu, CommonUtils.getRemoteIp(request), authenticatedManager);

	}

}
