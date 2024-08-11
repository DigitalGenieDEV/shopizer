package com.salesmanager.core.business.services.crossorder;

import com.salesmanager.core.model.crossorder.SupplierCrossOrder;

public interface SupplierCrossOrderService {

    SupplierCrossOrder saveAndUpdate(SupplierCrossOrder supplierCrossOrder);

    SupplierCrossOrder getByOrderIdStr(String orderId);
}
