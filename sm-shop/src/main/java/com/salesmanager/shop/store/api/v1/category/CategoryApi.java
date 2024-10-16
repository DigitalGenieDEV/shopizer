package com.salesmanager.shop.store.api.v1.category;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.model.catalog.category.CategoryType;
import com.salesmanager.core.model.catalog.product.SellerProductShippingTextInfo;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.api.v2.product.ProductApiV2;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.catalog.category.PersistableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.category.ReadableCategoryList;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.model.entity.ListCriteria;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Category management resource (Category Management Api)" })
@SwaggerDefinition(
		tags = { @Tag(name = "Category management resource", description = "Manage category and attached products") }
)
public class CategoryApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(CategoryApi.class);

	private static final int DEFAULT_CATEGORY_DEPTH = 0;

	@Inject
	private CategoryFacade categoryFacade;

	@Inject
	private UserFacade userFacade;

	@Inject
	private ManagerFacade managerFacade;

	@Autowired
	private CategoryService categoryService;

	@GetMapping(value = "/private/category/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(
			httpMethod = "GET", value = "Get category list for an given Category id", notes = "List current Category and child category"
	)
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "List of category found", response = ReadableCategory.class) }
	)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCategory get(
			@PathVariable(name = "id") Long categoryId,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		ReadableCategory category = categoryFacade.getById(merchantStore, categoryId, language);
		return category;
	}

	@GetMapping(value = "/category/{friendlyUrl}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(
			httpMethod = "GET", value = "Get category list for an given Category code", notes = "List current Category and child category"
	)
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "List of category found", response = ReadableCategory.class) }
	)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCategory getByfriendlyUrl(
			@PathVariable(name = "friendlyUrl") String friendlyUrl,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) throws Exception {
		ReadableCategory category = categoryFacade.getCategoryByFriendlyUrl(merchantStore, friendlyUrl, language);
		return category;
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/category/unique" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	@ApiOperation(
			httpMethod = "GET", value = "Check if category code already exists", notes = "", response = EntityExists.class
	)
	public ResponseEntity<EntityExists> exists(
			@RequestParam(value = "code") String code,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		boolean isCategoryExist = categoryFacade.existByCode(merchantStore, code);
		return new ResponseEntity<EntityExists>(new EntityExists(isCategoryExist), HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/category/depth/list" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	@ApiOperation(httpMethod = "GET", value = "category List By Depth", notes = "", response = EntityExists.class)
	public ResponseEntity<List<ReadableCategory>> categoryListByDepth(
			@RequestParam(value = "depth") int depth,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		List<ReadableCategory> readableCategories = categoryFacade.getListByDepth(merchantStore, depth, language);
		return new ResponseEntity<List<ReadableCategory>>(readableCategories, HttpStatus.OK);
	}

	/**
	 * Get all category starting from root filter can be used for filtering on
	 * fields only featured is supported
	 *
	 * @return
	 */
	@GetMapping(value = "/category", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(
			httpMethod = "GET", value = "Get category hierarchy from root. Supports filtering FEATURED_CATEGORIES and VISIBLE ONLY by adding ?filter=[featured] or ?filter=[visible] or ? filter=[featured,visible", notes = "Does not return any product attached"
	)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCategoryList list(
			@RequestParam(value = "filter", required = false) List<String> filter,
			@RequestParam(value = "name", required = false) String name,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "type", required = false, defaultValue = "USER") String type
	) {

		ListCriteria criteria = new ListCriteria();
		criteria.setName(name);
		return categoryFacade
				.getCategoryHierarchy(merchantStore, criteria, DEFAULT_CATEGORY_DEPTH, language, filter, page, count, type);
	}

	@GetMapping(value = "/category/product/{ProductId}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get category by product", notes = "")
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCategoryList list(
			@PathVariable(name = "ProductId") Long id,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language lang
	) {

		return categoryFacade.listByProduct(merchantStore, id, lang);

	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/category/list/by/parent" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	@ApiOperation(httpMethod = "GET", value = "category List By Parent", notes = "", response = EntityExists.class)
	public ResponseEntity<List<ReadableCategory>> listByParent(
			@RequestParam(value = "categoryId") Long categoryId,
			@ApiIgnore Language language
	) throws ServiceException {
		List<ReadableCategory> readableCategories = categoryFacade.listByParent(categoryId, language);
		return new ResponseEntity<List<ReadableCategory>>(readableCategories, HttpStatus.OK);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/admin/category/by/user" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	@ApiOperation(
			httpMethod = "GET", value = "admin Category By User CategoryId", notes = "", response = EntityExists.class
	)
	public ReadableCategory adminCategoryByUserCategoryId(
			@RequestParam(value = "userCategoryId") Long userCategoryId,
			@ApiIgnore Language language
	) throws Exception {
		return categoryFacade.adminCategoryByUserCategoryId(userCategoryId, language);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/user/category", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public PersistableCategory createUser(
			@Valid @RequestBody PersistableCategory category,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@ApiIgnore HttpServletRequest request
	) throws Exception {

		// superadmin, admin and admin_catalogue
		/*
		 * String authenticatedUser = userFacade.authenticatedUser(); if
		 * (authenticatedUser == null) { throw new UnauthorizedException(); }
		 * 
		 * userFacade.authorizedGroup(authenticatedUser,
		 * Stream.of(Constants.GROUP_SUPERADMIN, Constants.GROUP_ADMIN,
		 * Constants.GROUP_ADMIN_CATALOGUE,
		 * Constants.GROUP_ADMIN_RETAIL).collect(Collectors.toList()));
		 */

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		category.setCategoryType(CategoryType.USER.name());
		return categoryFacade.saveCategory(merchantStore, category);
	}

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = "/private/category", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public PersistableCategory create(
			@Valid @RequestBody PersistableCategory category,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@ApiIgnore HttpServletRequest request
	) throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		return categoryFacade.saveCategory(merchantStore, category);
	}

	@PutMapping(value = "/private/category/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public PersistableCategory update(
			@PathVariable Long id,
			@Valid @RequestBody PersistableCategory category,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore HttpServletRequest request
	) throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		category.setId(id);
		return categoryFacade.saveCategory(merchantStore, category);
	}

	@PatchMapping(value = "/private/category/{id}/visible", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public void updateVisible(
			@PathVariable Long id,
			@Valid @RequestBody PersistableCategory category,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore HttpServletRequest request
	) throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		category.setId(id);
		categoryFacade.setVisible(category, merchantStore);
	}

	@PutMapping(value = "/private/category/{id}/move/{parent}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(
			httpMethod = "PUT", value = "Move a category under another category", notes = "Move category {id} under category {parent}"
	)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT") })
	public void move(
			@PathVariable Long id,
			@PathVariable Long parent,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore HttpServletRequest request
	) throws Exception {

		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		categoryFacade.move(id, parent, merchantStore);
		return;
	}

	@DeleteMapping(value = "/private/category/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(
			@PathVariable("id") Long categoryId,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore HttpServletRequest request
	) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}

		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());

		categoryFacade.deleteCategory(categoryId, merchantStore);
	}

	@GetMapping(value = "/category/id/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(
			httpMethod = "GET", value = "Get user category list for an given Category id", notes = "List current Category and child category"
	)
	@ApiResponses(
			value = { @ApiResponse(code = 200, message = "List of category found", response = ReadableCategory.class) }
	)
	@ApiImplicitParams(
			{ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
					@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") }
	)
	public ReadableCategory getByUser(
			@PathVariable(name = "id") Long categoryId,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language
	) {
		ReadableCategory category = categoryFacade.getById(merchantStore, categoryId, language);
		return category;
	}



	@PutMapping(value = "/private/update/category/handlingFee/{id}", produces = { APPLICATION_JSON_VALUE })
	public CommonResultDTO<Void> updateHandlingFeeById(
			@PathVariable Long id,
			@RequestParam(value = "handlingFee", required = false) String handlingFee,
			@RequestParam(value = "localHandlingFee", required = false) String localHandlingFee,
			@RequestParam(value = "handlingFeeFor1688", required = false) String handlingFeeFor1688) {
		try {
			categoryService.updateHandlingFeeById(localHandlingFee, handlingFee, handlingFeeFor1688, id);
			return CommonResultDTO.ofSuccess();
		}catch(Exception e){
			LOGGER.error("updateHandlingFeeById error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}
}
