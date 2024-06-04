package com.salesmanager.shop.store.api.v1.privacy;

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

import com.salesmanager.shop.model.privacy.PersistablePrivacy;
import com.salesmanager.shop.model.privacy.PrivacyEntity;
import com.salesmanager.shop.model.privacy.ReadablePrivacy;
import com.salesmanager.shop.model.privacy.ReadableUserPrivacy;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.privacy.facade.PrivacyFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Privacy management resource (Privacy Management Api)" })
public class PrivacyApi {
	@Inject
	private PrivacyFacade privacyFacade;
	
	@Inject
	private ManagerFacade managerFacade;
	
	@GetMapping(value = "/privacy")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get Privacy by user list", notes = "")
	public ReadableUserPrivacy getUserPrivacy( 
					@RequestParam(value = "division", required = false, defaultValue = "0") String division,
					@RequestParam(value = "id", required = false, defaultValue = "0") Integer id
			)
			throws Exception {
		return privacyFacade.getListUserPrivacy(division, id);
	}
	
	@GetMapping(value = "/private/privacy")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get Privacy by list", notes = "")
	public ReadablePrivacy getListPrivacy(@RequestParam(value = "visible", required = false, defaultValue = "0") int visible, 
					@RequestParam(value = "division", required = false, defaultValue = "0") String division,
					@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
					@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
					@RequestParam(value = "keyword", required = false) String keyword
			)
			throws Exception {
		return privacyFacade.getListPrivacy(visible, division, keyword, page,count);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/privacy", produces = { APPLICATION_JSON_VALUE })
	public PersistablePrivacy create(@Valid @RequestBody PersistablePrivacy privacy, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		privacy.setUserId(authenticatedManager);
		privacy.setUserIp(CommonUtils.getRemoteIp(request));

		return privacyFacade.savePrivacy(privacy);
	}
	
	@GetMapping(value = "/private/privacy/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get Privacy list for an given Privacy id", notes = "List current Privacy and child Privacy")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of Privacy found", response = PrivacyEntity.class) })
	public PrivacyEntity get(@PathVariable(name = "id") int id) throws Exception {
		PrivacyEntity dept = privacyFacade.getById(id);
		return dept;
	}
	
	@PutMapping(value = "/private/privacy/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistablePrivacy update(@PathVariable int id, @Valid @RequestBody PersistablePrivacy privacy,
			HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		privacy.setId(id);
		privacy.setUserId(authenticatedManager);
		privacy.setUserIp(CommonUtils.getRemoteIp(request));
		return privacyFacade.savePrivacy(privacy);
	}
	
	@DeleteMapping(value = "/private/privacy/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		privacyFacade.deletePrivacy(id);
	}

}
