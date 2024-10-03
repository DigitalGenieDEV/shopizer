package com.salesmanager.shop.populator.order;

import com.alibaba.fastjson.JSONObject;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.fulfillment.facade.AdditionalServicesFacade;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductAdditionalServiceInstance;
import org.springframework.stereotype.Component;

import javax.inject.Inject;

@Component
public class ReadableOrderProductAdditionalServiceInstancePopulator extends AbstractDataPopulator<OrderProductAdditionalServiceInstance, ReadableOrderProductAdditionalServiceInstance> {

    @Inject
    private AdditionalServicesFacade additionalServicesFacade;

    @Override
    protected ReadableOrderProductAdditionalServiceInstance createTarget() {
        return new ReadableOrderProductAdditionalServiceInstance();
    }

    @Override
    public ReadableOrderProductAdditionalServiceInstance populate(OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance, ReadableOrderProductAdditionalServiceInstance readableOrderProductAdditionalServiceInstance, MerchantStore store, Language language) throws ConversionException {

        readableOrderProductAdditionalServiceInstance.setId(orderProductAdditionalServiceInstance.getId());
        readableOrderProductAdditionalServiceInstance.setOrderProductId(orderProductAdditionalServiceInstance.getOrderProductId());
        readableOrderProductAdditionalServiceInstance.setAdditionalServiceId(orderProductAdditionalServiceInstance.getAdditionalServiceId());
        ReadableAdditionalServices additionalServices = additionalServicesFacade.queryAdditionalServicesById(orderProductAdditionalServiceInstance.getAdditionalServiceId(), language);
        readableOrderProductAdditionalServiceInstance.setAdditionalServices(additionalServices);
        readableOrderProductAdditionalServiceInstance.setMessageContent(JSONObject.parseObject(orderProductAdditionalServiceInstance.getContent(), com.salesmanager.shop.model.order.v1.OrderProductAdditionalServiceInstance.MessageContent.class));
        readableOrderProductAdditionalServiceInstance.setAdditionalFilename(orderProductAdditionalServiceInstance.getAdditionalFilename());
        readableOrderProductAdditionalServiceInstance.setAdditionalFileUrl(orderProductAdditionalServiceInstance.getAdditionalFileUrl());
        readableOrderProductAdditionalServiceInstance.setStatus(orderProductAdditionalServiceInstance.getStatus().name());
        readableOrderProductAdditionalServiceInstance.setDateCreated(orderProductAdditionalServiceInstance.getAuditSection().getDateCreated());

        return readableOrderProductAdditionalServiceInstance;
    }
}
