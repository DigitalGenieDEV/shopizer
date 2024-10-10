package com.salesmanager.shop.store.api.v2.fulfillment;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
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


    @RequestMapping(value = {"/auth/product/fulfillment/{orderId}/invoice_packing/{orderProductId}" ,"/private/product/fulfillment/{orderId}/invoice_packing/{orderProductId}"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "query invoice packing by order id ", notes = "query invoice packing by order id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "query invoice packing by order id ", response = ReadableProduct.class) })
    @ResponseBody
    public CommonResultDTO<ReadableInvoicePackingForm> queryInvoicePackingFormByOrderIdAndProductId(@PathVariable Long orderId,@PathVariable Long orderProductId) {
        try {

            ReadableInvoicePackingForm readableInvoicePackingForm = fulfillmentFacade.queryInvoicePackingFormByOrderProductId(orderId, orderProductId);
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
            @Valid @RequestParam List<Long> ids,
            @Valid @RequestParam String status,
            @Valid @RequestParam String type) {
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


    @RequestMapping(value = {"/auth/product/fulfillment/shipping/information/order/{orderId}" ,"/private/product/fulfillment/shipping/information/order/{orderId}"}, method = RequestMethod.GET)
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


    @RequestMapping(value = {"/auth/product/fulfillment/shipping/information/order_product/{orderProductId}" ,"/private/product/fulfillment/shipping/information/order_product/{orderProductId}"}, method = RequestMethod.GET)
    @ApiOperation(httpMethod = "GET", value = "query shipping information by order id ", notes = "query shipping information by order id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "query shipping information by order id ", response = ReadableProduct.class) })
    @ResponseBody
    public CommonResultDTO<ReadableFulfillmentShippingInfo> queryShippingInformationByOrderProductId(@PathVariable Long orderProductId) {
        try {
            ReadableFulfillmentShippingInfo readableFulfillmentShippingInfos = fulfillmentFacade.queryShippingInformationByOrderProductId(orderProductId);
            return CommonResultDTO.ofSuccess(readableFulfillmentShippingInfos);
        }catch (Exception e){
            LOGGER.error("save invoice packing by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @GetMapping(value = {"/auth/order_products/{orderProductId}/fulfillment/shipping/international" ,
            "/private/order_products/{orderProductId}/fulfillment/shipping/international"})
    @ApiOperation(httpMethod = "GET", value = "query international shipping information by order_product_id ", notes = "query international shipping information by order_product_id ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "query international shipping information by order_product_id ", response = ReadableProduct.class) })
    @ResponseBody
    public CommonResultDTO<List<ReadableLogisticsTrackInformation>> queryInternationalShippingInformationByOrderProductId(@PathVariable Long orderProductId) {
        try {
            List<ReadableLogisticsTrackInformation> readableFulfillmentShippingInfos = fulfillmentFacade.queryInternationalShippingInformationByOrderProductId(orderProductId);
            return CommonResultDTO.ofSuccess(readableFulfillmentShippingInfos);
        }catch (Exception e){
            LOGGER.error("query international shipping information by order_product_id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }

    @RequestMapping(value = { "/auth/update/national/logistics", "/private/update/national/logistics" }, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Void> updateNationalLogistics(
            @Valid @RequestBody PersistableFulfillmentLogisticsUpdateReqDTO persistableFulfillmentLogisticsUpdateReqDTO) {
        try {
            fulfillmentFacade.updateNationalLogistics(persistableFulfillmentLogisticsUpdateReqDTO);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("updateNationalLogistics error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }

    @RequestMapping(value = { "/auth/update/china/logistics", "/private/update/china/logistics" }, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Void> updateChainLocalLogistics(
            @Valid @RequestBody PersistableFulfillmentLogisticsUpdateReqDTO persistableFulfillmentLogisticsUpdateReqDTO) {
        try {
            fulfillmentFacade.updateInternationalLogistics(persistableFulfillmentLogisticsUpdateReqDTO);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("updateNationalLogistics error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = { "/auth/update/cross_border/logistics", "/private/update/cross_border/logistics" }, method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Void> updateCrossBorderLogistics(
            @Valid @RequestBody PersistableFulfillmentLogisticsUpdateReqDTO persistableFulfillmentLogisticsUpdateReqDTO) {
        try {
            fulfillmentFacade.updateCrossBorderTransportation(persistableFulfillmentLogisticsUpdateReqDTO);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("updateNationalLogistics error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }

}
