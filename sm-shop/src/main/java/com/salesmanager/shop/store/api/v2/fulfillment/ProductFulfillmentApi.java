package com.salesmanager.shop.store.api.v2.fulfillment;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
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
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Product Fulfillment Api"})
@SwaggerDefinition(tags = {
        @Tag(name = "Product Fulfillment Api", description = "Product Fulfillment Api")
})
public class ProductFulfillmentApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductFulfillmentApi.class);

    @Autowired
    private CustomerService customerService;

    @Autowired
    private FulfillmentFacade fulfillmentFacade;


    @PostMapping(value = { "/auth/product/fulfillment/generalDocument", "/private/product/fulfillment/generalDocument"})
    public @ResponseBody CommonResultDTO<Void> saveGeneralDocumentByOrderId(
            @Valid @RequestBody PersistableGeneralDocument persistableGeneralDocument) {
        try {
            fulfillmentFacade.saveGeneralDocument(persistableGeneralDocument);
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
            ReadableGeneralDocument readableGeneralDocument = fulfillmentFacade.queryGeneralDocumentByOrderIdAndType(orderId, documentType);
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
            fulfillmentFacade.saveInvoicePackingForm(persistableInvoicePackingForm);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("save invoice packing form by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = {"/auth/product/fulfillment/{orderId}/invoice_packing/{productId}" ,"/private/product/fulfillment/{orderId}/invoice_packing/{productId}"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "query invoice packing by order id ", notes = "query invoice packing by order id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "query invoice packing by order id ", response = ReadableProduct.class) })
    @ResponseBody
    public CommonResultDTO<ReadableInvoicePackingForm> queryInvoicePackingFormByOrderIdAndProductId(@PathVariable Long orderId,@PathVariable Long productId) {
        try {
            ReadableInvoicePackingForm readableInvoicePackingForm = fulfillmentFacade.queryInvoicePackingFormByOrderId(orderId, productId);
            return CommonResultDTO.ofSuccess(readableInvoicePackingForm);
        }catch (Exception e){
            LOGGER.error("save invoice packing by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = { "/auth/orders/shipping/status", "/private/orders/shipping/status" }, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Void> updateSellerShippingStatusByOrder(
            @Valid @RequestBody List<Long> ids,
            @Valid @RequestBody String status,
            @Valid @RequestBody String type) {
        try {
            if (type.equals("ORDER")){
                //更新所有履约单状态
                ids.forEach(id ->{
                    fulfillmentFacade.updateFulfillmentOrderStatusByOrderId(id, status);
                });
                return CommonResultDTO.ofSuccess();
            }
            if (type.equals("PRODUCT")){
                fulfillmentFacade.updateFulfillmentOrderStatusByProductOrderId(ids, status);
            }
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("updateSellerOrderStatus error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = {"/auth/product/fulfillment/shipping/information/{orderId}" ,"/private/product/fulfillment/shipping/information/{orderId}"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "query shipping information by order id ", notes = "query shipping information by order id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "query shipping information by order id ", response = ReadableProduct.class) })
    @ResponseBody
    public CommonResultDTO<List<ReadableFulfillmentShippingInfo>> queryShippingInformationByOrderId(@PathVariable Long orderId) {
        try {
            List<ReadableFulfillmentShippingInfo> readableFulfillmentShippingInfos = fulfillmentFacade.queryShippingInformationByOrderId(orderId);
            return CommonResultDTO.ofSuccess(readableFulfillmentShippingInfos);
        }catch (Exception e){
            LOGGER.error("save invoice packing by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }



    @RequestMapping(value = { "/auth/update/national/logistics", "/private/update/national/logistics" }, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Void> updateNationalLogistics(
            @Valid @RequestBody PersistableFulfillmentLogisticsUpdateReqDTO persistableFulfillmentLogisticsUpdateReqDTO,
            @Valid @RequestBody Long id,
            @Valid @RequestBody String type) {
        try {
            fulfillmentFacade.updateNationalLogistics(persistableFulfillmentLogisticsUpdateReqDTO, type, id);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("updateNationalLogistics error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }

}
