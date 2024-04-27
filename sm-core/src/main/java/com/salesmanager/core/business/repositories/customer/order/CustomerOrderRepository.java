package com.salesmanager.core.business.repositories.customer.order;


import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CustomerOrderRepository extends JpaRepository<CustomerOrder, Long>, CustomerOrderRepositoryCustom {

    @Query("select co from CustomerOrder co join fetch co.orders o join fetch o.merchant om "
            + "join fetch o.orderProducts op "
            + "left join fetch o.delivery od left join fetch od.country left join fetch od.zone "
            + "left join fetch o.billing ob left join fetch ob.country left join fetch ob.zone "
            + "left join fetch o.orderAttributes oa "
            + "join fetch o.orderTotal ot left "
            + "join fetch o.orderHistory oh left "
            + "join fetch op.downloads opd left "
            + "join fetch op.orderAttributes opa "
            + "left join fetch op.prices opp where co.id = ?1")
    CustomerOrder findOne(Long id);
}
