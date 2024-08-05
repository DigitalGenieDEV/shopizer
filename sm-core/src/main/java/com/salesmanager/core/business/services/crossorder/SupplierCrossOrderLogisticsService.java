package com.salesmanager.core.business.services.crossorder;


import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;

public interface SupplierCrossOrderLogisticsService {

    SupplierCrossOrderLogistics saveAndUpdate(SupplierCrossOrderLogistics supplierCrossOrderLogistics);

    SupplierCrossOrderLogistics getByLogisticsId(String logisticsId);
}
