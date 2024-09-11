package com.salesmanager.core.business.services.order;

import com.salesmanager.core.business.repositories.order.OrderAdditionalPaymentRepository;
import com.salesmanager.core.model.order.OrderAdditionalPayment;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service("orderAdditionalPaymentService")
public class OrderAdditionalPaymentServiceImpl implements OrderAdditionalPaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderAdditionalPaymentServiceImpl.class);

    private final OrderAdditionalPaymentRepository repository;

    @Override
    public void saveOrderAdditionalPaymentService(OrderAdditionalPayment orderAdditionalPayment) {
        LOGGER.info("OrderAdditionalPaymentServiceImpl :: saveOrderAdditionalPaymentService");
        repository.save(orderAdditionalPayment);
    }

    @Override
    public Optional<OrderAdditionalPayment> findById(String id) {
        LOGGER.info("OrderAdditionalPaymentServiceImpl :: findById");
        return repository.findById(id);
    }
}
