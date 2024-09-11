package com.salesmanager.core.business.services.order;

import com.salesmanager.core.model.order.OrderAdditionalPayment;

import java.util.Optional;


public interface OrderAdditionalPaymentService {
	void saveOrderAdditionalPaymentService(OrderAdditionalPayment orderAdditionalPayment);
	Optional<OrderAdditionalPayment> findById(String id);
}
