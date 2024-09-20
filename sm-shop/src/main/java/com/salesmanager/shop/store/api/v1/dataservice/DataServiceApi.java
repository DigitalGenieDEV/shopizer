package com.salesmanager.shop.store.api.v1.dataservice;

import java.util.Map;

import javax.inject.Inject;

import com.salesmanager.shop.model.catalog.BIProductInfoRequestV2;
import com.salesmanager.shop.model.catalog.BIMemberInfoRequestV2;
import com.salesmanager.shop.model.catalog.BIBuyerInfoRequestV2;
import com.salesmanager.shop.model.catalog.BISaleInfoRequestV2;
import com.salesmanager.shop.model.catalog.BITrafficInfoRequestV2;

import org.springframework.web.bind.annotation.*;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.store.controller.dataservice.facade.DataServiceFacade;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Api for data service, mainly about BI (Business Intelligence)
 *
 * @author sword
 */
@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Data statistics and business insight functionality (Data Service Api)"})
public class DataServiceApi {

  @Inject
  private DataServiceFacade dataServiceFacade;

  @PostMapping("/dataservice/product_info")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
          @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public @ResponseBody
  Map<String, Integer> biproductinfo(
          @RequestBody BIProductInfoRequestV2 biProductInfoRequest,
          @ApiIgnore Language language) {
    return dataServiceFacade.BIProductInfoV2(biProductInfoRequest, language);
  }

  @PostMapping("/dataservice/member_info")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
          @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public @ResponseBody
  Map<String, Integer> bimemberinfo(
          @RequestBody BIMemberInfoRequestV2 biMemberInfoRequest,
          @ApiIgnore Language language) {
    return dataServiceFacade.BIMemberInfoV2(biMemberInfoRequest, language);
  }

  @PostMapping("/dataservice/buyer_info")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
          @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public @ResponseBody
  Map<String, Object> bibuyerinfo(
          @RequestBody BIBuyerInfoRequestV2 biBuyerInfoRequest,
          @ApiIgnore Language language) {
    return dataServiceFacade.BIBuyerInfoV2(biBuyerInfoRequest, language);
  }

  @PostMapping("/dataservice/sale_info")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
          @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public @ResponseBody
  Map<String, Object> bisalerinfo(
          @RequestBody BISaleInfoRequestV2 biSaleInfoRequest,
          @ApiIgnore Language language) {
    return dataServiceFacade.BISaleInfoV2(biSaleInfoRequest, language);
  }

  @PostMapping("/dataservice/traffic_info")
  @ApiImplicitParams({
          @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
          @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public @ResponseBody
  Map<String, Object> bitrafficinfo(
          @RequestBody BITrafficInfoRequestV2 biTrafficInfoRequest,
          @ApiIgnore Language language) {
    return dataServiceFacade.BITrafficInfoV2(biTrafficInfoRequest, language);
  }

}