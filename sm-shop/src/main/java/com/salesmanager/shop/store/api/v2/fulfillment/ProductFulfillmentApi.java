package com.salesmanager.shop.store.api.v2.fulfillment;

import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
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

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Product Fulfillment Api"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product Fulfillment Api", description = "Product Fulfillment Api")
})
public class ProductFulfillmentApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFulfillmentApi.class);


    @Autowired
    private FulfillmentFacade fulfillmentFacade;


    @PostMapping(value = { "/auth/product/fulfillment/generalDocument", "/private/product/fulfillment/generalDocument"})
    public @ResponseBody CommonResultDTO<Void> saveGeneralDocumentByOrderId(
            @Valid @RequestBody PersistableGeneralDocument persistableGeneralDocument) {
        try {
            fulfillmentFacade.saveGeneralDocumentByOrderId(persistableGeneralDocument);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("save general document by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = {"/auth/product/fulfillment/generalDocument/{orderId}/{documentType}",
            "/private/product/fulfillment/generalDocument/{orderId}/{documentType}"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "query general document by order id ", notes = "query general document by order id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "query general document by order id ", response = ReadableProduct.class) })
    @ResponseBody
    public CommonResultDTO<ReadableGeneralDocument> queryGeneralDocumentByOrderId(@PathVariable Long orderId,
                                                                 @PathVariable String documentType) {
        try {
            ReadableGeneralDocument readableGeneralDocument = fulfillmentFacade.queryGeneralDocumentByOrderId(orderId, documentType);
            return CommonResultDTO.ofSuccess(readableGeneralDocument);
        }catch (Exception e){
            LOGGER.error("save general document by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }



    @PostMapping(value = { "/auth/product/fulfillment/invoice_packing",
            "/private/product/fulfillment/invoice_packing"})
    public @ResponseBody CommonResultDTO<Void> saveInvoicePackingFormByOrderId(
            @Valid @RequestBody PersistableInvoicePackingForm persistableInvoicePackingForm) {
        try {
            fulfillmentFacade.saveInvoicePackingFormByOrderId(persistableInvoicePackingForm);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("save invoice packing form by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = {"/auth/product/fulfillment/invoice_packing/{orderId}" ,"/private/product/fulfillment/invoice_packing/{orderId}"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "query invoice packing by order id ", notes = "query invoice packing by order id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "query invoice packing by order id ", response = ReadableProduct.class) })
    @ResponseBody
    public CommonResultDTO<ReadableInvoicePackingForm> queryGeneralDocumentByOrderId(@PathVariable Long orderId) {
        try {
            ReadableInvoicePackingForm readableInvoicePackingForm = fulfillmentFacade.queryInvoicePackingFormByOrderId(orderId);
            return CommonResultDTO.ofSuccess(readableInvoicePackingForm);
        }catch (Exception e){
            LOGGER.error("save invoice packing by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }

}
