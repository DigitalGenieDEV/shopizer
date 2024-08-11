package com.salesmanager.core.business.services.purchaseorder.supplier;

import com.salesmanager.core.business.repositories.purchaseorder.PurchaseSupplierOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrder;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSupplierOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, PurchaseSupplierOrder> implements PurchaseSupplierOrderService {

    private PurchaseSupplierOrderRepository repository;

    public PurchaseSupplierOrderServiceImpl(PurchaseSupplierOrderRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PurchaseSupplierOrder saveAndUpdate(PurchaseSupplierOrder purchaseSupplierOrder) {
        return this.saveAndFlush(purchaseSupplierOrder);
    }
}
