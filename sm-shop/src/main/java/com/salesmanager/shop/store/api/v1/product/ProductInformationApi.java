package com.salesmanager.shop.store.api.v1.product;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.product.information.PersistableProductInformation;
import com.salesmanager.shop.model.catalog.product.product.information.ReadableProductInformation;
import com.salesmanager.shop.store.controller.product.facade.ProductInformationFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Product Information management resource (Product Information Api)" })
public class ProductInformationApi {
	
	@Inject
	private ProductInformationFacade productInformationFacade;
	
	
	@GetMapping(value = {"/private/product/information", "/auth/product/information"})
	@ResponseStatus(HttpStatus.OK)
	@ApiImplicitParams({
	      @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
	      @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")})
	@ApiOperation(httpMethod = "GET", value = "Get Product Information by list", notes = "")
	public ReadableProductInformation list(
					@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
					@RequestParam(value = "division", required = false, defaultValue = "0") String division,
					@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
					@RequestParam(value = "page", required = false, defaultValue = "0") Integer page
			)
			throws Exception {
		return productInformationFacade.getList(merchantStore, language, page,count,division);
	}



	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = {"/private/product/information", "/auth/product/information"}, produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
	@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public PersistableProductInformation  create(@Valid @RequestBody PersistableProductInformation data,@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletRequest request)
			throws Exception {
		
		
		return productInformationFacade.saveProductInformation(data,merchantStore,language);
	}
	
	@DeleteMapping(value = {"/private/product/information/{id}", "/auth/product/information/{id}"}, produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable Long id,HttpServletRequest request) throws Exception {
		
	
		productInformationFacade.deleteProductInformation(id);
	}
	
}
