package com.salesmanager.shop.store.api.v2.fulfillment;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.PersistableInvoicePackingForm;
import com.salesmanager.shop.model.fulfillment.PersistableQcInfo;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.ReadableQcInfo;
import com.salesmanager.shop.model.fulfillment.facade.AdditionalServicesFacade;
import com.salesmanager.shop.model.fulfillment.facade.ProductQcFacade;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Product qc info Api"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product qc info Api", description = "Product qc info Api")
})
public class ProductQcApi {


    @Autowired
    private ProductQcFacade productQcFacade;

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductQcApi.class);


    @GetMapping(value = {"/auth/qc/info/id/{qcInfoId}",
            "/private/qc/info/id/{qcInfoId}"})
    @ApiOperation(httpMethod = "GET", value = "Get qc info by id", notes = "Get qc info by id")
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
    public CommonResultDTO<ReadableQcInfo> getQcInfoById(@PathVariable Long qcInfoId) {
        try {
            ReadableQcInfo readableQcInfo = productQcFacade.queryQcInfoById(qcInfoId);
            return CommonResultDTO.ofSuccess(readableQcInfo);
        }catch (Exception e){
            LOGGER.error("Error  to get qc info By id", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }
    }

    @PostMapping(value = {"/auth/update/qc/info",
            "/private/update/qc/info"})
    public @ResponseBody CommonResultDTO<Void> updateQcInfo(
            @Valid @RequestBody PersistableQcInfo persistableQcInfo) {
        try {
            productQcFacade.updateQcInfo(persistableQcInfo);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("update Qc info error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @PostMapping(value = {"/auth/update/qc/info/status",
            "/private/update/qc/info/status"})
    public @ResponseBody CommonResultDTO<Void> updateQcStatusById(
            @RequestParam(value = "status", required = false) String status,
            @RequestParam(value = "id", required = false) Long id) {
        try {
            productQcFacade.updateQcStatusById(status, id);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("update Qc Status By Id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


}
