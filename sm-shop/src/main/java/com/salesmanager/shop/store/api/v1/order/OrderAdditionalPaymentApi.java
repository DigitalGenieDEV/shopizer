package com.salesmanager.shop.store.api.v1.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.order.OrderAdditionalPaymentService;
import com.salesmanager.core.model.order.OrderAdditionalPayment;
import com.salesmanager.shop.model.order.v1.PersistableOrderAdditionalPayment;
import com.salesmanager.shop.model.order.v1.ReadableOrderAdditionalPayment;
import com.salesmanager.shop.populator.order.PersistableOrderAdditionalPaymentPopulator;
import com.salesmanager.shop.populator.order.ReadableOrderAdditionalPaymentPopulator;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Order Additional Payment API"})
@SwaggerDefinition(tags = {
        @Tag(name = "Order Additional Payment API", description = "Order Additional Payment API")
})
@RequiredArgsConstructor
public class OrderAdditionalPaymentApi {
    private final OrderAdditionalPaymentService service;
    private final PersistableOrderAdditionalPaymentPopulator persistableOrderAdditionalPaymentPopulator;
    private final ReadableOrderAdditionalPaymentPopulator readableOrderAdditionalPaymentPopulator;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderAdditionalPaymentApi.class);

    @RequestMapping(
            value = {"/private/order/{id}/additional/payment"},
            method = RequestMethod.POST)
    @ResponseBody
    public void saveAdditionalPayment(
            @PathVariable final String id,
            @RequestBody PersistableOrderAdditionalPayment payment
    ) throws Exception {
        LOGGER.info("OrderAdditionalPaymentApi :: saveAdditionalPayment id: {}", id);
        OrderAdditionalPayment target = new OrderAdditionalPayment();
        target.setId(id);
        service.saveOrderAdditionalPayment(persistableOrderAdditionalPaymentPopulator.populate(payment, target, null, null));
    }

    @RequestMapping(
            value = {"/private/order/{id}/additional/payment"},
            method = RequestMethod.GET)
    @ResponseBody
    public ReadableOrderAdditionalPayment getAdditionalPayment(
            @PathVariable final String id
    ) throws Exception {
        OrderAdditionalPayment payment = service.findById(id).orElse(new OrderAdditionalPayment());
        LOGGER.info("OrderAdditionalPaymentApi :: getAdditionalPayment id: {}", id);
        return readableOrderAdditionalPaymentPopulator.populate(payment, new ReadableOrderAdditionalPayment() ,null ,null);
    }

    @RequestMapping(
            value = {"/private/order/{id}/additional/payment/request"},
            method = RequestMethod.POST)
    @ResponseBody
    public void requestAdditionalPayment(
            @PathVariable final String id
    ) {
        LOGGER.info("OrderAdditionalPaymentApi :: requestAdditionalPayment id: {}", id);
        service.requestOrderAdditionalPayment(id);
    }
}
