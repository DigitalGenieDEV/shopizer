package com.salesmanager.core.business.services.purchaseorder.supplier;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrderPayTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

//@Service
public class PurchaseSupplierPayOrderTransactionServiceImpl extends SalesManagerEntityServiceImpl<Long, PurchaseSupplierOrderPayTransaction> implements PurchaseSupplierPayOrderTransactionService {
    public PurchaseSupplierPayOrderTransactionServiceImpl(JpaRepository<PurchaseSupplierOrderPayTransaction, Long> repository) {
        super(repository);
    }
}
