package com.salesmanager.core.business.repositories.order;

import com.salesmanager.core.model.order.OrderInvoice;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.order.Order;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface OrderRepository extends JpaRepository<Order, Long>, OrderRepositoryCustom {

    @Query("select o from Order o join fetch o.merchant om "
    		+ "join fetch o.orderProducts op "
    		+ "left join fetch o.delivery od left join fetch od.country left join fetch od.zone "
    		+ "left join fetch o.billing ob left join fetch ob.country left join fetch ob.zone "
    		+ "left join fetch o.orderAttributes oa "
    		+ "join fetch o.orderTotal ot left "
    		+ "join fetch o.orderHistory oh left "
    		+ "join fetch op.downloads opd left "
    		+ "join fetch op.orderAttributes opa "
    		+ "left join fetch op.prices opp where o.id = ?1")
	Order findOne(Long id);

	@Query("select o from Order o join fetch o.merchant om "
			+ "join fetch o.orderProducts op "
			+ "left join fetch o.delivery od left join fetch od.country left join fetch od.zone "
			+ "left join fetch o.billing ob left join fetch ob.country left join fetch ob.zone "
			+ "left join fetch o.orderAttributes oa "
			+ "join fetch o.orderTotal ot left "
			+ "join fetch o.orderHistory oh left "
			+ "join fetch op.downloads opd left "
			+ "join fetch op.orderAttributes opa "
			+ "left join fetch op.prices opp where o.id = ?1 and o.customerId = ?2")
    Order findOneByCustomer(Long id, Long customerId);


	@Query("select o from Order o join fetch o.merchant om "
			+ "join fetch o.orderProducts op "
			+ "left join fetch o.delivery od left join fetch od.country left join fetch od.zone "
			+ "left join fetch o.billing ob left join fetch ob.country left join fetch ob.zone "
			+ "left join fetch o.orderAttributes oa "
			+ "join fetch o.orderTotal ot left "
			+ "join fetch o.orderHistory oh left "
			+ "join fetch op.downloads opd left "
			+ "join fetch op.orderAttributes opa "
			+ "left join fetch op.prices opp where o.customerId = ?1")
	Order findOrderListByCustomerOrderId(Long customerOrderId);



	@Modifying
	@Transactional
	@Query("UPDATE Order o SET o.status = :status WHERE o.id = :orderId")
	void updateOrderStatus(@Param("orderId") Long orderId, @Param("status") OrderStatus status);

	@Modifying
	@Transactional
	@Query("UPDATE Order o set o.orderInvoice.id = :invoiceId WHERE o.id = :orderId")
	void updateOrderInvoice(@Param("orderId") Long orderId, @Param("invoiceId") Long invoiceId);
}
