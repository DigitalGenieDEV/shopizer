package com.salesmanager.shop.store.api.v1.order;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductList;
import com.salesmanager.shop.store.controller.order.facade.OrderProductFacade;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Ordering Product api (Order Product Flow Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Order flow resource", description = "Manage orders (create, list, get)") })
public class OrderProductApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProductApi.class);

    @Inject
    private OrderProductFacade orderProductFacade;

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
}
