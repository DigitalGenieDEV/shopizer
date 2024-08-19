package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.business.repositories.crossorder.SupplierCrossOrderLogisticsTraceStepRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTraceStep;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class SupplierCrossOrderLogisticsTraceStepServiceImpl extends SalesManagerEntityServiceImpl<Long, SupplierCrossOrderLogisticsTraceStep> implements SupplierCrossOrderLogisticsTraceStepService   {

    private SupplierCrossOrderLogisticsTraceStepRepository repository;

    public SupplierCrossOrderLogisticsTraceStepServiceImpl(SupplierCrossOrderLogisticsTraceStepRepository repository) {
        super(repository);
        this.repository = repository;
    }
}
