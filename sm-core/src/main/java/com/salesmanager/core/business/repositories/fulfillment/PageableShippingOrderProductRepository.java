package com.salesmanager.core.business.repositories.fulfillment;

import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

public interface PageableShippingOrderProductRepository extends PagingAndSortingRepository<OrderProduct, Long>{


    @Query("select orp from OrderProduct orp left join orp.qcInfo qc   where qc.status='APPROVE' " +
            " and (?1 is null or orp.productName like %?1%)  and (?2 is null or orp.order.id = ?2)")
    Page<OrderProduct> findByProductNameOrOrderId(String productName, Long orderId, Pageable pageable);

}



