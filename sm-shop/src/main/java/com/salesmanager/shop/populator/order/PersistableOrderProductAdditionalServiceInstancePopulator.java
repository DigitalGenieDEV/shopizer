package com.salesmanager.shop.populator.order;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.v1.PersistableOrderProductAdditionalServiceInstance;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

@Component
public class PersistableOrderProductAdditionalServiceInstancePopulator extends AbstractDataPopulator<PersistableOrderProductAdditionalServiceInstance, OrderProductAdditionalServiceInstance> {

    @Override
    protected OrderProductAdditionalServiceInstance createTarget() {
        return new OrderProductAdditionalServiceInstance();
    }

    @Override
    public OrderProductAdditionalServiceInstance populate(PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance, OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance, MerchantStore store, Language language) throws ConversionException {

        if (persistableOrderProductAdditionalServiceInstance.getId() != null) {
            orderProductAdditionalServiceInstance.setId(persistableOrderProductAdditionalServiceInstance.getId());
        }

        if (persistableOrderProductAdditionalServiceInstance.getOrderProductId() != null) {
            orderProductAdditionalServiceInstance.setOrderProductId(persistableOrderProductAdditionalServiceInstance.getOrderProductId());
        }
        if (persistableOrderProductAdditionalServiceInstance.getAdditionalServiceId() != null) {
            orderProductAdditionalServiceInstance.setAdditionalServiceId(persistableOrderProductAdditionalServiceInstance.getAdditionalServiceId());
        }
        if (persistableOrderProductAdditionalServiceInstance.getMessageContent() != null) {
            orderProductAdditionalServiceInstance.setContent(persistableOrderProductAdditionalServiceInstance.buildContentString());
        }
        if (StringUtils.isNotBlank(persistableOrderProductAdditionalServiceInstance.getAdditionalFilename())) {
            orderProductAdditionalServiceInstance.setAdditionalFilename(persistableOrderProductAdditionalServiceInstance.getAdditionalFilename());
        }
        if (StringUtils.isNotBlank(persistableOrderProductAdditionalServiceInstance.getAdditionalFileUrl())) {
            orderProductAdditionalServiceInstance.setAdditionalFileUrl(persistableOrderProductAdditionalServiceInstance.getAdditionalFileUrl());
        }
        if (StringUtils.isNotBlank(persistableOrderProductAdditionalServiceInstance.getStatus())) {
            AdditionalServiceInstanceStatusEnums additionalServiceInstanceStatusEnums = AdditionalServiceInstanceStatusEnums.valueOf(persistableOrderProductAdditionalServiceInstance.getStatus());
            orderProductAdditionalServiceInstance.setStatus(additionalServiceInstanceStatusEnums);
        }

        return orderProductAdditionalServiceInstance;
    }
}
