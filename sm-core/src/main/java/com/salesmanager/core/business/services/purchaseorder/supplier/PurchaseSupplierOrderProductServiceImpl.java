package com.salesmanager.core.business.services.purchaseorder.supplier;

import com.salesmanager.core.business.repositories.purchaseorder.PurchaseSupplierOrderProductRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSupplierOrderProductServiceImpl extends SalesManagerEntityServiceImpl<Long, PurchaseSupplierOrderProduct> implements PurchaseSupplierOrderProductService {

    private PurchaseSupplierOrderProductRepository repository;

    public PurchaseSupplierOrderProductServiceImpl(PurchaseSupplierOrderProductRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public PurchaseSupplierOrderProduct saveAndUpdate(PurchaseSupplierOrderProduct purchaseSupplierOrderProduct) {
        return saveAndFlush(purchaseSupplierOrderProduct);
    }

    @Override
    public PurchaseSupplierOrderProduct getByOrderProductId(Long orderProductId) {
        return repository.findByOrderProductId(orderProductId);
    }
}
