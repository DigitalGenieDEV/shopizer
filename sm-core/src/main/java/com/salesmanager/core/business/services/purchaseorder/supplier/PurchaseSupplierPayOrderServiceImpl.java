package com.salesmanager.core.business.services.purchaseorder.supplier;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierPayOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSupplierPayOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, PurchaseSupplierPayOrder> implements PurchaseSupplierPayOrderService {
    public PurchaseSupplierPayOrderServiceImpl(JpaRepository<PurchaseSupplierPayOrder, Long> repository) {
        super(repository);
    }
}
