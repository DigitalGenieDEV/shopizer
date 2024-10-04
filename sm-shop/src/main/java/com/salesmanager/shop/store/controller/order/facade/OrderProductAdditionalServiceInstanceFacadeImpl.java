package com.salesmanager.shop.store.controller.order.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductAdditionalServiceInstanceService;
import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.fulfillment.ReadableProductAdditionalService;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v1.PersistableOrderProductAdditionalServiceInstance;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductAdditionalServiceInstance;
import com.salesmanager.shop.populator.order.PersistableOrderProductAdditionalServiceInstancePopulator;
import com.salesmanager.shop.populator.order.ReadableOrderProductAdditionalServiceInstancePopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class OrderProductAdditionalServiceInstanceFacadeImpl implements OrderProductAdditionalServiceInstanceFacade {

    @Inject
    private OrderProductAdditionalServiceInstanceService orderProductAdditionalServiceInstanceService;

    @Inject
    private ReadableOrderProductAdditionalServiceInstancePopulator readableOrderProductAdditionalServiceInstancePopulator;

    @Inject
    private PersistableOrderProductAdditionalServiceInstancePopulator persistableOrderProductAdditionalServiceInstancePopulator;


    @Override
    public ReadableOrderProductAdditionalServiceInstance getById(Long id, MerchantStore merchantStore, Language language) {
        OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceService.getById(id);

        ReadableOrderProductAdditionalServiceInstance readableOrderProductAdditionalServiceInstance;
        try {
            readableOrderProductAdditionalServiceInstance = readableOrderProductAdditionalServiceInstancePopulator.populate(orderProductAdditionalServiceInstance, merchantStore, language);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error while getting order product additional service instance ", e);
        }
        return readableOrderProductAdditionalServiceInstance;
    }

    @Override
    public List<ReadableOrderProductAdditionalServiceInstance> listReadableOrderProductAdditionalServiceInstance(ReadableOrderProduct readableOrderProduct, MerchantStore merchantStore, Language language) {
        List<OrderProductAdditionalServiceInstance> orderProductAdditionalServiceInstanceList = orderProductAdditionalServiceInstanceService.listByOrderProductId(readableOrderProduct.getId());

        List<ReadableOrderProductAdditionalServiceInstance> list = orderProductAdditionalServiceInstanceList.stream()
                .map(instance -> {
                    try {
                        return readableOrderProductAdditionalServiceInstancePopulator.populate(instance, merchantStore, language);
                    } catch (ConversionException e) {
                        throw new ServiceRuntimeException(e);
                    }
                })
                .collect(Collectors.toList());

        return list;
    }

    @Override
    public Boolean saveAdditionalServiceInstance(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance, MerchantStore merchantStore, Language language) {
        OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance = null;
        List<ReadableProductAdditionalService> readableProductAdditionalServices = readableOrderProduct.getReadableProductAdditionalServices();

        Set<Long> validAdditionalServicesIds = readableProductAdditionalServices.stream().map(additionalServices -> additionalServices.getAdditionalServices().getId())
                .collect(Collectors.toSet());

        if (!validAdditionalServicesIds.contains(persistableOrderProductAdditionalServiceInstance.getAdditionalServiceId())) {
            throw new ServiceRuntimeException("Order product does not have such additional service, id: " + persistableOrderProductAdditionalServiceInstance.getAdditionalServiceId());
        }

        try {
            if (persistableOrderProductAdditionalServiceInstance.getContent() != null) {
                persistableOrderProductAdditionalServiceInstance.addMessage(persistableOrderProductAdditionalServiceInstance.getContent(), null);
            }
            orderProductAdditionalServiceInstance = persistableOrderProductAdditionalServiceInstancePopulator.populate(persistableOrderProductAdditionalServiceInstance, merchantStore, language);
        } catch (ConversionException e) {
            throw new ServiceRuntimeException("Error while saving order product additional service instance ", e);
        }
        return orderProductAdditionalServiceInstanceService.save(orderProductAdditionalServiceInstance);
    }

    @Override
    public Boolean replyAdditionalServiceInstance(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance, MerchantStore merchantStore, Language language) {
        OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceService.getById(persistableOrderProductAdditionalServiceInstance.getId());

        com.salesmanager.shop.model.order.v1.OrderProductAdditionalServiceInstance.MessageDetail messageDetail = persistableOrderProductAdditionalServiceInstance.getMessageContent().getSequenceOf(persistableOrderProductAdditionalServiceInstance.getReplyTo());
        if (messageDetail == null) {
            throw new ServiceRuntimeException("Sequence of index:" + persistableOrderProductAdditionalServiceInstance.getReplyTo() + " is not found");
        }

        if (persistableOrderProductAdditionalServiceInstance.getMessageContent().size() >= 2) {
            throw new ServiceRuntimeException("Order product additional service instance has already reply");
        }

        try {
            if (persistableOrderProductAdditionalServiceInstance.getReplyContent() != null) {
                persistableOrderProductAdditionalServiceInstance.replyMessage(persistableOrderProductAdditionalServiceInstance.getReplyContent(), persistableOrderProductAdditionalServiceInstance.getReplyFrom(), persistableOrderProductAdditionalServiceInstance.getReplyTo());
            }
            orderProductAdditionalServiceInstance = persistableOrderProductAdditionalServiceInstancePopulator.populate(persistableOrderProductAdditionalServiceInstance, orderProductAdditionalServiceInstance, merchantStore, language);
        } catch (ConversionException e) {
            throw new ServiceRuntimeException("Error while reply order product additional service instance ", e);
        }

        return orderProductAdditionalServiceInstanceService.save(orderProductAdditionalServiceInstance);
    }

    @Override
    public Boolean delAdditionalServiceInstanceReply(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance, MerchantStore merchantStore, Language language) {
        OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceService.getById(persistableOrderProductAdditionalServiceInstance.getId());

        try {
            persistableOrderProductAdditionalServiceInstance.getMessageContent().delReply();
            orderProductAdditionalServiceInstance = persistableOrderProductAdditionalServiceInstancePopulator.populate(persistableOrderProductAdditionalServiceInstance, orderProductAdditionalServiceInstance, merchantStore, language);
        } catch (ConversionException e) {
            throw new ServiceRuntimeException("Error while reply order product additional service instance ", e);
        }

        return orderProductAdditionalServiceInstanceService.save(orderProductAdditionalServiceInstance);
    }

    @Override
    public Boolean updateAdditionalServiceInstanceReply(ReadableOrderProduct readableOrderProduct, PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance, MerchantStore merchantStore, Language language) {
        OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceService.getById(persistableOrderProductAdditionalServiceInstance.getId());

        try {
            persistableOrderProductAdditionalServiceInstance.getMessageContent().updateReply(persistableOrderProductAdditionalServiceInstance.getReplyContent());
            orderProductAdditionalServiceInstance = persistableOrderProductAdditionalServiceInstancePopulator.populate(persistableOrderProductAdditionalServiceInstance, orderProductAdditionalServiceInstance, merchantStore, language);
        } catch (ConversionException e) {
            throw new ServiceRuntimeException("Error while reply order product additional service instance ", e);
        }

        return orderProductAdditionalServiceInstanceService.save(orderProductAdditionalServiceInstance);
    }

    @Override
    public Boolean confirmAdditionalServiceInstance(Long id, AdditionalServiceInstanceStatusEnums additionalServiceInstanceStatusEnums, MerchantStore merchantStore, Language language) {
        OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceService.getById(id);
        AdditionalServiceInstanceStatusEnums originStatus = orderProductAdditionalServiceInstance.getStatus();
        if (originStatus != AdditionalServiceInstanceStatusEnums.INITIAL) {
            throw new ServiceRuntimeException("Only INITIAL status support confirm");
        }
        if (additionalServiceInstanceStatusEnums != AdditionalServiceInstanceStatusEnums.AGREE
                && additionalServiceInstanceStatusEnums != AdditionalServiceInstanceStatusEnums.REJECT) {
            throw new ServiceRuntimeException("Status must be Agree or Reject");
        }

        orderProductAdditionalServiceInstance.setStatus(additionalServiceInstanceStatusEnums);
        return orderProductAdditionalServiceInstanceService.updateStatus(orderProductAdditionalServiceInstance);
    }
}
