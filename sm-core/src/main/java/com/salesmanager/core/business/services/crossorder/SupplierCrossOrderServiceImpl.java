package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.business.repositories.crossorder.SupplierCrossOrderRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierCrossOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, SupplierCrossOrder> implements SupplierCrossOrderService {

    private SupplierCrossOrderRepository repository;

    public SupplierCrossOrderServiceImpl(SupplierCrossOrderRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SupplierCrossOrder getByOrderIdStr(String orderId) {
        return repository.findByOrderIdStr(orderId);
    }
}
