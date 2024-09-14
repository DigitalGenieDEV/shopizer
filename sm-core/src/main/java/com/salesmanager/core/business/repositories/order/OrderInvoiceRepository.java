package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.OrderInvoice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderInvoiceRepository extends JpaRepository<OrderInvoice, Long> {
}
