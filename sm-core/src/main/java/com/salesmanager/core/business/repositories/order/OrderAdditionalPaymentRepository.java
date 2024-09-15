package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.OrderAdditionalPayment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderAdditionalPaymentRepository extends JpaRepository<OrderAdditionalPayment, String> {
}
