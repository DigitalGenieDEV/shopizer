package com.salesmanager.shop.store.api.v1.dept;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
import com.salesmanager.shop.model.dept.PersistableChangeOrdDept;
import com.salesmanager.shop.model.dept.PersistableDept;
import com.salesmanager.shop.model.dept.ReadableDept;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.dept.facade.DeptFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Dept management resource (Dept Management Api)" })
public class DeptApi {

	@Inject
	private DeptFacade deptFacde;

	@Inject
	private UserFacade userFacade;

	@GetMapping(value = "/private/dept")
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(httpMethod = "GET", value = "Get Dept by list", notes = "")
	public ReadableDept getListDept(@RequestParam(value = "visible", required = false, defaultValue = "0") int visible)
			throws Exception {
		return deptFacde.getListDept(visible);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/dept/unique" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Check if Dept code already exists", notes = "", response = EntityExists.class)
	public ResponseEntity<EntityExists> exists(@RequestParam(value = "code") String code) throws Exception {
		boolean isCategoryExist = deptFacde.existByCode(code);
		return new ResponseEntity<EntityExists>(new EntityExists(isCategoryExist), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/dept", produces = { APPLICATION_JSON_VALUE })
	public PersistableDept create(@Valid @RequestBody PersistableDept dept, HttpServletRequest request)
			throws Exception {

		// superadmin
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}
		userFacade.authorizedGroup(authenticatedUser,
				Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));
		dept.setUserIp(CommonUtils.getRemoteIp(request));

		return deptFacde.saveDept(dept);
	}

	@GetMapping(value = "/private/dept/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get dept list for an given Dept id", notes = "List current Dept and child dept")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of dept found", response = ReadableDept.class) })
	public ReadableDept get(@PathVariable(name = "id") int deptId) throws Exception {
		ReadableDept dept = deptFacde.getById(deptId);
		return dept;
	}

	@PutMapping(value = "/private/dept/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableDept update(@PathVariable int id, @Valid @RequestBody PersistableDept dept,
			HttpServletRequest request) throws Exception {
		// superadmin
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}

		userFacade.authorizedGroup(authenticatedUser,
				Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));

		dept.setId(id);
		dept.setUserIp(CommonUtils.getRemoteIp(request));
		return deptFacde.saveDept(dept);
	}

	@DeleteMapping(value = "/private/dept/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id) throws Exception {
		// superadmin
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}

		userFacade.authorizedGroup(authenticatedUser,
				Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));
		deptFacde.deleteDept(id);
	}

	@PostMapping(value = "/private/dept/changeOrd", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void changeOrd(@Valid @RequestBody PersistableChangeOrdDept dept, HttpServletRequest request)
			throws Exception {
		String authenticatedUser = userFacade.authenticatedUser();
		if (authenticatedUser == null) {
			throw new UnauthorizedException();
		}
		userFacade.authorizedGroup(authenticatedUser,
				Stream.of(Constants.GROUP_SUPERADMIN).collect(Collectors.toList()));
		deptFacde.updateChangeOrd(dept, CommonUtils.getRemoteIp(request));

	}
}
