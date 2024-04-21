package com.salesmanager.shop.store.api.v2.product;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.shop.model.catalog.product.ReadableProductPrice;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductVariantPrice;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductVariantValue;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableSelectedProductVariant;
import com.salesmanager.shop.model.catalog.product.feature.PersistableProductFeature;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchKeywordQueryParam;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableProductPageInfo;
import com.salesmanager.shop.populator.catalog.ReadableFinalPricePopulator;
import com.salesmanager.shop.store.controller.product.facade.AlibabaProductFacade;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.salesmanager.core.model.catalog.product.ProductCriteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.product.LightPersistableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductList;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.definition.PersistableProductDefinition;
import com.salesmanager.shop.model.catalog.product.product.definition.ReadableProductDefinition;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductDefinitionFacade;
import com.salesmanager.shop.store.controller.product.facade.ProductFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

/**
 * API to create, read, update and delete a Product API.
 *
 * @author Carl Samson
 */
@Controller
@RequestMapping("/api/v2")
@Api(tags = {
		"Product display and management resource (Product display and Management Api such as adding a product to category. Serves api v1 and v2 with backward compatibility)" })
@SwaggerDefinition(tags = {
		@Tag(name = "Product management resource, add product to category", description = "View product, Add product, edit product and delete product") })
public class ProductApiV2 {


	@Autowired
	private ProductDefinitionFacade productDefinitionFacade;
	
	@Autowired
	private ProductFacade productFacadeV2;
	
	@Autowired
	private ProductCommonFacade productCommonFacade;
	
	@Autowired
	private CategoryFacade categoryFacade;

