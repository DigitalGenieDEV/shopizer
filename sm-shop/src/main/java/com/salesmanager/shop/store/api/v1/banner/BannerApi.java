package com.salesmanager.shop.store.api.v1.banner;

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

import com.salesmanager.shop.model.banner.PersistableBanner;
import com.salesmanager.shop.model.banner.ReadableBanner;
import com.salesmanager.shop.model.banner.ReadableBannerList;
import com.salesmanager.shop.model.banner.ReadableUserBannerList;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.banner.facade.BannerFacade;
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
@Api(tags = { "Banner management resource (Banner Management Api)" })
public class BannerApi {
	@Inject
	private BannerFacade bannerFacade;
	
	@Inject
	private ManagerFacade managerFacade;
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/banner" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of Banner", notes = "", response = ReadableBannerList.class)
	public ReadableUserBannerList userList(@RequestParam(value = "site", required = false, defaultValue = "2") String site,HttpServletRequest request) throws Exception {
		return bannerFacade.getBannerUserList(site);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/banner" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of Banner", notes = "", response = ReadableBannerList.class)
	public ReadableBannerList list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "site", required = false) String site,
			@RequestParam(value = "keyword", required = false) String keyword,
			HttpServletRequest request) throws Exception {

		return bannerFacade.getBannerList(site, keyword, page, count);
	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/banner", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public PersistableBanner create(@Valid @RequestBody PersistableBanner banner, HttpServletRequest request)
			throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		banner.setUserId(authenticatedManager);
		banner.setUserIp(CommonUtils.getRemoteIp(request));

		return bannerFacade.saveBanner(banner);
	}
	
	
	@GetMapping(value = "/private/banner/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get Banner list for an given Banner id", notes = "List current Banner and child access")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of Board found", response = ReadableBanner.class) })
	public ReadableBanner get(@PathVariable(name = "id") int id) throws Exception {
		ReadableBanner data = bannerFacade.getById(id);
		return data;
	}
	
	@PutMapping(value = "/private/banner/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableBanner update(@PathVariable int id, @Valid @RequestBody PersistableBanner banner, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		banner.setId(id);
		banner.setUserId(authenticatedManager);
		banner.setUserIp(CommonUtils.getRemoteIp(request));
		return bannerFacade.saveBanner(banner);
	}
	
	@DeleteMapping(value = "/private/banner/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		bannerFacade.deleteBanner(id);
	}
	
}
