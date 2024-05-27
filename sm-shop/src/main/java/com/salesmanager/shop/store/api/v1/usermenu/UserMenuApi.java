package com.salesmanager.shop.store.api.v1.usermenu;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.List;

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

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryList;
import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.model.entity.ListCriteria;
import com.salesmanager.shop.model.usermenu.PersistableUserMenu;
import com.salesmanager.shop.model.usermenu.ReadableUserMenu;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.usermenu.facade.UserMenuFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "UserMenu management resource (UserMenu Management Api)" })
public class UserMenuApi {
	@Inject
	private UserMenuFacade userMenuFacade;

	@Inject
	private ManagerFacade managerFacade;
	
	@GetMapping(value = "/usermenu", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get User Front by menu")
	public ReadableUserMenu list(
			@RequestParam(value = "visible", required = false, defaultValue = "0") int visible,@RequestParam(value = "parentId", required = false, defaultValue = "0") int parentId) throws Exception {
		return userMenuFacade.getListUserMenu(visible,parentId);
	}

	@GetMapping(value = "/private/user/menu")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get User by menu", notes = "")
	public ReadableUserMenu getListUserMenu(
			@RequestParam(value = "visible", required = false, defaultValue = "0") int visible,
			@RequestParam(value = "parentId", required = false, defaultValue = "0") int parentId,
			HttpServletRequest request) throws Exception {
		return userMenuFacade.getListUserMenu(visible,parentId);

	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/user/menu", produces = { APPLICATION_JSON_VALUE })
	public PersistableUserMenu create(@Valid @RequestBody PersistableUserMenu userMenu, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		userMenu.setUserId(authenticatedManager);
		userMenu.setUserIp(CommonUtils.getRemoteIp(request));

		return userMenuFacade.saveUserMenu(userMenu);
	}

	@GetMapping(value = "/private/user/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get User menu list for an given User Menu id", notes = "List current UserMenu and child menu")
	public ReadableUserMenu get(@PathVariable(name = "id") int id) throws Exception {
		return userMenuFacade.getById(id);
	}

	@PutMapping(value = "/private/user/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableUserMenu update(@Valid @RequestBody PersistableUserMenu userMenu, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		userMenu.setUserId(authenticatedManager);
		userMenu.setUserIp(CommonUtils.getRemoteIp(request));

		return userMenuFacade.saveUserMenu(userMenu);
	}

	@DeleteMapping(value = "/private/user/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		userMenuFacade.deleteUserMenu(id);
	}

	@PostMapping(value = "/private/user/menu/changeOrd", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void changeOrd(@Valid @RequestBody PersistableChangeOrd usermenu, HttpServletRequest request)
			throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		userMenuFacade.updateChangeOrd(usermenu, CommonUtils.getRemoteIp(request), authenticatedManager);

	}

}
