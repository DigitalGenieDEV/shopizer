package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import org.springframework.data.jpa.repository.Query;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {

    @Query("select op from OrderProduct op join fetch op.order o join fetch o.merchant om "
            + "left join fetch o.delivery od left join fetch od.country left join fetch od.zone "
            + "left join fetch o.billing ob left join fetch ob.country left join fetch ob.zone "
            + "left join fetch o.orderAttributes oa "
            + "join fetch o.orderTotal ot left "
            + "join fetch o.orderHistory oh left "
            + "join fetch op.prices opp where op.id = ?1 and om.id = ?2")
    OrderProduct findOne(Long id, Integer merchantId);

}
