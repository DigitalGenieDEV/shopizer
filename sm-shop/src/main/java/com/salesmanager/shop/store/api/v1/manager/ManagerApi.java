package com.salesmanager.shop.store.api.v1.manager;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.model.entity.UniqueEntity;
import com.salesmanager.shop.model.manager.PersistableManager;
import com.salesmanager.shop.model.manager.ReadableManager;
import com.salesmanager.shop.model.manager.ReadableManagerList;
import com.salesmanager.shop.model.user.ReadableUser;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

/** Api for managing admin users */
@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Manager management resource (Manager Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Manager management resource", description = "Manager administration users") })
public class ManagerApi {

	@Inject
	private ManagerFacade managerFacade;

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/managers" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of manager", notes = "", response = ReadableManagerList.class)
	public ReadableManagerList list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "gbn", required = false) String gbn,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "deptId", required = false, defaultValue = "0") int deptId,HttpServletRequest request) throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		return managerFacade.getManagerList(gbn, keyword, deptId, page, count);
	}

	@PatchMapping(value = "/private/managers/{id}/enabled", produces = { APPLICATION_JSON_VALUE })
	public void updateEnabled(@PathVariable Long id, @Valid @RequestBody PersistableManager manager,HttpServletRequest request) throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		manager.setUserId(authenticatedManager);
		manager.setId(id);
		managerFacade.updateEnabled(manager);
	}

	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = { "/private/managers/unique" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "Check if id already exists", notes = "", response = EntityExists.class)
	public ResponseEntity<EntityExists> exists(@RequestBody UniqueEntity unique) throws Exception {

		boolean isMangerExist = false;// default manager exist
		try {
			// will throw an exception if not fount
			isMangerExist = managerFacade.isManagerExist(unique.getUnique());
			
		} catch (ResourceNotFoundException e) {
			isMangerExist = true;
		}
		return new ResponseEntity<EntityExists>(new EntityExists(isMangerExist), HttpStatus.OK);
	}

	/**
	 * Creates a new Manager
	 *
	 * @param store
	 * @param user
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = { "/private/managers/" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "POST", value = "Creates a new Managers", notes = "", response = PersistableManager.class)
	public PersistableManager create(@Valid @RequestBody PersistableManager manager, HttpServletRequest request)  throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		manager.setUserId(authenticatedManager);
		manager.setUserIp(CommonUtils.getRemoteIp(request));
		return managerFacade.create(manager);
	}
	
	/**
	 * Get userName by merchant code and userName
	 *
	 * @param storeCode
	 * @param name
	 * @param request
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@GetMapping({ "/private/managers/{id}" })
	@ApiOperation(httpMethod = "GET", value = "Get a specific Manager profile by user id", notes = "", produces = MediaType.APPLICATION_JSON_VALUE, response = ReadableUser.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Success", responseContainer = "User", response = ReadableManager.class),
			@ApiResponse(code = 400, message = "Error while getting User"),
			@ApiResponse(code = 401, message = "Login required") })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
	public ReadableManager get( @PathVariable Long id, HttpServletRequest request)  throws Exception{
		return managerFacade.getById(id);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = {"/private/managers/{id}" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "PUT", value = "Updates a Manager", notes = "", response = PersistableManager.class)
	public PersistableManager update(@Valid @RequestBody PersistableManager manager, HttpServletRequest request) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		manager.setUserId(authenticatedManager);
		manager.setUserIp(CommonUtils.getRemoteIp(request));
		return managerFacade.update( manager);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@DeleteMapping(value = { "/private/managers/{id}" })
	@ApiOperation(httpMethod = "DELETE", value = "Deletes Managers", notes = "", response = Void.class)
	public void delete(@PathVariable Long id,
			HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		managerFacade.delete(id);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = {"/private/managers/loginSuccess" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "PATCH", value = "Updates a Login Success", notes = "")
	public void updateLoginSuccess(@Valid @RequestBody PersistableManager manager) throws Exception {
		managerFacade.updateLoginDate(manager.getEmplId());
	}
}
