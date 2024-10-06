package com.salesmanager.core.business.services.order.orderproduct;

import com.salesmanager.core.business.repositories.order.orderproduct.OrderProductAdditionalServiceInstanceRepository;
import com.salesmanager.core.model.order.orderproduct.OrderProductAdditionalServiceInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderProductAdditionalServiceInstanceServiceImpl implements OrderProductAdditionalServiceInstanceService {
    public static final Logger LOGGER = LoggerFactory.getLogger(OrderProductAdditionalServiceInstanceServiceImpl.class);

    private final OrderProductAdditionalServiceInstanceRepository orderProductAdditionalServiceInstanceRepository;

    public OrderProductAdditionalServiceInstanceServiceImpl(OrderProductAdditionalServiceInstanceRepository orderProductAdditionalServiceInstanceRepository) {
        this.orderProductAdditionalServiceInstanceRepository = orderProductAdditionalServiceInstanceRepository;
    }

    @Override
    public OrderProductAdditionalServiceInstance getById(Long id) {
        return orderProductAdditionalServiceInstanceRepository.getById(id);
    }

    @Override
    public List<OrderProductAdditionalServiceInstance> listByOrderProductId(Long orderProductId) {
        return orderProductAdditionalServiceInstanceRepository.queryByOrderProductId(orderProductId);
    }

    @Override
    public Boolean save(OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance) {
        try {
            orderProductAdditionalServiceInstanceRepository.save(orderProductAdditionalServiceInstance);
        } catch (Exception e) {
            if (e.getCause() != null && e.getCause().getCause() != null && e.getCause().getCause().getMessage().contains("Duplicate")) {
                throw new RuntimeException("Additional Service Instance already exists, orderProductId: " + orderProductAdditionalServiceInstance.getOrderProductId()
                        + ", additionalServiceId: " + orderProductAdditionalServiceInstance.getAdditionalServiceId());
            }
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public Boolean updateStatus(OrderProductAdditionalServiceInstance orderProductAdditionalServiceInstance) {
        return orderProductAdditionalServiceInstanceRepository.updateStatusById(orderProductAdditionalServiceInstance.getId(), orderProductAdditionalServiceInstance.getStatus()) == 1;
    }
}
