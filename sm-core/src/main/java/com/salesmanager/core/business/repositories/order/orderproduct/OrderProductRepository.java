package com.salesmanager.core.business.repositories.order.orderproduct;

import com.salesmanager.core.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface OrderProductRepository extends JpaRepository<OrderProduct, Long>, OrderProductRepositoryCustom {

    @Query("select op from OrderProduct op join fetch op.order o join fetch o.merchant om "
            + "left join fetch o.delivery od left join fetch od.country left join fetch od.zone "
            + "left join fetch o.billing ob left join fetch ob.country left join fetch ob.zone "
            + "left join fetch o.orderAttributes oa "
            + "join fetch o.orderTotal ot left "
            + "join fetch o.orderHistory oh left "
            + "join fetch op.prices opp where op.id = ?1 and om.id = ?2")
    OrderProduct findOne(Long id, Integer merchantId);


    @Modifying
    @Transactional
    @Query("UPDATE OrderProduct p SET p.isInShippingOrder = :isInShippingOrder WHERE p.id = :id")
    void updateIsInShippingOrderById(@Param("isInShippingOrder") Boolean isInShippingOrder, @Param("id") Long id);



    @Modifying
    @Transactional
    @Query("UPDATE OrderProduct p SET p.shippingDocumentOrder.id = :shippingDocumentOrderId WHERE p.id = :id")
    void updateShippingDocumentOrderIdById(@Param("shippingDocumentOrderId") Long shippingDocumentOrderId, @Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE OrderProduct p SET p.qcInfo.id = :qcInfoId WHERE p.id = :id")
    void updateQcInfoIdByOrderProductId(@Param("qcInfoId") Long qcInfoId, @Param("id") Long id);



    @Query("select op.id from OrderProduct op  where op.shippingDocumentOrder.id = ?1 ")
    List<Long> findIdListByShippingDocumentOrderId(Long shippingDocumentOrderId);



    @Query("select op from OrderProduct op join fetch op.order o join fetch o.merchant om "
            + " left join fetch op.prices opp where op.order.id = ?1 ")
    List<OrderProduct> findListByOrderId(Long orderId);


    @Modifying
    @Transactional
    @Query("UPDATE OrderProduct p SET p.order.id = :orderId WHERE p.id = :id")
    void updateOrderIdById(@Param("orderId") Long orderId, @Param("id") Long id);

}
