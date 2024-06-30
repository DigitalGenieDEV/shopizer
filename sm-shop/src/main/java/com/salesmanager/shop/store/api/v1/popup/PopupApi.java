package com.salesmanager.shop.store.api.v1.popup;

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

import com.salesmanager.shop.model.banner.ReadableBannerList;
import com.salesmanager.shop.model.popup.PersistablePopup;
import com.salesmanager.shop.model.popup.ReadablePopup;
import com.salesmanager.shop.model.popup.ReadablePopupList;
import com.salesmanager.shop.model.popup.ReadableUserPopupList;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.popup.facade.PopupFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Popup management resource (Popup Management Api)" })
public class PopupApi {
	
	@Inject
	private PopupFacade popupFacade;
	
	@Inject
	private ManagerFacade managerFacade;
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/popup" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of User Popup", notes = "", response = ReadableUserPopupList.class)
	public ReadableUserPopupList userList(@RequestParam(value = "site", required = false, defaultValue = "2") String site,HttpServletRequest request) throws Exception {
		return popupFacade.getPopupUserList(site);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/popup" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of Banner", notes = "", response = ReadablePopupList.class)
	public ReadablePopupList list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "site", required = false) String site,
			@RequestParam(value = "keyword", required = false) String keyword,
			HttpServletRequest request) throws Exception {

		return popupFacade.getPopupList(site, keyword, page, count);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/popup", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public PersistablePopup create(@Valid @RequestBody PersistablePopup popup, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		popup.setUserId(authenticatedManager);
		popup.setUserIp(CommonUtils.getRemoteIp(request));

		return popupFacade.savePopup(popup);
	}
	
	
	@GetMapping(value = "/private/popup/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get Popup id list for an given Popup id", notes = "List current Popup id and child access")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of Board found", response = ReadablePopup.class) })
	public ReadablePopup get(@PathVariable(name = "id") int id) throws Exception {
		ReadablePopup data = popupFacade.getById(id);
		return data;
	}
	
	@PutMapping(value = "/private/popup/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistablePopup update(@PathVariable int id, @Valid @RequestBody PersistablePopup popup, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		popup.setId(id);
		popup.setUserId(authenticatedManager);
		popup.setUserIp(CommonUtils.getRemoteIp(request));
		return popupFacade.savePopup(popup);
	}
	
	@DeleteMapping(value = "/private/popup/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		popupFacade.deletePopup(id);
	}
}
