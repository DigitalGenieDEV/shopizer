package com.salesmanager.shop.store.api.v1.adminmenu;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.stream.Collectors;
import java.util.stream.Stream;

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

import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.adminmenu.PersistableAdminMenu;
import com.salesmanager.shop.model.adminmenu.PersistableChangeOrdAdminMenu;
import com.salesmanager.shop.model.adminmenu.ReadableAdminMenu;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.adminmenu.facade.AdminMenuFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = {"AdminMenu management resource (AdminMenu Management Api)"})
@SwaggerDefinition(tags = {
    @Tag(name = "AdminMenu management resource", description = "Administrator menu management")
})
public class AdminMenuApi {
	
	  @Inject
	  private AdminMenuFacade adminMenuFacde;
	  
	  @Inject
	  private UserFacade userFacade;
	
	 @GetMapping(value = "/private/admin/menu")
	 @ResponseStatus(HttpStatus.OK)
	 @ApiOperation(httpMethod = "GET", value = "Get admin by menu", notes = "")
	 public ReadableAdminMenu getListAdminMenu(@RequestParam(value = "visible", required = false, defaultValue= "0") int visible) throws Exception {
		 return adminMenuFacde.getListAdminMenu(visible);
		
	}
	 
	 @ResponseStatus(HttpStatus.CREATED)
	 @PostMapping(value = "/private/admin/menu", produces = { APPLICATION_JSON_VALUE })
	 public PersistableAdminMenu create(@Valid @RequestBody PersistableAdminMenu adminMenu, HttpServletRequest request ) throws Exception {

		// superadmin
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}
		userFacade.authorizedGroup(authenticatedUser,
				Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));
		adminMenu.setUserIp(CommonUtils.getRemoteIp(request));

		return adminMenuFacde.saveAdminMenu(adminMenu);
	 }
	 
	@GetMapping(value = "/private/admin/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get Admin menu list for an given Admin Menu id", notes = "List current AdminMenu and child menu")
	public ReadableAdminMenu get(@PathVariable(name = "id") int adminMenuId) throws Exception{
		return  adminMenuFacde.getById(adminMenuId);
	} 
	 
	 @PutMapping(value = "/private/admin/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	 public PersistableAdminMenu update(@Valid @RequestBody PersistableAdminMenu adminMenu, HttpServletRequest request ) throws Exception {
		// superadmin
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}
		userFacade.authorizedGroup(authenticatedUser,
				Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));
		adminMenu.setUserIp(CommonUtils.getRemoteIp(request));
	
		return adminMenuFacde.saveAdminMenu(adminMenu);
	 }
	 
	 
	@DeleteMapping(value = "/private/admin/menu/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id) throws Exception {
		// superadmin
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}

		userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));
		adminMenuFacde.deleteAdminMenu(id);
	}
		
	 
	 @PostMapping(value = "/private/admin/menu/changeOrd", produces = { APPLICATION_JSON_VALUE })
	 @ResponseStatus(OK)
	 public void changeOrd(@Valid @RequestBody PersistableChangeOrdAdminMenu adminMenu, HttpServletRequest request ) throws Exception {
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}
		userFacade.authorizedGroup(authenticatedUser, Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));
		adminMenuFacde.updateChangeOrd(adminMenu,CommonUtils.getRemoteIp(request));
		
	 }
 	 
}
