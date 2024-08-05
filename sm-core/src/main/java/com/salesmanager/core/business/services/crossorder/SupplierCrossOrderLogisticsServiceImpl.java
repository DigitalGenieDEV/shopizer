package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.business.repositories.crossorder.SupplierCrossOrderLogisticsRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import org.springframework.stereotype.Service;

@Service
public class SupplierCrossOrderLogisticsServiceImpl extends SalesManagerEntityServiceImpl<Long, SupplierCrossOrderLogistics> implements SupplierCrossOrderLogisticsService {

    private SupplierCrossOrderLogisticsRepository repository;

    public SupplierCrossOrderLogisticsServiceImpl(SupplierCrossOrderLogisticsRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SupplierCrossOrderLogistics saveAndUpdate(SupplierCrossOrderLogistics supplierCrossOrderLogistics) {
        return saveAndUpdate(supplierCrossOrderLogistics);
    }

    @Override
    public SupplierCrossOrderLogistics getByLogisticsId(String logisticsId) {
        return repository.findByLogisticsId(logisticsId);
    }
}
