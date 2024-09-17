package com.salesmanager.shop.store.api.v2.product;

import static com.salesmanager.core.model.catalog.product.ProductCriteria.ORIGIN_ADMIN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.math.BigDecimal;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.AnnouncementInfo;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.*;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import com.salesmanager.core.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.catalog.product.variant.ProductVariant;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.feature.ProductFeatureType;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.shop.model.catalog.PersistableMaterial;
import com.salesmanager.shop.model.catalog.ReadableMaterial;
import com.salesmanager.shop.model.catalog.product.*;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductVariantPrice;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductVariantValue;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableSelectedProductVariant;
import com.salesmanager.shop.model.catalog.product.feature.PersistableProductFeature;
import com.salesmanager.shop.model.catalog.product.product.PersistableSimpleProductUpdateReq;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchKeywordQueryParam;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableProductPageInfo;
import com.salesmanager.shop.model.references.ReadableAddress;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.populator.catalog.ReadableFinalPricePopulator;
import com.salesmanager.shop.store.controller.erp.facade.ErpFacade;
import com.salesmanager.shop.store.controller.product.facade.*;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import com.salesmanager.shop.utils.UniqueIdGenerator;
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

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.category.ReadableCategory;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.definition.PersistableProductDefinition;
import com.salesmanager.shop.model.catalog.product.product.definition.ReadableProductDefinition;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.category.facade.CategoryFacade;

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
	private ErpFacade erpFacade;

	@Autowired
	private ProductVariantService productVariantService;

	@Autowired
	private ProductService productService;

	@Autowired
	private AlibabaProductFacade alibabaProductFacade;

	@Autowired
	private CategoryService categoryService;

	@Autowired
	private CountryService countryService;

	@Autowired
	private CustomerService customerService;

	@Autowired
	private SellerTextInfoFacade sellerTextInfoFacade;

	private static final Logger LOGGER = LoggerFactory.getLogger(ProductApiV2.class);
	

	/**
	 * ------------ V2
	 * 
	 * --- product definition
	 */

	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = { "/product"})
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody Entity createV2(@Valid @RequestBody PersistableProduct product,
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) throws ServiceException {

		PersistableProductDefinition persistableProductDefinition = new PersistableProductDefinition();
		persistableProductDefinition.setIdentifier(product.getIdentifier());

		// Check if product exists based on identifier or SKU, or if it's a new product
		if (StringUtils.isNotBlank(product.getIdentifier())) {
			if (!productService.existsByProductIdentifier(product.getIdentifier())) {
				product.setSku(null);
				product.setIdentifier(null);
			}
		} else if (StringUtils.isNotBlank(product.getSku())) {
			if (!productService.existsByProductIdentifier(product.getIdentifier())) {
				product.setSku(null);
				product.setIdentifier(null);
			}
		} else if (product.getId() != null && product.getId() > 0) {
			Product productByMySql = productService.getProductWithOnlyMerchantStoreById(product.getId());
			if (productByMySql != null) {
				product.setSku(null);
				product.setIdentifier(null);
			}
		}

		Long productId = product.getId();
		if (productId == null) {
			productId = productDefinitionFacade.saveProductDefinition(merchantStore, persistableProductDefinition, language);
		}

		product.setId(productId);
		productId = productCommonFacade.saveProduct(merchantStore, product, language);

		Entity returnEntity = new Entity();
		returnEntity.setId(productId);

		return returnEntity;
	}


	@PutMapping(value = { "/private/product/simple/update"})
	public @ResponseBody CommonResultDTO<Long> productSimpleUpdate(
			@Valid @RequestBody PersistableSimpleProductUpdateReq product) throws ServiceException {
		try {
			return CommonResultDTO.ofSuccess(productCommonFacade.simpleUpdateProduct(product));
		}catch (Exception e){
			LOGGER.error("productSimpleUpdate error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@PutMapping(value = { "/private/product/documents/update"})
	public @ResponseBody CommonResultDTO<Long> productDocumentsUpdate(
			@Valid @RequestBody PersistableSimpleProductUpdateReq product) throws ServiceException {
		try {
			productService.updateProductDocuments(product.getProductId(), product.getCertificationDocument(), product.getIntellectualPropertyDocuments());
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("productSimpleUpdate error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}



	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = { "/auth/product"})
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public @ResponseBody Entity createBySeller(@Valid @RequestBody PersistableProduct product,
										 @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) throws ServiceException {

		PersistableProductDefinition persistableProductDefinition = new PersistableProductDefinition();
		persistableProductDefinition.setIdentifier(product.getIdentifier());
		// make sure product id is null
		Product productByMySql = null;
		if(StringUtils.isNotBlank(product.getIdentifier())){
			productByMySql = productService.getBySku(product.getIdentifier(), merchantStore);
		}
		if (productByMySql == null && (product.getId() !=null && product.getId().longValue() >0)){
			productByMySql = productService.getProductWithOnlyMerchantStoreById(product.getId());
		}
		Long productId = null;
		if (productByMySql == null){
			productId = productDefinitionFacade.saveProductDefinition(merchantStore, persistableProductDefinition, language);
		}else{
			productId = productByMySql.getId();
		}
		product.setId(productId);
		productCommonFacade.saveProduct(merchantStore, product, language);
		Entity returnEntity = new Entity();
		returnEntity.setId(productId);
		return returnEntity;
	}



	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = { "/product/delete/{id}" })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public void deleteV2(@PathVariable Long id, @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
		productCommonFacade.deleteProduct(id, merchantStore);
	}
	

	/**
	 * query Product Count By Category
	 * @param lang
	 * @param merchantStore
	 * @param categoryIds
	 * @return
	 */
	@RequestMapping(value = "/private/product/count/by/category", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public Long queryProductCountByCategory(
			@RequestParam(value = "lang", required = false) String lang,
			@ApiIgnore MerchantStore merchantStore,
			@RequestParam(value = "categoryIds") List<Long> categoryIds) {
		ProductCriteria searchCriterias = new ProductCriteria();
		searchCriterias.setCategoryIds(categoryIds);
		searchCriterias.setMaxCount(10);

		try {
			List<Long> ids = new ArrayList<Long>();

			if (categoryIds.size() == 1) {
				com.salesmanager.core.model.catalog.category.Category category = categoryService
						.getById(categoryIds.get(0));

				if (category != null) {
					String lineage = new StringBuilder().append(category.getLineage())
							.toString();

					List<com.salesmanager.core.model.catalog.category.Category> categories = categoryService
							.getListByLineage(lineage);

					if (categories != null && categories.size() > 0) {
						for (com.salesmanager.core.model.catalog.category.Category c : categories) {
							ids.add(c.getId());
						}
					}
					ids.add(category.getId());
				}
			}
			return productService.countProductsByCategoryIds(ids);
		} catch (Exception e) {
			LOGGER.error("Error while filtering product count", e);
			return 0L;
		}
	}


	/**
	 * for admin
	 * @param productId
	 * @param lang
	 * @param merchantStore
	 * @param language
	 * @return
	 */
	@RequestMapping(value = {"/private/product/id/{productId}","/auth/product/id/{productId}"}, method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get a product by productId", notes = "For Shop purpose. Specifying ?merchant is required otherwise it falls back to DEFAULT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Single product found", response = ReadableProduct.class) })
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public ReadableProduct getByProductIdForAdmin(@PathVariable Long productId,
										  @RequestParam(value = "lang", required = false) String lang,
										  @ApiIgnore MerchantStore merchantStore,
										  @ApiIgnore Language language) {
		ReadableProduct product = new ReadableProduct();
		try {
			 product = productFacadeV2.getProductByIdForAdmin(productId, merchantStore, language);
			 product.setStoreName(merchantStore.getStorename());
		}catch (Exception e){
			LOGGER.error("Error  to get Product By Id For Admin", e);
		}
		return product;
	}


	/**
	 * for user
	 * @param productId
	 * @param lang
	 * @param language
	 * @return
	 */
	@RequestMapping(value = "/product/id/{productId}", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get a product by productId", notes = "For Shop purpose. Specifying ?merchant is required otherwise it falls back to DEFAULT")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Single product found", response = ReadableProduct.class) })
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public ReadableProduct getByProductIdForUser(@PathVariable Long productId,
							   @RequestParam(value = "lang", required = false) String lang,
							   @ApiIgnore Language language) {
		long startTime = System.currentTimeMillis();
		ReadableProduct product = productFacadeV2.getProductById(productId, language);
		product.setProductAuditStatus(null);
		long endTime = System.currentTimeMillis();
		System.out.println("商品查询方法执行时长:" + (endTime - startTime));
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
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
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
	@ResponseBody
	@RequestMapping(value = "/private/product/searchProductByKeywords", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "search product by keywords",
			notes = "search product by keywords", produces = "application/json",
			response = ReadableProductPageInfo.class)
	public ReadableProductPageInfo searchProductByKeywords(
			@Valid @RequestBody
			AlibabaProductSearchKeywordQueryParam queryParam) throws ServiceException {
		return alibabaProductFacade.searchProductByKeywords(queryParam);
	}



	@RequestMapping(value = "/private/exist/product/{productIdBy1688}", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "1688 product is exist", notes = "1688 product is exist")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Single product found", response = Boolean.class) })
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public CommonResultDTO<Boolean> productExistBy1688ProductId( @PathVariable Long productIdBy1688) {
		try {
			Product byOutId = productService.findByOutId(productIdBy1688);
			if (byOutId == null){
				return CommonResultDTO.ofSuccess(false);
			}
			return CommonResultDTO.ofSuccess(true);
		}catch (Exception e){
			LOGGER.error("importAlibabaProduct error", e);
			return CommonResultDTO.ofFailed("20001", "importAlibabaProduct error");
		}
	}



	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PostMapping(value = "/private/product/import", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "import product", notes = "import product", produces = "application/json", response = List.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public CommonResultDTO<List<Long>> importAlibabaProduct(
			@RequestParam(value = "productIds") List<Long> productIds,
			@RequestParam(value = "leftCategoryId") Long leftCategoryId,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language){
		try {
			List<Long> longs = alibabaProductFacade.importProduct(productIds, language.getCode(),
					merchantStore, leftCategoryId == null ? null : Lists.newArrayList(leftCategoryId), PublishWayEnums.IMPORT_BY_1688);
			return CommonResultDTO.ofSuccess(longs);
		}catch (Exception e){
			LOGGER.error("importAlibabaProduct error", e);
			return CommonResultDTO.ofFailed("20001", "importAlibabaProduct error");
		}
	}


	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PostMapping(value = "/private/product/count", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "product count", notes = "product count", produces = "application/json", response = Integer.class)
	public Integer productCountNum() {
		return productService.countProduct();
	}

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PostMapping(value = "/private/product/audit", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "product audit", notes = "product Audit", produces = "application/json", response = Void.class)
	public void productAudit(
			@RequestParam(value = "productAudit") String productAudit,
			@RequestParam(value = "productId") Long productId) {
		productService.updateProductAuditStatusById(productAudit, productId);
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
	@RequestMapping(value = "/private/products", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public ReadableProductList list(
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "category", required = false) Long category,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "sku", required = false) String sku,
			@RequestParam(value = "manufacturer", required = false) Long manufacturer,
			@RequestParam(value = "optionValues", required = false) List<Long> optionValueIds,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "owner", required = false) Long owner,
			@RequestParam(value = "productType", required = false) String productType,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, // current
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count, // count
			@RequestParam(value = "slug", required = false) String slug, // category slug
			@RequestParam(value = "available", required = false) Boolean available,
			@RequestParam(value = "sellerCountryCode", required = false) String sellerCountryCode,
			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		ProductCriteria criteria = new ProductCriteria();

		criteria.setOrigin(ORIGIN_ADMIN);

		// do not use legacy pagination anymore
		if (lang != null) {
			criteria.setLanguage(lang);
		} else {
			criteria.setLanguage(language.getCode());
		}
		if (!StringUtils.isBlank(status)) {
			criteria.setStatus(status);
		}
		if (StringUtils.isNotEmpty(sellerCountryCode)){
			Country country = countryService.getByCode(sellerCountryCode);
			criteria.setSellerCountryCode(country.getId());
		}
		// Start Category handling
		List<Long> categoryIds = new ArrayList<Long>();
		if (slug != null) {
			Category categoryBySlug = categoryService.getBySeUrl(merchantStore, slug, language);
			categoryIds.add(categoryBySlug.getId());
		}
		if (category != null) {
			categoryIds.add(category);
		}
		if (categoryIds.size() > 0) {
			criteria.setCategoryIds(categoryIds);
		}
		// End Category handling

		if (available != null && available) {
			criteria.setAvailable(available);
		}

		if (manufacturer != null) {
			criteria.setManufacturerId(manufacturer);
		}

		if (CollectionUtils.isNotEmpty(optionValueIds)) {
			criteria.setOptionValueIds(optionValueIds);
		}
		if (StringUtils.isNotBlank(sellerCountryCode)){
			criteria.setPublishWay(PublishWayEnums.SELLER.name());
		}
		if (StringUtils.isNotBlank(productType)){
			criteria.setPublishWay(PublishWayEnums.IMPORT_BY_1688.name());
		}


		if (owner != null) {
			criteria.setOwnerId(owner);
		}

		if (page != null) {
			criteria.setStartPage(page);
		}

		if (count != null) {
			criteria.setMaxCount(count);
		}

		if (!StringUtils.isBlank(name)) {
			criteria.setProductName(name);
		}

		if (!StringUtils.isBlank(sku)) {
			criteria.setCode(sku);
		}

		try {
			long start = System.currentTimeMillis();
			ReadableProductList productSimpleListsByCriterias = productFacadeV2.getProductSimpleListsByCriterias(null, language, criteria);
			long end = System.currentTimeMillis();
			System.out.println("执行时间："+(end - start));
			return productSimpleListsByCriterias;
		} catch (Exception e) {

			LOGGER.error("Error while filtering products product", e);
			try {
				response.sendError(503, "Error while filtering products " + e.getMessage());
			} catch (Exception ignore) {
			}

			return null;
		}
	}



	@RequestMapping(value = "/private/main/display/products", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public ReadableProductList getMainDisplayManagementList(
			@RequestParam(value = "tag", required = true) String tag,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "category", required = false) Long category,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "identifier", required = false) String identifier,
			@RequestParam(value = "sortField", required = false) String sortField,
			@RequestParam(value = "sortDirection", required = false) String sortDirection,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, // current
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count, // count
			@RequestParam(value = "companyName", required = false) String companyName,
			@ApiIgnore Language language, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductCriteria criteria = new ProductCriteria();

		criteria.setOrigin(ORIGIN_ADMIN);

		// do not use legacy pagination anymore
		if (lang != null) {
			criteria.setLanguage(lang);
		} else {
			criteria.setLanguage(language.getCode());
		}
		criteria.setSortField(sortField);
		criteria.setSortDirection(sortDirection);
		criteria.setProductFeatureType(ProductFeatureType.MARKET_SORT.name());
		if (!StringUtils.isBlank(status)) {
			criteria.setStatus(status);
		}
		if (!StringUtils.isBlank(tag)) {
			criteria.setTag(tag);
		}
		// Start Category handling
		List<Long> categoryIds = new ArrayList<Long>();

		if (category != null) {
			categoryIds.add(category);
		}
		if (categoryIds.size() > 0) {
			criteria.setCategoryIds(categoryIds);
		}
		// End Category handling

		if (!StringUtils.isBlank(companyName)) {
			criteria.setCompanyName(companyName);
		}

		if (page != null) {
			criteria.setStartPage(page);
		}

		if (count != null) {
			criteria.setMaxCount(count);
		}

		if (!StringUtils.isBlank(name)) {
			criteria.setProductName(name);
		}

		if (!StringUtils.isBlank(identifier)) {
			criteria.setCode(identifier);
		}

		try {
			long start = System.currentTimeMillis();
			ReadableProductList productSimpleListsByCriterias = productFacadeV2.getMainDisplayManagementList(null, language, criteria);
			long end = System.currentTimeMillis();
			System.out.println("执行时间："+(end - start));
			return productSimpleListsByCriterias;
		} catch (Exception e) {

			LOGGER.error("Error while filtering products product", e);
			try {
				response.sendError(503, "Error while filtering products " + e.getMessage());
			} catch (Exception ignore) {
			}

			return null;
		}
	}


	@RequestMapping(value = "/auth/products", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public ReadableProductList listBySeller(
			@RequestParam(value = "identifier", required = false) String identifier,
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "category", required = false) Long category,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "shippingType", required = false) String shippingType,
			@RequestParam(value = "auditStatus", required = false) String auditStatus,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, // current
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count, // count
			@RequestParam(value = "available", required = false) Boolean available,
			@RequestParam(value = "startTime", required = false) Long startTime,
			@RequestParam(value = "endTime", required = false) Long endTime,
			@ApiIgnore Language language, HttpServletRequest request,
			HttpServletResponse response)  {

		Principal principal = request.getUserPrincipal();
		String userName = principal.getName();
		Customer customer = customerService.getByNick(userName);

		ProductCriteria criteria = new ProductCriteria();

		criteria.setOrigin(ORIGIN_ADMIN);
		criteria.setPublishWay(PublishWayEnums.SELLER.name());

		// do not use legacy pagination anymore
		if (lang != null) {
			criteria.setLanguage(lang);
		} else {
			criteria.setLanguage(language.getCode());
		}
		if (!StringUtils.isBlank(status)) {
			criteria.setStatus(status);
		}
		// Start Category handling
		List<Long> categoryIds = new ArrayList<Long>();

		if (category != null) {
			categoryIds.add(category);
		}
		if (categoryIds.size() > 0) {
			criteria.setCategoryIds(categoryIds);
		}
		criteria.setAuditStatus(auditStatus);
		criteria.setShippingType(shippingType);
		criteria.setCreateStartTime(startTime);
		criteria.setCreateEndTime(endTime);
		criteria.setStatus(status);
		// End Category handling

		if (available != null && available) {
			criteria.setAvailable(available);
		}

		if (page != null) {
			criteria.setStartPage(page);
		}
		if (count != null) {
			criteria.setMaxCount(count);
		}

		if (!StringUtils.isBlank(name)) {
			criteria.setProductName(name);
		}

		if (!StringUtils.isBlank(identifier)) {
			criteria.setCode(identifier);
		}

		try {
			return productFacadeV2.getProductSimpleListsByCriterias(customer.getMerchantStore(), language, criteria);

		} catch (Exception e) {

			LOGGER.error("Error while filtering products product", e);
			try {
				response.sendError(503, "Error while filtering products " + e.getMessage());
			} catch (Exception ignore) {

			}

			return null;
		}
	}


	@RequestMapping(value = "/shipping/price/product/id/{productId}", method = RequestMethod.GET)
	@ApiOperation(httpMethod = "GET", value = "Get a product shipping price", notes = "product detail get shipping price" )
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Single product found", response = ReadableProductDetailShippingPrice.class) })
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public CommonResultDTO<ReadableProductDetailShippingPrice> getShippingPriceByProductDetail(@PathVariable Long productId,
																	@RequestParam(value = "lang", required = false) String lang,
																	@ApiIgnore Language language) {
		ReadableProductDetailShippingPrice readableProductDetailShippingPrice = new ReadableProductDetailShippingPrice();
		ReadableAddress readableAddress = new ReadableAddress();
		readableAddress.setAddress("부산광역시 강서구");
		readableProductDetailShippingPrice.setShippingOrigin(readableAddress);
		readableProductDetailShippingPrice.setShippingType(Lists.newArrayList(ShippingType.NATIONAL.name()));
		readableProductDetailShippingPrice.setShippingPrice("27,000");
		return CommonResultDTO.ofSuccess(readableProductDetailShippingPrice);
	}







	@RequestMapping(value = "/private/main/display/search/products", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	public ReadableProductList getMainDisplayManagementSearchProductList(
			@RequestParam(value = "lang", required = false) String lang,
			@RequestParam(value = "category", required = false) Long category,
			@RequestParam(value = "name", required = false) String name,
			@RequestParam(value = "identifier", required = false) String identifier,
			@RequestParam(value = "status", required = false) String status,
			@RequestParam(value = "productType", required = false) String productType,
			@RequestParam(value = "page", required = false, defaultValue = "0") Integer page, // current
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count, // count
			@RequestParam(value = "companyName", required = false) String companyName,
			@RequestParam(value = "available", required = false) Boolean available,
			@RequestParam(value = "createStartTime", required = false) Long createStartTime,
			@RequestParam(value = "createEndTime", required = false) Long createEndTime,
			@RequestParam(value = "modifiedStartTime", required = false) Long modifiedStartTime,
			@RequestParam(value = "tag", required = true) String tag,
			@RequestParam(value = "modifiedEndTime", required = false) Long modifiedEndTime,
			@ApiIgnore Language language, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ProductCriteria criteria = new ProductCriteria();

		criteria.setOrigin(ORIGIN_ADMIN);

		if (!StringUtils.isBlank(status)) {
			criteria.setStatus(status);
		}
		// do not use legacy pagination anymore
		if (lang != null) {
			criteria.setLanguage(lang);
		} else {
			criteria.setLanguage(language.getCode());
		}
		if (available != null) {
			criteria.setAvailable(available);
		}
		if (!StringUtils.isBlank(status)) {
			criteria.setStatus(status);
		}
		criteria.setTag(tag);
		criteria.setModifiedStartTime(modifiedStartTime);
		criteria.setModifiedEndTime(modifiedEndTime);
		criteria.setCreateStartTime(createStartTime);
		criteria.setCreateEndTime(createEndTime);

		// Start Category handling
		List<Long> categoryIds = new ArrayList<Long>();

		if (category != null) {
			categoryIds.add(category);
		}
		if (categoryIds.size() > 0) {
			criteria.setCategoryIds(categoryIds);
		}
		// End Category handling

		if (!StringUtils.isBlank(companyName)) {
			criteria.setCompanyName(companyName);
		}

		if (page != null) {
			criteria.setStartPage(page);
		}

		if (count != null) {
			criteria.setMaxCount(count);
		}

		if (!StringUtils.isBlank(name)) {
			criteria.setProductName(name);
		}

		if (!StringUtils.isBlank(identifier)) {
			criteria.setCode(identifier);
		}

		try {
			long start = System.currentTimeMillis();
			ReadableProductList productSimpleListsByCriterias = productFacadeV2.getMainDisplayManagementList(null, language, criteria);
			long end = System.currentTimeMillis();
			System.out.println("执行时间："+(end - start));
			return productSimpleListsByCriterias;
		} catch (Exception e) {

			LOGGER.error("Error while filtering products product", e);
			try {
				response.sendError(503, "Error while filtering products " + e.getMessage());
			} catch (Exception ignore) {
			}

			return null;
		}
	}


	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PostMapping(value = "/private/add/main/display/product", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "add main display product", notes = "add main display  product", produces = "application/json", response = List.class)
	public CommonResultDTO<Void> addMainDisplayManagementProduct(@RequestBody PersistableProductFeature productFeature){
		try {
			productFacadeV2.addMainDisplayManagementProduct(productFeature);
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("addMainDisplayManagementProduct error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}



	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PostMapping(value = "/private/delete/main/display/product", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "delete main display product", notes = "delete main display  product", produces = "application/json", response = List.class)
	public CommonResultDTO<Void> deleteMainDisplayManagementProduct(@RequestBody PersistableProductFeature productFeature){
		try {
			productFacadeV2.removeMainDisplayManagementProduct(productFeature);
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("deleteMainDisplayManagementProduct error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@PostMapping(value = "/private/update/main/display/product/sort", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "POST", value = "update main display product", notes = "update main display  product", produces = "application/json", response = List.class)
	public CommonResultDTO<Void> sortUpdateMainDisplayManagementProduct(@RequestBody List<PersistableProductFeature> persistableProductFeatures){
		try {
			productFacadeV2.sortUpdateMainDisplayManagementProduct(persistableProductFeatures);
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("sortUpdateMainDisplayManagementProduct error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@RequestMapping(value = {"/private/announcement","/auth/announcement" }, method = RequestMethod.GET)
	@ResponseBody
	public CommonResultDTO<Map<Integer,AnnouncementInfo>> getAdminAnnouncement(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			String adminProductAnnouncement = productService.getAdminProductAnnouncement();
			if (StringUtils.isNotEmpty(adminProductAnnouncement)){
				Map<Integer,AnnouncementInfo> announcementInfo = JSON.parseObject(adminProductAnnouncement, Map.class);
				return CommonResultDTO.ofSuccess(announcementInfo);
			}
			return null;
		}catch(Exception e){
			LOGGER.error("getAdminAnnouncement error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@RequestMapping(value = "/private/seller/shipping/text/list", method = RequestMethod.GET)
	@ResponseBody
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT")})
	public CommonResultDTO<List<SellerProductShippingTextInfo>> getAdminSellerProductShippingTextInfoListByMerchant(
			@ApiIgnore MerchantStore merchantStore,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			List<SellerProductShippingTextInfo> sellerProductShippingTextInfoListByMerchant = sellerTextInfoFacade.getSellerProductShippingTextInfoListByMerchant(merchantStore);
			return CommonResultDTO.ofSuccess(sellerProductShippingTextInfoListByMerchant);
		}catch(Exception e){
			LOGGER.error("getSellerProductShippingTextInfoListByMerchant error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}

	@PutMapping(value = { "/private/seller/shipping/text"})
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT")})
	public @ResponseBody CommonResultDTO<Long> saveAdminSellerProductShippingTextInfoListByMerchant(
			@ApiIgnore MerchantStore merchantStore,
			@Valid @RequestBody SellerProductShippingTextInfo sellerProductShippingTextInfo) {
		try {
			return CommonResultDTO.ofSuccess(sellerTextInfoFacade.save(sellerProductShippingTextInfo, merchantStore));
		}catch (Exception e){
			LOGGER.error("productSimpleUpdate error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@RequestMapping(value = "/auth/seller/shipping/text/list", method = RequestMethod.GET)
	@ResponseBody
	public CommonResultDTO<List<SellerProductShippingTextInfo>> getSellerProductShippingTextInfoListByMerchant(
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		try {
			Principal principal = request.getUserPrincipal();
			String userName = principal.getName();
			Customer customer = customerService.getByNick(userName);
			List<SellerProductShippingTextInfo> sellerProductShippingTextInfoListByMerchant = sellerTextInfoFacade.getSellerProductShippingTextInfoListByMerchant(customer.getMerchantStore());
			return CommonResultDTO.ofSuccess(sellerProductShippingTextInfoListByMerchant);
		}catch(Exception e){
			LOGGER.error("getSellerProductShippingTextInfoListByMerchant error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}

	@PutMapping(value = { "/auth/seller/shipping/text"})
	public @ResponseBody CommonResultDTO<Long> saveSellerProductShippingTextInfoListByMerchant(
			@Valid @RequestBody SellerProductShippingTextInfo sellerProductShippingTextInfo,
			HttpServletRequest request) {
		try {
			Principal principal = request.getUserPrincipal();
			String userName = principal.getName();
			Customer customer = customerService.getByNick(userName);
			return CommonResultDTO.ofSuccess(sellerTextInfoFacade.save(sellerProductShippingTextInfo, customer.getMerchantStore()));
		}catch (Exception e){
			LOGGER.error("productSimpleUpdate error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@PutMapping(value = { "/private/update/admin/hscode"})
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT")})
	public @ResponseBody CommonResultDTO<Void> updateProductHsCodeByAdmin(
			@Valid @RequestBody ProductUpdateReqDTO productUpdateReqDTO,
			@ApiIgnore MerchantStore merchantStore,
			HttpServletRequest request) {
		try {
			productService.updateProductHsCode(productUpdateReqDTO.getProductId(),
					productUpdateReqDTO.getHsCode(), merchantStore);
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("productSimpleUpdate error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@PutMapping(value = { "/auth/update/hscode"})
	public @ResponseBody CommonResultDTO<Void> updateProductHsCodeBySeller(
			@Valid @RequestBody ProductUpdateReqDTO productUpdateReqDTO,
			HttpServletRequest request) {
		try {
			Principal principal = request.getUserPrincipal();
			String userName = principal.getName();
			Customer customer = customerService.getByNick(userName);
			productService.updateProductHsCode(productUpdateReqDTO.getProductId(),
					productUpdateReqDTO.getHsCode(), customer.getMerchantStore());
			return CommonResultDTO.ofSuccess();
		}catch (Exception e){
			LOGGER.error("productSimpleUpdate error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}



	@ResponseStatus(HttpStatus.CREATED)
	@PostMapping(value = { "/private/save/material"})
	public @ResponseBody CommonResultDTO<Void> saveMaterial(@Valid @RequestBody PersistableMaterial material) {
		try {
			erpFacade.saveMaterial(material);
			return CommonResultDTO.ofSuccess();
		}catch(Exception e){
			LOGGER.error("saveMaterial error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(),
					ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}


	@RequestMapping(value = {"/private/materials/list","/auth/materials/list"}, method = RequestMethod.GET)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
	@ResponseBody
	public CommonResultDTO<List<ReadableMaterial>> getMaterials(@ApiIgnore Language language)  {
		try {
			List<ReadableMaterial> materials = erpFacade.getMaterials(language);
			return CommonResultDTO.ofSuccess(materials);
		}catch(Exception e){
			LOGGER.error("getMaterials error", e);
			return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(),
					ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
		}
	}





}
