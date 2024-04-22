package com.salesmanager.shop.store.api.v1.search;

import java.util.List;

import javax.inject.Inject;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.shop.model.catalog.SearchProductRequestV2;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.search.ReadableSearchProduct;
import org.springframework.web.bind.annotation.*;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.SearchProductRequest;
import com.salesmanager.shop.model.entity.ValueList;
import com.salesmanager.shop.store.controller.search.facade.SearchFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import modules.commons.search.request.SearchItem;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Api for searching shopizer catalog based on search term when filtering products based on product
 * attribute is required, see /api/v1/product
 *
 * @author c.samson
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Search products and search word/sentence completion functionality (Search Api)"})
@SwaggerDefinition(tags = {
    @Tag(name = "Search products resource", description = "Search products and search term completion functionality")
})
public class SearchApi {

  @Inject private SearchFacade searchFacade;


  /**
   * Search products from underlying elastic search
   */
  @PostMapping("/search")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  
  //TODO use total, count and page
  public @ResponseBody List<SearchItem> search(
      @RequestBody SearchProductRequest searchRequest,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {

    return searchFacade.search(merchantStore, language, searchRequest);
  }

  @PostMapping("/search_v2")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
          @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody List<ReadableProduct> searchV2(
          @RequestBody SearchProductRequestV2 searchProductRequestV2,
          @ApiIgnore Language language) throws Exception {
    return  searchFacade.searchV2(searchProductRequestV2, language);
  }

  @PostMapping("/search/autocomplete")
  @ApiImplicitParams({
    @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
    @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en")
  })
  public @ResponseBody ValueList autocomplete(
      @RequestBody SearchProductRequest searchRequest,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language) {
    return searchFacade.autocompleteRequest(searchRequest.getQuery(), merchantStore, language);
  }
}
