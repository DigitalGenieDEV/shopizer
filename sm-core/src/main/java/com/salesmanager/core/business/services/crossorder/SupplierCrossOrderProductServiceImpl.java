package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.crossorder.SupplierCrossOrderProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierCrossOrderProductServiceImpl extends SalesManagerEntityServiceImpl<Long, SupplierCrossOrderProduct> implements SupplierCrossOrderProductService {
    public SupplierCrossOrderProductServiceImpl(JpaRepository<SupplierCrossOrderProduct, Long> repository) {
        super(repository);
    }
}
