package com.salesmanager.shop.store.api.v1.ipSafetyCenter;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.security.Principal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.shop.model.banner.ReadableBanner;
import com.salesmanager.shop.model.common.AllDeleteIdEntity;
import com.salesmanager.shop.model.common.ChangeStateEntity;
import com.salesmanager.shop.model.ipSafetyCenter.IpSafetyCenterEntity;
import com.salesmanager.shop.model.ipSafetyCenter.PersistableIpSafetyCenter;
import com.salesmanager.shop.model.ipSafetyCenter.ReadableIpSafetyCenterList;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.ipSafetyCenter.facade.IpSafetyCenterFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.utils.CommonUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "IP Safety Center management resource (IP Safety Center Management Api)" })
public class IpSafetyCenterApi {
	
	@Inject
	private IpSafetyCenterFacade ipSafetyCenterFacde;
	
	@Inject
	private ManagerFacade managerFacade;
	
	@Inject
	private CustomerService customerService;
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/auth/ipSafetyCenter" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of IP Safety Center", notes = "", response = ReadableIpSafetyCenterList.class)
	public ReadableIpSafetyCenterList userList(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "gbn", required = false) String gbn,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "sdate", required = false) String sdate,
			@RequestParam(value = "edate", required = false) String edate,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
            return null;
        }

		return ipSafetyCenterFacde.getIpSafetyList(type,gbn, sdate,edate, keyword, page, count, customer.getEmailAddress());
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/ipSafetyCenter" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of IP Safety Center", notes = "", response = ReadableIpSafetyCenterList.class)
	public ReadableIpSafetyCenterList list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "gbn", required = false) String gbn,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "sdate", required = false) String sdate,
			@RequestParam(value = "edate", required = false) String edate,
			HttpServletRequest request) throws Exception {

		return ipSafetyCenterFacde.getIpSafetyList(type,gbn, sdate,edate, keyword, page, count, "");
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/auth/ipSafetyCenter", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
	@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public PersistableIpSafetyCenter createUser(@Valid @RequestBody PersistableIpSafetyCenter data, HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);
        
        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
            return null;
        }

		data.setUserId(customer.getEmailAddress());
		data.setUserIp(CommonUtils.getRemoteIp(request));

		return ipSafetyCenterFacde.saveIpSafeCenter(data);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/ipSafetyCenter", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
	@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public PersistableIpSafetyCenter create(@Valid @RequestBody PersistableIpSafetyCenter data, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		data.setUserId(authenticatedManager);
		data.setUserIp(CommonUtils.getRemoteIp(request));

		return ipSafetyCenterFacde.saveIpSafeCenter(data);
	}
	
	@GetMapping(value ={"/private/ipSafetyCenter/{id}","/auth/ipSafetyCenter/{id}"} , produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get IP Safety Center  list for an given IP Safety Center  id", notes = "List current IP Safety Center  and child access")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of IP Safety Center  found", response = IpSafetyCenterEntity.class) })
	public IpSafetyCenterEntity get(@PathVariable(name = "id") int id) throws Exception {
		IpSafetyCenterEntity data = ipSafetyCenterFacde.getById(id);
		return data;
	}
	
	
	@PutMapping(value = "/auth/ipSafetyCenter/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableIpSafetyCenter updateIpsafeCenter(@PathVariable int id, @Valid @RequestBody PersistableIpSafetyCenter data, HttpServletRequest request, HttpServletResponse response) throws Exception {

		Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);
        
        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
            return null;
        }
		data.setId(id);
		data.setUserId(customer.getEmailAddress());
		data.setUserIp(CommonUtils.getRemoteIp(request));
		return ipSafetyCenterFacde.saveIpSafeCenter(data);
	}
	
	@PutMapping(value = "/private/ipSafetyCenter/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableIpSafetyCenter update(@PathVariable int id, @Valid @RequestBody PersistableIpSafetyCenter data, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		data.setId(id);
		data.setUserId(authenticatedManager);
		data.setUserIp(CommonUtils.getRemoteIp(request));
		return ipSafetyCenterFacde.saveIpSafeCenter(data);
	}
	
	@DeleteMapping(value = "/auth/ipSafetyCenter/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void deleteIpSafetyCenter(@PathVariable int id,HttpServletRequest request, HttpServletResponse response) throws Exception {
		Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);
        
        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
        }
		ipSafetyCenterFacde.deleteIpSafeCenter(id);
	}
	
	@DeleteMapping(value = "/private/ipSafetyCenter/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		ipSafetyCenterFacde.deleteIpSafeCenter(id);
	}
	
	@PatchMapping(value = "/private/ipSafetyCenter/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void updateReplyContent(@PathVariable int id, @RequestBody PersistableIpSafetyCenter data, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		data.setId(id);
		data.setUserId(authenticatedManager);
		data.setUserIp(CommonUtils.getRemoteIp(request));
		ipSafetyCenterFacde.updateReplyContent(data);
	}
	
	@PatchMapping(value = "/private/ipSafetyCenter/changeState", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void updateChangeState(@RequestBody ChangeStateEntity data, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		data.setUserId(authenticatedManager);
		data.setUserIp(CommonUtils.getRemoteIp(request));
		ipSafetyCenterFacde.updateChangeState(data);
	}
	
	
	@PostMapping(value = "/private/ipSafetyCenter/allDelete", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void deleteAll(@RequestBody AllDeleteIdEntity data, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		
		ipSafetyCenterFacde.deleteAll(data);
	}

}
