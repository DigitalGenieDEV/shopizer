package com.salesmanager.shop.store.api.v1.order;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.business.services.order.invoice.OrderInvoiceService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.order.InvoiceType;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderInvoice;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.shop.model.order.v1.PersistableOrderInvoice;
import com.salesmanager.shop.model.order.v1.ReadableOrderInvoice;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.populator.order.PersistableOrderInvoicePopulator;
import com.salesmanager.shop.populator.order.ReadableOrderInvoicePopulator;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Invoice api"})
@SwaggerDefinition(tags = {@Tag(name = "Invoice resource", description = "Manage invoices(get, patch)")})
public class InvoiceApi {

    public static final Logger LOGGER = LoggerFactory.getLogger(InvoiceApi.class);

    @Inject
    private OrderService orderService;

    @Inject
    private CustomerService customerService;

    @Inject
    private OrderInvoiceService orderInvoiceService;

    @Inject
    private ReadableOrderInvoicePopulator readableOrderInvoicePopulator;

    @Inject
    private PersistableOrderInvoicePopulator persistableOrderInvoicePopulator;

    @GetMapping(value = {"/auth/orders/invoice/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<ReadableOrderInvoice> getInvoice(@PathVariable Long id,
                                                            HttpServletRequest request) throws Exception {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            String name = userPrincipal.getName();
            Customer customer = customerService.getByNick(name);
            Order order = orderService.getOrder(id, customer.getMerchantStore());
            if (order == null) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Order not found [" + id + "]");
            }

            OrderInvoice orderInvoice = order.getInvoice();
            ReadableOrderInvoice readableInvoice = readableOrderInvoicePopulator.populate(orderInvoice, null, null);
            return CommonResultDTO.ofSuccess(readableInvoice);
        } catch (Exception e) {
            LOGGER.error("Error while get readableInvoice [" + id + "]", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }
    }

    @PostMapping(value = {"/auth/orders/invoice/save/{id}"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Boolean> updateInvoice(@PathVariable Long id, @Valid @RequestBody PersistableOrderInvoice persistableInvoice,
                                                 HttpServletRequest request) throws Exception {
        try {
            Principal userPrincipal = request.getUserPrincipal();
            String name = userPrincipal.getName();
            Customer customer = customerService.getByNick(name);
            Order order = orderService.getOrder(id, customer.getMerchantStore());
            if (order == null) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Order not found [" + id + "]");
            }

            // 1688 Product patch invoice information before ordered
            if (OrderType.PRODUCT_1688.equals(order.getOrderType()) && !OrderStatus.ORDERED.equals(order.getStatus())) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Current order status can not patch invoice information");
            }

            // maybe patch multi times, it's ok because idempotent
//            if (order.getInvoice() != null && !InvoiceType.NO_INVOICE.equals(order.getInvoice().getInvoiceType())) {
//                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "Order persistableInvoice is already filled");
//            }
            Boolean success = orderInvoiceService.saveInvoice(order, persistableOrderInvoicePopulator.populate(persistableInvoice, order.getInvoice(), null, null), customer.getMerchantStore());
            return CommonResultDTO.ofSuccess(success);
        } catch (Exception e) {
            LOGGER.error("Error while patching persistableInvoice [" + id + "]", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }
    }

}
