package com.salesmanager.shop.store.api.v1.product;

import java.util.List;

import javax.validation.Valid;

import com.salesmanager.core.model.catalog.product.attribute.OptionSetForSaleType;
import com.salesmanager.shop.init.data.InitializationLoader;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.optionset.PersistableProductOptionSet;
import com.salesmanager.shop.model.catalog.product.attribute.optionset.ReadableProductOptionSet;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.store.controller.product.facade.ProductOptionSetFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
@Api(tags = { "Product property set regroupment management resource (Product Options Set Management Api)" })
@SwaggerDefinition(tags = {
		@Tag(name = "Product property set regroupment management resource resource", description = "Edit product property set") })
public class ProductPropertySetApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductPropertySetApi.class);

	@Autowired
	private ProductOptionSetFacade productOptionSetFacade;

	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = { "/private/product/property/set" }, method = RequestMethod.POST)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public void create(
			@Valid @RequestBody PersistableProductOptionSet optionSet, 
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		productOptionSetFacade.create(optionSet, merchantStore, language);

	}

	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/property/announcement/category" }, method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public CommonResultDTO<List<ReadableCategory>> getAnnouncementCategory(@ApiIgnore Language language) {
		try {
			List<ReadableCategory> announcementCategory = productOptionSetFacade.getAnnouncementCategory(language);
			return CommonResultDTO.ofSuccess(announcementCategory);
		}catch (Exception e){
			LOGGER.error("getAnnouncementCategory error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage());
		}
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/product/property/set/unique" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	@ApiOperation(httpMethod = "GET", value = "Check if option set code already exists", notes = "", response = EntityExists.class)
	public ResponseEntity<EntityExists> exists(
			@RequestParam(value = "code") String code,
			@ApiIgnore MerchantStore merchantStore, 
			@ApiIgnore Language language) {

		boolean isOptionExist = productOptionSetFacade.exists(code, merchantStore);
		return new ResponseEntity<EntityExists>(new EntityExists(isOptionExist), HttpStatus.OK);
	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/property/set/{id}" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	@ResponseBody
	public ReadableProductOptionSet get(
			@PathVariable Long id, 
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		return productOptionSetFacade.get(id, merchantStore, language);

	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/property/set/{id}" }, method = RequestMethod.PUT)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public void update(
			@Valid @RequestBody PersistableProductOptionSet option, 
			@PathVariable Long id,
			@ApiIgnore MerchantStore merchantStore, 
			@ApiIgnore Language language) {
		
		option.setId(id);
		productOptionSetFacade.update(id, option, merchantStore, language);

	}


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/property/set/{id}" }, method = RequestMethod.DELETE)
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public void delete(
			@PathVariable Long id,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		productOptionSetFacade.delete(id, merchantStore);

	}

	/**
	 * Get property set by store
	 * filter by product type
	 * @param merchantStore
	 * @param language
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/property/set" }, method = RequestMethod.GET)
	@ApiImplicitParams({ 
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody List<ReadableProductOptionSet> list(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			@RequestParam(value = "productType", required = false) String type) {

		if(!StringUtils.isBlank(type)) {
			return productOptionSetFacade.list(merchantStore, language, type);
		} else {
			return productOptionSetFacade.list(merchantStore, language);
		}
		
		
	}

	/**
	 * Get property set by category
	 * @return
	 */
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/property/category/set" }, method = RequestMethod.GET)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody ResponseEntity<List<ReadableProductOptionSet>> listByCategoryId(
			@ApiIgnore Language language,
			@RequestParam(value = "categoryId", required = false) Long categoryId,
			@RequestParam(value = "optionSetForSaleType", required = false) OptionSetForSaleType optionSetForSaleType) {
		return new ResponseEntity<>(productOptionSetFacade.listByCategoryId(language, categoryId, optionSetForSaleType), HttpStatus.OK);

	}
	

}