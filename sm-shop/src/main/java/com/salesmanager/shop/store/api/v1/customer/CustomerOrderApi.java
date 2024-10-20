package com.salesmanager.shop.store.api.v1.customer;

import com.google.common.collect.Lists;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.customer.order.CustomerOrderService;
import com.salesmanager.core.business.services.order.OrderServiceImpl;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderCustomerCriteria;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrder;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrderConfirmation;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrderList;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.model.order.transaction.PersistablePayment;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.order.v0.ReadableOrderList;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.populator.customer.ReadableCustomerPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.customer.facade.CustomerOrderFacade;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.security.Principal;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@Api(tags = { "Customer Ordering api (Order Flow Api)" })
@SwaggerDefinition(tags = { @Tag(name = "Order flow resource", description = "Manage orders (list, get)") })
public class CustomerOrderApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(Customer.class);

    @Inject
    private CustomerService customerService;

    @Inject
    private CustomerOrderFacade customerOrderFacade;

    @Inject
    private CustomerFacade customerFacade;

    @Inject
    private CustomerOrderService customerOrderService;

    @Inject
    private OrderFacade orderFacade;
    @Autowired
    private OrderServiceImpl orderService;

    /**
     * List orders for authenticated customers
     *
     * @param start
     * @param count
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = { "/auth/customer_orders" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public ReadableCustomerOrderList list(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "count", required = false) Integer count,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
            return null;
        }

        if (page == null) {
            page = new Integer(0);
        }
        if (count == null) {
            count = new Integer(10);
        }

        ReadableCustomer readableCustomer = new ReadableCustomer();
        ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
        customerPopulator.populate(customer, readableCustomer, merchantStore, language);

        ReadableCustomerOrderList returnList = customerOrderFacade.getReadableCustomerOrderList(customer, page, count, language);

        if (returnList == null) {
            returnList = new ReadableCustomerOrderList();
        }

        return returnList;
    }

    /**
     * Get a given order by id
     *
     * @param id
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping(value = { "/auth/customer_orders/{id}" })
    @ResponseStatus(HttpStatus.OK)
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public ReadableCustomerOrder getCustomerOrder(
            @PathVariable Long id, @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while performing checkout customer not authorized");
            return null;
        }

        ReadableCustomerOrder customerOrder = customerOrderFacade.getReadableCustomerOrder(id, merchantStore, language);

        if (customerOrder == null) {
            LOGGER.error("CustomerOrder is null for id " + id);
            response.sendError(404, "CustomerOrder is null for id " + id);
            return null;
        }

        if (customerOrder.getCustomer() == null) {
            LOGGER.error("CustomerOrder is null for customer " + principal);
            response.sendError(404, "CustomerOrder is null for customer " + principal);
            return null;
        }

        if (customerOrder.getCustomer().getId() != null
                && customerOrder.getCustomer().getId().longValue() != customer.getId().longValue()) {
            LOGGER.error("CustomerOrder is null for customer " + principal);
            response.sendError(404, "CustomerOrder is null for customer " + principal);
            return null;
        }

        return customerOrder;
    }

//    @RequestMapping(value = { "/auth/customer_orders/{id}/capture2" }, method = RequestMethod.POST)
//    @ResponseStatus(HttpStatus.OK)
//    @ResponseBody
//    public ReadableCombineTransaction captureCustomerOrder(@PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
//                                                           @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception {
//        Principal principal = request.getUserPrincipal();
//        String userName = principal.getName();
//
//        Customer customer = customerService.getByNick(userName);
//
//        if (customer == null) {
//            response.sendError(401, "Error while performing checkout customer not authorized");
//            return null;
//        }
//
//        CustomerOrder customerOrder = customerOrderService.getById(id);
//
//        if (customerOrder == null) {
//            response.sendError(404, "Customer Order id " + id + " does not exists");
//            return null;
//        }
//
//        ReadableCombineTransaction transaction = customerOrderFacade.captureCustomerOrder(merchantStore, customerOrder, customer, language);
//        return transaction;
//    }

    @RequestMapping(value = { "/auth/customer_orders/{id}/authorize_capture" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReadableCombineTransaction authorizeAndCaptureCustomerOrder(@PathVariable final Long id,
                                                                       @ApiIgnore MerchantStore merchantStore,
                                                                       @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while performing checkout customer not authorized");
            return null;
        }

        CustomerOrder customerOrder = customerOrderService.getById(id);

        if (customerOrder == null) {
            response.sendError(404, "Customer Order id " + id + " does not exists");
            return null;
        }

        ReadableCombineTransaction transaction = customerOrderFacade.authorizeCaptureCustomerOrder(merchantStore, customerOrder, customer, language);
        return transaction;
    }

    @RequestMapping(value = { "/auth/customer_orders/{id}/post_payment" }, method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public ReadableCombineTransaction capture(@PathVariable final Long id,
                                              @RequestBody Payment payment,
                                              @ApiIgnore MerchantStore merchantStore,
                                              @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response) throws Exception {

        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while performing checkout customer not authorized");
            return null;
        }

        CustomerOrder customerOrder = customerOrderService.getById(id);

        if (customerOrder == null) {
            response.sendError(404, "Customer Order id " + id + " does not exists");
            return null;
        }

        ReadableCombineTransaction transaction = customerOrderFacade.processPostPayment(merchantStore, customerOrder, customer, payment, language);
        return transaction;
    }

    @RequestMapping(value = { "/auth/customer/orders" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public ReadableOrderList listOrders(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "endTime", required = false) Long endTime,
            @RequestParam(value = "orderStatus", required = false) String orderStatus,
            @RequestParam(value = "orderType", required = false) String orderType,
            @RequestParam(value = "productName", required = false) String productName,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
            return null;
        }

        if (page == null) {
            page = new Integer(0);
        }
        if (count == null) {
            count = new Integer(100);
        }

        ReadableCustomer readableCustomer = new ReadableCustomer();
        ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
        customerPopulator.populate(customer, readableCustomer, merchantStore, language);

        OrderCustomerCriteria criteria = new OrderCustomerCriteria();
        criteria.setLegacyPagination(false);
        criteria.setStartPage(page);
        criteria.setPageSize(count);
        criteria.setStartTime(startTime);
        criteria.setOrderType(orderType);
        criteria.setEndTime(endTime);
        criteria.setOrderStatus(orderStatus);
        criteria.setProductName(productName);

        ReadableOrderList readableOrderList = orderFacade.getCustomerReadableOrderList(merchantStore, customer, criteria, language);

//        ReadableCustomerOrderList returnList = customerOrderFacade.getReadableCustomerOrderList(customer, page, count, language);

        if (readableOrderList == null) {
            readableOrderList = new ReadableOrderList();
        }

        return readableOrderList;
    }

    @RequestMapping(value = { "/auth/customer/orders/overview" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public CommonResultDTO<Map<String, Integer>> countByStatus(
            @RequestParam(value = "page", required = false) Integer page,
            @RequestParam(value = "count", required = false) Integer count,
            @RequestParam(value = "startTime", required = false) Long startTime,
            @RequestParam(value = "endTime", required = false) Long endTime,
            @RequestParam(value = "orderType", required = false) String orderType,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response
    ) throws Exception {

        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
            return null;
        }

        if (page == null) {
            page = new Integer(0);
        }
        if (count == null) {
            count = new Integer(100);
        }

        if (startTime == null) {
            Calendar instance = Calendar.getInstance();
            instance.setTime(new Date());
            instance.add(Calendar.MONTH, -3);
            startTime = instance.getTimeInMillis();
        }

        ReadableCustomer readableCustomer = new ReadableCustomer();
        ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
        customerPopulator.populate(customer, readableCustomer, merchantStore, language);

        OrderCustomerCriteria criteria = new OrderCustomerCriteria();
        criteria.setLegacyPagination(false);
        criteria.setStartPage(page);
        criteria.setPageSize(count);
        criteria.setStartTime(startTime);
        criteria.setEndTime(endTime);
        criteria.setOrderType(orderType);

        Map<String, Integer> countedMap = orderFacade.countCustomerOrderByStatus(merchantStore, customer, criteria, language);

        return CommonResultDTO.ofSuccess(countedMap);
    }

    /**
     * 废弃 不要使用
     * @param id
     * @param merchantStore
     * @param language
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Deprecated
    @RequestMapping(value = { "/auth/customer/orders/{id}" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public ReadableOrder getOrder(
            @PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            response.sendError(401, "Error while listing orders, customer not authorized");
            return null;
        }

        return orderFacade.getCustomerReadableOrder(id, customer, language);

    }

    @RequestMapping(value = { "/auth/customer/order/{id}" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
    public CommonResultDTO<ReadableOrder> getOrderById(
            @PathVariable final Long id, @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language, HttpServletRequest request, HttpServletResponse response
    ) throws IOException {
        try {
            Principal principal = request.getUserPrincipal();
            String userName = principal.getName();

            Customer customer = customerService.getByNick(userName);

            if (customer == null) {
                response.sendError(401, "Error while listing orders, customer not authorized");
                return null;
            }
            ReadableOrder customerReadableOrder = orderFacade.getCustomerReadableOrder(id, customer, language);
            return CommonResultDTO.ofSuccess(customerReadableOrder);
        }catch(Exception e){
            LOGGER.error("updateHandlingFeeById error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @PostMapping(value = "/auth/customer/orders/{id}/partial_pay")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko")})
    public ReadableCustomerOrderConfirmation partialPay(@PathVariable Long id,
                                                        @Valid @RequestBody PersistablePayment persistablePayment,
                                                        @ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language,
                                                        HttpServletRequest request,
                                                        HttpServletResponse response, Locale locale) throws Exception {

        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        Customer customer = customerService.getByNick(userName);

        if (customer == null) {
            throw new ResourceNotFoundException("Error while partial pay, customer not authorized");
        }

        Long customerOrderId = orderService.findCustomerOrderIdByOrderId(id);
        if (customerOrderId == null) {
            throw new ResourceNotFoundException("Error while partial pay, order relation pay order is not found");
        }

        Order order = orderService.getOrder(id, customer);

        // 这里重新创建一个CustomOrder并创建一个新的支付单
        CustomerOrder customerOrder = customerOrderService.getCustomerOrder(customerOrderId);
        customerOrder.setOrders(Lists.newArrayList(order));
        customerOrder.setTotal(order.getTotal());

        customerOrder = customerOrderFacade.replicateCustomerOrder(customerOrder, persistablePayment, customer, language);

        return customerOrderFacade.orderConfirmation(customerOrder, customer, language, id);
    }
}
