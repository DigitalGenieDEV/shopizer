package com.salesmanager.shop.store.api.v2.fulfillment;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.facade.AdditionalServicesFacade;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Product AdditionalServices Api"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product AdditionalServices Api", description = "Product AdditionalServices Api")
})
public class ProductAdditionalServicesApi {


    @Autowired
    private AdditionalServicesFacade additionalServicesFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductAdditionalServicesApi.class);

    @RequestMapping(value = {"/auth/additional/services", "/private/additional/services"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get additionalServices by merchant code", notes = "Get additionalServices by merchant code")
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
    public CommonResultDTO<List<ReadableAdditionalServices>> getAdditionalServicesByMerchantId(@RequestParam(value = "lang", required = false) String lang,
                                                  @ApiIgnore MerchantStore merchantStore,
                                                  @ApiIgnore Language language) {
        try {
            List<ReadableAdditionalServices> readableAdditionalServices = additionalServicesFacade.queryAdditionalServicesByMerchantId(Long.valueOf(merchantStore.getId()), language);
            return CommonResultDTO.ofSuccess(readableAdditionalServices);
        }catch (Exception e){
            LOGGER.error("Error  to get additionalServices By MerchantId", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }
    }


    @GetMapping(value = {"/auth/additional/services/id/{additionalServicesId}",
            "/private/additional/services/id/{additionalServicesId}"})
    @ApiOperation(httpMethod = "GET", value = "Get additionalServices by id", notes = "Get additionalServices by id")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
    public CommonResultDTO<ReadableAdditionalServices> getAdditionalServicesById(@PathVariable Long additionalServicesId,
                                                                                               @ApiIgnore Language language) {
        try {
            return CommonResultDTO.ofSuccess( additionalServicesFacade.queryAdditionalServicesById(additionalServicesId, language));
        }catch (Exception e){
            LOGGER.error("Error  to get additionalServices By id", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }
    }



    @RequestMapping(value = {"/auth/additional/services/price", "/private/additional/services/price"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "Get additionalServices by merchant code", notes = "Get additionalServices by merchant code")
    @ResponseBody
    public CommonResultDTO<String> getAdditionalServicesPrice(@RequestParam(value = "additionalServicesId", required = false) Long additionalServicesId,
                                                                                        @RequestParam(value = "additionalServicesQuantity", required = false) Integer additionalServicesQuantity,
                                                                                        @RequestParam(value = "itemQuantity", required = false) Integer itemQuantity) {
        try {
            return CommonResultDTO.ofSuccess( additionalServicesFacade.queryAdditionalServicesPrice(additionalServicesId, additionalServicesQuantity, itemQuantity));
        }catch (Exception e){
            LOGGER.error("Error  to get additionalServices price ", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }
    }






}
