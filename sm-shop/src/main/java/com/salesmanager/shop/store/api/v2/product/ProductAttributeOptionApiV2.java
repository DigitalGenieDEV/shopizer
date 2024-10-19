package com.salesmanager.shop.store.api.v2.product;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.attribute.DeleteProductValue;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductOptionSetValueEntity;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList2;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionList3;
import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValueList2;
import com.salesmanager.shop.store.controller.product.facade.ProductOptionFacade2;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping("/api/v2")
@Api(tags = { "Product attributes and options  / options values V2 management resource (Product Option Management Api)" })
@SwaggerDefinition(tags = {
		@Tag(name = "Product attributes and options / options values V2 management resource", description = "Edit product attributes / options and product option values") })
public class ProductAttributeOptionApiV2 {
	
	
	@Autowired
	private ProductOptionFacade2 productOptionFacade2;


	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/options" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody ReadableProductOptionList2 options(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, 
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "categoryId", required = false) int categoryId,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "1000") Integer count)  throws Exception{

		return productOptionFacade2.getListOption(merchantStore, language, name, categoryId, page, count);

	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/option/values" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody ReadableProductOptionValueList2 optionValues(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, 
			@RequestParam(value = "setId", required = false, defaultValue = "0") Integer setId,
			@RequestParam(value = "categoryId", required = false, defaultValue = "0") Integer categoryId)  throws Exception{
		return productOptionFacade2.getListOptionValues(merchantStore, language,setId,categoryId);

	}
	
	@PostMapping(value = "/private/product/option/set/delete", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void deleteSetOption(@Valid @RequestBody DeleteProductValue delOption, HttpServletRequest request) throws Exception {
		productOptionFacade2.deleteSetOption(delOption);
	}
	
	@PostMapping(value = "/private/product/option/set/values/delete", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void deleteSetValues(@Valid @RequestBody DeleteProductValue delValue, HttpServletRequest request) throws Exception {
		productOptionFacade2.deleteSetValues(delValue);
	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/optionList", "/auth/product/optionList", "product/optionList" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody ReadableProductOptionList3 optionList(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, 
			@RequestParam(value = "categoryId", required = false) int categoryId,
			@RequestParam(value = "division", required = false) String division)  throws Exception{

		return productOptionFacade2.getProductListOption(merchantStore, language,  categoryId,division);

	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/option/set/{optionId}" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody int optionSet(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, @PathVariable Long optionId)  throws Exception{

		return productOptionFacade2.getOptionSet(merchantStore, language,  optionId);

	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/option/setValue/{valueId}" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody int optionSetValue(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, @PathVariable Long valueId)  throws Exception{

		return productOptionFacade2.getOptionSetValue(merchantStore, language,  valueId);

	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/option/name/count" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody int optionNameCount(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, 	@RequestParam(value = "name", required = false) String name)  throws Exception{
		return productOptionFacade2.getOptionNameCount(merchantStore, language,  name);

	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/option/value/name/count" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody int optionValueNameCount(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, 	@RequestParam(value = "name", required = false) String name)  throws Exception{
		return productOptionFacade2.getOptionValueNameCount(merchantStore, language,  name);

	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/option/keywordList" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody ReadableProductOptionList2 optionsKeyword(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, 
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "categoryId", required = false) int categoryId
			)  throws Exception{

		return productOptionFacade2.getListOptionKeyword(merchantStore, language, keyword, categoryId);

	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/option/value/keywordList" }, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody ReadableProductOptionValueList2 optionsKeywordValue(
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, 
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "categoryId", required = false) int categoryId,
			@RequestParam(value = "setId", required = false) Integer setId
			)  throws Exception{

		return productOptionFacade2.getListOptionKeywordValues(merchantStore, language, keyword, categoryId, setId);

	}
	
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = { "/private/product/option/set/value" }, method = RequestMethod.POST)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public void createOption(
			@Valid @RequestBody PersistableProductOptionSetValueEntity data, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception{
			productOptionFacade2.insertOptionSetValue(data);
	

	}

	
}
