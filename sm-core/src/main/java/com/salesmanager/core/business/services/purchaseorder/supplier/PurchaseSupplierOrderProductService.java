package com.salesmanager.core.business.services.purchaseorder.supplier;

import com.salesmanager.core.model.purchaseorder.PurchaseSupplierOrderProduct;

public interface PurchaseSupplierOrderProductService {

    PurchaseSupplierOrderProduct saveAndUpdate(PurchaseSupplierOrderProduct purchaseSupplierOrderProduct);

    PurchaseSupplierOrderProduct getByOrderProductId(Long orderProductId);
}
