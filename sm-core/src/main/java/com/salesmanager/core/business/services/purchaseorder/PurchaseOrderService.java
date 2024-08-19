package com.salesmanager.core.business.services.purchaseorder;

import com.salesmanager.core.model.purchaseorder.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PurchaseOrderService {

    PurchaseOrder saveAndUpdate(PurchaseOrder purchaseOrder);

    PurchaseOrder getById(Long poOrderId);

    Page<PurchaseOrder> getPurchaseOrders(Pageable pageable);
}
