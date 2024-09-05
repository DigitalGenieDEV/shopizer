package com.salesmanager.shop.store.api.v1.product;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import com.salesmanager.shop.model.catalog.product.PersistableProductPriceDiscount;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import com.salesmanager.shop.utils.CommonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductPrice;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.store.controller.product.facade.ProductPriceFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Use inventory
 * @author carlsamson
 *
 */

@Controller
@RequestMapping("/api/v1")
@Api(tags = { "Product price api" })
@SwaggerDefinition(tags = { @Tag(name = "Product price management", description = "Edit price and discount") })
public class ProductPriceApi {

	@Autowired
	private ManagerFacade managerFacade;


	@Autowired
	private ProductPriceFacade productPriceFacade;;


	private static final Logger LOGGER = LoggerFactory.getLogger(ProductApi.class);



	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/discount"},
			method = RequestMethod.POST)
	public @ResponseBody  CommonResultDTO<Void> saveProductDiscount(
			@Valid @RequestBody PersistableProductPriceDiscount discount,
			HttpServletRequest request) {
		try {
			productPriceFacade.setProductDiscount(discount);
			return CommonResultDTO.ofSuccess();
		} catch (IllegalArgumentException e) {
			LOGGER.error("saveProductDiscount error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), e.getMessage());
		} catch (Exception e) {
			LOGGER.error("saveProductDiscount error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
		}


	}
//
//	@ResponseStatus(HttpStatus.CREATED)
//	@RequestMapping(value = { "/private/product/{sku}/price"},
//			method = RequestMethod.POST)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public @ResponseBody Entity save(
//			@PathVariable String sku,
//			@Valid @RequestBody PersistableProductPrice price,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		price.setSku(sku);
//
//		Long id = productPriceFacade.save(price, merchantStore);
//		return new Entity(id);
//
//
//	}


//	@ResponseStatus(HttpStatus.OK)
//	@RequestMapping(value = { "/private/product/{sku}/price/{priceId}"},
//			method = RequestMethod.GET)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public ReadableProductPrice get(
//			@PathVariable String sku,
//			@PathVariable Long priceId,
//			@Valid @RequestBody PersistableProductPrice price,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//
//		price.setSku(sku);
//		price.setId(priceId);
//
//		return productPriceFacade.get(sku, priceId, merchantStore, language);
//
//	}
//
//	@RequestMapping(value = { "/private/product/{sku}/inventory/{inventoryId}/price"},
//			method = RequestMethod.GET)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public List<ReadableProductPrice> list(
//			@PathVariable String sku,
//			@PathVariable Long inventoryId,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//
//		return productPriceFacade.list(sku, inventoryId, merchantStore, language);
//
//
//	}
//
//
//	@RequestMapping(value = { "/private/product/{sku}/prices"},
//			method = RequestMethod.GET)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public List<ReadableProductPrice> list(
//			@PathVariable String sku,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//
//		return productPriceFacade.list(sku, merchantStore, language);
//
//
//	}
//
//	@ResponseStatus(HttpStatus.OK)
//	@RequestMapping(value = { "/private/product/{sku}/price/{priceId}"},
//			method = RequestMethod.DELETE)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public void delete(
//			@PathVariable String sku,
//			@PathVariable Long priceId,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//
//		productPriceFacade.delete(priceId, sku, merchantStore);
//
//	}

}
