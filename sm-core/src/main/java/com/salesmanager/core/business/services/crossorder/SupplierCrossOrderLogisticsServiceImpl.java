package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.business.repositories.crossorder.SupplierCrossOrderLogisticsRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SupplierCrossOrderLogisticsServiceImpl extends SalesManagerEntityServiceImpl<Long, SupplierCrossOrderLogistics> implements SupplierCrossOrderLogisticsService {

    private SupplierCrossOrderLogisticsRepository repository;

    public SupplierCrossOrderLogisticsServiceImpl(SupplierCrossOrderLogisticsRepository repository) {
        super(repository);
        this.repository = repository;
    }

    @Override
    public SupplierCrossOrderLogistics saveAndUpdate(SupplierCrossOrderLogistics supplierCrossOrderLogistics) {
        return saveAndFlush(supplierCrossOrderLogistics);
    }

    @Override
    public SupplierCrossOrderLogistics getByLogisticsId(String logisticsId) {
        return repository.findByLogisticsId(logisticsId);
    }

    @Override
    public List<SupplierCrossOrderLogistics> getLogisticsByOrderProductId(Long orderProductId) {
        return repository.findLogisticsByOrderProductId(orderProductId);
    }
}
