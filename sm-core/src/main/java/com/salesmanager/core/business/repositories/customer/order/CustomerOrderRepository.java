package com.salesmanager.core.business.repositories.customer.order;


import com.salesmanager.core.model.customer.order.CustomerOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long> {
}
