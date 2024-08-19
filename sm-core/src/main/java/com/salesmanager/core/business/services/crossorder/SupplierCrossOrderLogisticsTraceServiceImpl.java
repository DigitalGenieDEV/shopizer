package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.business.repositories.crossorder.SupplierCrossOrderLogisticsTraceRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTrace;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierCrossOrderLogisticsTraceServiceImpl extends SalesManagerEntityServiceImpl<Long, SupplierCrossOrderLogisticsTrace> implements SupplierCrossOrderLogisticsTraceService  {


    private SupplierCrossOrderLogisticsTraceRepository repository;

    public SupplierCrossOrderLogisticsTraceServiceImpl(SupplierCrossOrderLogisticsTraceRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public List<SupplierCrossOrderLogisticsTrace> getByLogisticsId(String logisticsId) {
        return repository.findByLogisticsId(logisticsId);
    }

    @Override
    public SupplierCrossOrderLogisticsTrace saveAndUpdate(SupplierCrossOrderLogisticsTrace supplierCrossOrderLogisticsTrace) {
        return saveAndFlush(supplierCrossOrderLogisticsTrace);
    }

    @Override
    public List<SupplierCrossOrderLogisticsTrace> getLogisticsTraceByOrderProductId(Long orderProductId) {
        return repository.findLogisticsTraceByOrderProductId(orderProductId);
    }
}
