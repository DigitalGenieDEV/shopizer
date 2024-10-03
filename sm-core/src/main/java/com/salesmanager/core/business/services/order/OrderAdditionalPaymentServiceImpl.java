package com.salesmanager.core.business.services.order;

import com.google.api.gax.rpc.NotFoundException;
import com.salesmanager.core.business.repositories.order.OrderAdditionalPaymentRepository;
import com.salesmanager.core.model.order.OrderAdditionalPayment;
import com.salesmanager.core.model.order.OrderAdditionalPaymentStatus;
import lombok.RequiredArgsConstructor;
import org.opensearch.ResourceNotFoundException;
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
    public void saveOrderAdditionalPayment(OrderAdditionalPayment orderAdditionalPayment) {
        LOGGER.info("OrderAdditionalPaymentServiceImpl :: saveOrderAdditionalPayment");
        OrderAdditionalPayment payment = findById(orderAdditionalPayment.getId()).orElse(null);
        if(payment == null){
            LOGGER.info("OrderAdditionalPaymentServiceImpl :: save");
            repository.save(orderAdditionalPayment);
            }
        else if(payment.getStatus().equals(OrderAdditionalPaymentStatus.WAITING)) {
            LOGGER.info("OrderAdditionalPaymentServiceImpl :: save");
            repository.save(orderAdditionalPayment);
        } else {
            LOGGER.info("OrderAdditionalPaymentServiceImpl :: do not save");
        }
    }

    @Override
    public void completeAdditionalPayment(String id) {
        LOGGER.info("OrderAdditionalPaymentServiceImpl :: completeAdditionalPayment");
        OrderAdditionalPayment payment = findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Additional Payment with id " + id + " does not exist"));
        payment.setStatus(OrderAdditionalPaymentStatus.COMPLETE);
        repository.save(payment);
    }

    @Override
    public OrderAdditionalPayment requestOrderAdditionalPayment(String id) {
        LOGGER.info("OrderAdditionalPaymentServiceImpl :: requestOrderAdditionalPayment");
        OrderAdditionalPayment payment = findById(id).orElseThrow(() -> new ResourceNotFoundException("Order Additional Payment with id " + id + " does not exist"));
        payment.setStatus(OrderAdditionalPaymentStatus.REQUEST);
        // 요청 보내는 로직 추가 해야됨
        repository.save(payment);
        return payment;
    }

    @Override
    public Optional<OrderAdditionalPayment> findById(String id) {
        LOGGER.info("OrderAdditionalPaymentServiceImpl :: findById");
        return repository.findById(id);
    }
}
