package com.salesmanager.core.business.services.crossorder;


import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface SupplierCrossOrderLogisticsService {

    SupplierCrossOrderLogistics saveAndUpdate(SupplierCrossOrderLogistics supplierCrossOrderLogistics);

    SupplierCrossOrderLogistics getByLogisticsId(String logisticsId);

    List<SupplierCrossOrderLogistics> getLogisticsByOrderProductId(Long orderProductId);

}
