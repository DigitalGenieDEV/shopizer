package com.salesmanager.core.business.services.purchaseorder;

import com.salesmanager.core.business.repositories.purchaseorder.PurchaseOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.purchaseorder.PurchaseOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, PurchaseOrder> implements PurchaseOrderService{

    private PurchaseOrderRepository repository;

    public PurchaseOrderServiceImpl(PurchaseOrderRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PurchaseOrder saveAndUpdate(PurchaseOrder purchaseOrder) {
        return this.saveAndFlush(purchaseOrder);
    }

    @Override
    public Page<PurchaseOrder> getPurchaseOrders(Pageable pageable) {
        return repository.findAll(pageable);
    }
}
