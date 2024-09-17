package com.salesmanager.shop.store.api.v1.order;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsTrace;
import com.salesmanager.shop.model.order.PersistableOrderProductDesign;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.ReadableOrderProductDesign;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductList;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.controller.order.facade.OrderProductFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Ordering Product api (Order Product Flow Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Order flow resource", description = "Manage orders (create, list, get)") })
public class OrderProductApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProductApi.class);

    @Inject
    private OrderProductFacade orderProductFacade;

    @Inject
    private OrderFacade orderFacade;

    @Inject
    private CustomerService customerService;

    /**
     * This method returns list of all the order products for a store.This is not
     * bound to any specific stores and will get list of all the order products
     * available for this instance
     *
     * @param start
     * @param count
     * @return List of orders
     * @throws Exception
     */
    @RequestMapping(value = { "/auth/order_products" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReadableOrderProductList list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam(value = "statuses", required = false) String statuses,
            @RequestParam(value = "beginDate", required = false) String beginDate,
            @RequestParam(value = "endDate", required = false) String endDate,
            @RequestParam(value = "paymentMethod", required = false) String paymentMethod,
            @RequestParam(value = "id", required = false) Long id,
            @RequestParam(value = "receiver", required = false) String receiver,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response
    ) {
        OrderProductCriteria orderProductCriteria = new OrderProductCriteria();
        orderProductCriteria.setPageSize(count);
        orderProductCriteria.setStartPage(page);

        orderProductCriteria.setId(id);
        orderProductCriteria.setReceiver(receiver);
        orderProductCriteria.setPaymentMethod(paymentMethod);
        orderProductCriteria.setBeginDate(beginDate);
        orderProductCriteria.setEndDate(endDate);
        orderProductCriteria.setStatuses(statuses);

        ReadableOrderProductList readableOrderProductList = orderProductFacade.getReadableOrderProductList(orderProductCriteria, merchantStore, language);

        return readableOrderProductList;
    }

    @RequestMapping(value = { "/auth/order_products/{id}" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public ReadableOrderProduct getOrderProduct(
            @PathVariable final Long id,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response
    ) throws IOException {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while performing checkout customer not authorized");
            return null;
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(id, merchantStore, language);

        if (readableOrderProduct == null) {
            LOGGER.error("OrderProduct is null for id " + id);
            response.sendError(404, "OrderProduct is null for id " + id);
            return null;
        }
        return readableOrderProduct;
    }


    @RequestMapping(value = { "/auth/order_products/{id}/design" }, method = RequestMethod.PATCH)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public CommonResultDTO<Boolean> patchDesign(
            @PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
            @Valid @RequestBody PersistableOrderProductDesign persistableOrderProductDesign,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), "Error while patch design, customer not authorized");
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(id, merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + id);
        }
        persistableOrderProductDesign.setOrderProductId(readableOrderProduct.getId());

        ReadableOrder readableOrder = orderFacade.getCustomerReadableOrder(readableOrderProduct.getOrderId(), customer, language);
        if (readableOrder == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Error while patch design, order is not found");
        }


        OrderType orderType = OrderType.valueOf(readableOrderProduct.getOrderType());
        if (!OrderType.OEM.equals(orderType)) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Error while patch degign, only OEM order support patch design");
        }

        return CommonResultDTO.ofSuccess(orderProductFacade.patchDesign(readableOrderProduct, persistableOrderProductDesign));
    }

    @RequestMapping(value = { "/auth/order_products/{id}/design" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public CommonResultDTO<ReadableOrderProductDesign> getDesign(
            @PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws IOException {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);
        if (customer == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), "Error while patch design, customer not authorized");
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(id, merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + id);
        }

        ReadableOrder readableOrder = orderFacade.getCustomerReadableOrder(readableOrderProduct.getOrderId(), customer, language);
        if (readableOrder == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Error while patch design, order is not found");
        }

        return CommonResultDTO.ofSuccess(orderProductFacade.getReadableOrderProductDesignById(readableOrderProduct.getId(), merchantStore, language));
    }

    @RequestMapping(value = { "/private/order_products/{id}/logistics" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private List<ReadableSupplierCrossOrderLogistics> getLogistics(@PathVariable final Long id) {
        return orderProductFacade.getLogisticsInfo(id);
    }

    @RequestMapping(value = { "/private/order_products/{id}/logistics_trace" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    private List<ReadableSupplierCrossOrderLogisticsTrace> getLogisticsTrace(@PathVariable final Long id) {
        return orderProductFacade.getLogisticsTrace(id);
    }
}
