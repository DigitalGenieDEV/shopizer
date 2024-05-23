package com.salesmanager.shop.store.api.v1.code;

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

import com.salesmanager.shop.model.code.PersistableCode;
import com.salesmanager.shop.model.code.ReadableCode;
import com.salesmanager.shop.model.common.PersistableChangeOrd;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.code.facade.CodeFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "CommonCode management resource (CommonCode Management Api)" })
@SwaggerDefinition(tags = { @Tag(name = "CommonCode management resource", description = "CommonCode management") })
public class CodeApi {

	@Inject
	private CodeFacade codeFacde;

	@Inject
	private ManagerFacade managerFacade;

	@GetMapping(value = "/private/code")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get Code by list", notes = "")
	public ReadableCode getListCode(
			@RequestParam(value = "visible", required = false, defaultValue = "0") int visible) throws Exception {
		return codeFacde.getListCode(visible);
	}
	
	@GetMapping(value = "/private/code/detail")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get Code by Detail list", notes = "")
	public List<ReadableCode> getListCodeDetail(
			@RequestParam(value = "code", required = true, defaultValue = "") String code) throws Exception {
		return codeFacde.getListCodeDetail(code);
	}


	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/code", produces = { APPLICATION_JSON_VALUE })
	public PersistableCode create(@Valid @RequestBody PersistableCode code, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		code.setUserId(authenticatedManager);
		code.setUserIp(CommonUtils.getRemoteIp(request));

		return codeFacde.saveCode(code);
	}

	@GetMapping(value = "/private/code/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get code list for an given Code id", notes = "List current Code and child code")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of code found", response = ReadableCode.class) })
	public ReadableCode get(@PathVariable(name = "id") int codeId) throws Exception {
		ReadableCode code = codeFacde.getById(codeId);
		return code;
	}

	@PutMapping(value = "/private/code/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableCode update(@PathVariable int id, @Valid @RequestBody PersistableCode code,
			HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		code.setId(id);
		code.setUserId(authenticatedManager);
		code.setUserIp(CommonUtils.getRemoteIp(request));
		return codeFacde.saveCode(code);
	}

	@DeleteMapping(value = "/private/code/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		codeFacde.deleteCode(id);
	}

	@PostMapping(value = "/private/code/changeOrd", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void changeOrd(@Valid @RequestBody PersistableChangeOrd code, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		codeFacde.updateChangeOrd(code, CommonUtils.getRemoteIp(request), authenticatedManager);

	}
}
