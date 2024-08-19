package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTrace;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface SupplierCrossOrderLogisticsTraceService {

    List<SupplierCrossOrderLogisticsTrace> getByLogisticsId(String logisticsId);

    SupplierCrossOrderLogisticsTrace saveAndUpdate(SupplierCrossOrderLogisticsTrace supplierCrossOrderLogisticsTrace);

    List<SupplierCrossOrderLogisticsTrace> getLogisticsTraceByOrderProductId(Long orderProductId);
}