	@Autowired
	private PricingService pricingService;

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AlibabaProductFacade alibabaProductFacade;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductApiV2.class);
	
	
	/**
	 * Create product inventory with variants, quantity and prices
	 * @param product
	 * @param merchantStore
	 * @param language
	 * @return
	 */
	@ResponseStatus(HttpStatus.CREATED)
	@RequestMapping(value = { "/private/product/inventory" }, 
			method = RequestMethod.POST)
	@ApiImplicitParams({ 
			@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public @ResponseBody Entity create(
			@Valid @RequestBody PersistableProduct product,
			@ApiIgnore MerchantStore merchantStore, 
			@ApiIgnore Language language) {

		Long id = productCommonFacade.saveProduct(merchantStore, product, language);
		Entity returnEntity = new Entity();
		returnEntity.setId(id);
		return returnEntity;

	}


	/**
	 * ------------ V2
	 * 
	 * --- product definition
	 */

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = { "/private/product" })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public @ResponseBody Entity createV2(@Valid @RequestBody PersistableProductDefinition product,
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		// make sure product id is null
		product.setId(null);
		Long id = productDefinitionFacade.saveProductDefinition(merchantStore, product, language);
		Entity returnEntity = new Entity();
		returnEntity.setId(id);
		return returnEntity;

	}

	/**
	 * ------------ V2
	 *
	 * --- product feature
	 */

	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = { "/private/feature/product/{id}" })
	public void createProductFeature(@PathVariable Long id,
						 @Valid @RequestBody PersistableProductFeature persistableProductFeature) {

	}


	@ResponseStatus(HttpStatus.OK)
	@PutMapping(value = { "/private/product/{id}" })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public void updateV2(@PathVariable Long id, 
			@Valid @RequestBody PersistableProductDefinition product,
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		productDefinitionFacade.update(id, product, merchantStore, language);

	}


	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/product/{id}" })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public @ResponseBody ReadableProductDefinition getV2(
			@PathVariable Long id, 
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) {

		ReadableProductDefinition def = productDefinitionFacade.getProduct(merchantStore, id, language);
		return def;

	}
	
	@ResponseStatus(HttpStatus.OK)
	@RequestMapping(value = { "/private/product/{id}" }, method = RequestMethod.DELETE)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public void deleteV2(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		productCommonFacade.deleteProduct(id, merchantStore);
	}
	
	/**
	 * API for getting a product
	 *
	 * @param friendlyUrl
	 * @param lang        ?lang=fr|en
	 * @param response
	 * @return ReadableProduct
	 * @throws Exception
	 *                   <p>
	 *                   /api/product/123
	 */
	@RequestMapping(value = { "/product/name/{friendlyUrl}",
			"/product/friendly/{friendlyUrl}" }, method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get a product by friendlyUrl (slug) version 2", notes = "For shop purpose. Specifying ?merchant is "
			+ "required otherwise it falls back to DEFAULT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Single product found", response = ReadableProduct.class) })
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public ReadableProduct getByfriendlyUrl(
			@PathVariable final String friendlyUrl,
			@RequestParam(value = "lang", required = false) String lang, @ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, HttpServletResponse response) throws Exception {
		
		ReadableProduct product = productFacadeV2.getProductBySeUrl(merchantStore, friendlyUrl, language);

		if (product == null) {
			response.sendError(404, "Product not fount for id " + friendlyUrl);
			return null;
		}

		return product;
	}
	

	/**
	 * List products by category
	 * count and page are supported. Default values are set when not specified
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/products/category/{friendlyUrl}", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public ReadableProductList list(
			@RequestParam(value = "lang", required = false) String lang,
			@PathVariable String friendlyUrl, 
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, // count
			@RequestParam(value = "count", required = false, defaultValue = "25") Integer count, // count
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
		
		
		
		try {
			ReadableCategory category = categoryFacade.getCategoryByFriendlyUrl(merchantStore, friendlyUrl, language);
			ProductCriteria  criterias = new ProductCriteria();
			
			List<Long> listOfIds = new ArrayList<Long>();
			listOfIds.add(category.getId());
			
			
			criterias.setCategoryIds(listOfIds);
			
			criterias.setMaxCount(count);
			criterias.setLanguage(language.getCode());
			criterias.setStartPage(page);
			
			return productFacadeV2.getProductListsByCriterias(merchantStore, language, criterias);
			
			
		} catch (ResourceNotFoundException rnf) {
			throw rnf;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			LOGGER.error("Error while getting category by friendlyUrl", e);
			throw new ServiceRuntimeException(e);
		}

	}

	

	/**
	 * List products
	 * Filtering product lists based on product option and option value ?category=1
	 * &manufacturer=2 &type=... &lang=en|fr NOT REQUIRED, will use request language
	 * &start=0 NOT REQUIRED, can be used for pagination &count=10 NOT REQUIRED, can
	 * be used to limit item count
	 *
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/products", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public ReadableProductList list(
			@RequestParam(value = "lang", required = false) String lang,
			ProductCriteria searchCriterias,

			// page
			// 0
			// ..
			// n
			// allowing
			// navigation
			@RequestParam(value = "count", required = false, defaultValue = "100") Integer count, // count
			// per
			// page
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {

		
		if (!StringUtils.isBlank(searchCriterias.getSku())) {
			searchCriterias.setCode(searchCriterias.getSku());
		}
		
		if (!StringUtils.isBlank(searchCriterias.getName())) {
			searchCriterias.setProductName(searchCriterias.getName());
		}
		
		searchCriterias.setMaxCount(count);
		searchCriterias.setLanguage(language.getCode());

		try {
			return productFacadeV2.getProductListsByCriterias(merchantStore, language, searchCriterias);

		} catch (Exception e) {
			LOGGER.error("Error while filtering products product", e);
			throw new ServiceRuntimeException(e);

		}
	}
	
	/** updates price quantity **/
	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = "/private/product/{sku}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "PATCH", value = "Update product inventory", notes = "Updates product inventory", produces = "application/json", response = Void.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
	public void update(
			@PathVariable String sku, 
			@Valid @RequestBody 
			LightPersistableProduct product,
			@ApiIgnore MerchantStore merchantStore, 
			@ApiIgnore Language language) {
		productCommonFacade.update(sku, product, merchantStore, language);
		return;

	}

	
	/**
	 * API for getting a product using sku in v2
	 *
	 * @param id
	 * @param lang     ?lang=fr|en|...
	 * @param response
	 * @return ReadableProduct
	 * @throws Exception
	 *                   <p>
	 *                   /api/products/123
	 */
	@RequestMapping(value = "/product/{sku}", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get a product by sku", notes = "For Shop purpose. Specifying ?merchant is required otherwise it falls back to DEFAULT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Single product found", response = ReadableProduct.class) })
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public ReadableProduct get(@PathVariable final String sku, 
			@RequestParam(value = "lang", required = false) String lang,
			@ApiIgnore MerchantStore merchantStore, 
			@ApiIgnore Language language) {
		ReadableProduct product = productFacadeV2.getProductByCode(merchantStore, sku, language);



		return product;
	}



	/**
	 * Calculates the price based on selected variant code
	 * @param id
	 * @param readableProductVariantPrices
	 * @param merchantStore
	 * @param language
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/product/{id}/calculate/price", method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.OK)
	@ApiOperation(
			httpMethod = "POST",
			value = "Get product price variants based on selected product",
			notes = "",
			produces = "application/json",
			response = ReadableProductPrice.class)
	@ResponseBody
	@ApiImplicitParams({
			@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
	})
	public ReadableProductPrice calculateVariantPrice(
			@PathVariable final Long id,
			@RequestBody List<ReadableProductVariantPrice> readableProductVariantPrices,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletResponse response)
			throws Exception {

		Product product = productService.getById(id);

		if (product == null) {
			response.sendError(404, "Product not fount for id " + id);
			return null;
		}

		if (CollectionUtils.isEmpty(readableProductVariantPrices)) {
			return null;
		}

		ReadableProductPrice readableProductPrice = new ReadableProductPrice();

		FinalPrice totalPrice = new FinalPrice();
		BigDecimal totalOriginalPrice = BigDecimal.ZERO;
		BigDecimal totalDiscountedPrice = BigDecimal.ZERO;
		BigDecimal totalFinalPrice = BigDecimal.ZERO;

		for (ReadableProductVariantPrice variant : readableProductVariantPrices) {
			ProductVariant productVariant = productVariantService.queryBySkuCodeAndProductId(variant.getVariantCode(), id);
			try {
				FinalPrice price = pricingService.calculateProductPrice(productVariant);

				if (price != null) {
					BigDecimal variantQuantity = BigDecimal.valueOf(variant.getQuantity());
					if (price.getOriginalPrice() != null) {
						totalOriginalPrice = totalOriginalPrice.add(price.getOriginalPrice().multiply(variantQuantity));
					}
					if (price.getDiscountedPrice() != null) {
						totalDiscountedPrice = totalDiscountedPrice.add(price.getDiscountedPrice().multiply(variantQuantity));
					}
					if (price.getFinalPrice() != null) {
						totalFinalPrice = totalFinalPrice.add(price.getFinalPrice().multiply(variantQuantity));
					}
				}

			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		totalPrice.setOriginalPrice(totalOriginalPrice);
		totalPrice.setDiscountedPrice(totalDiscountedPrice);
		totalPrice.setFinalPrice(totalFinalPrice);

		ReadableFinalPricePopulator populator = new ReadableFinalPricePopulator();
		populator.setPricingService(pricingService);
		populator.populate(totalPrice, readableProductPrice, merchantStore, language);

		return readableProductPrice;
	}



	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = "/private/product/searchProductByKeywords", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "search product by keywords",
			notes = "search product by keywords", produces = "application/json",
			response = ReadableProductPageInfo.class)
	public ReadableProductPageInfo searchProductByKeywords(
			@Valid @RequestBody
			AlibabaProductSearchKeywordQueryParam queryParam) {

		return alibabaProductFacade.searchProductByKeywords(queryParam);
	}


	@ResponseStatus(HttpStatus.OK)
	@PatchMapping(value = "/private/product/import", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "import product", notes = "import product", produces = "application/json", response = Void.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "en") })
	public void importAlibabaProduct(
			@RequestParam List<Long> productIds,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language) throws ServiceException {
		alibabaProductFacade.importProduct(productIds, language.getCode(), merchantStore);
	}


}
