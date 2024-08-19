package com.salesmanager.shop.store.controller.purchaseorder.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.purchaseorder.PersistablePurchaseOrder;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseOrder;
import com.salesmanager.shop.model.purchaseorder.ReadablePurchaseOrderList;

public interface PurchaseOrderFacade {

    ReadablePurchaseOrder createPurchaseOrder(PersistablePurchaseOrder persistablePurchaseOrder) throws ServiceException;

    ReadablePurchaseOrder confirmPurchaseOrder(Long purchaseOrderId) throws ServiceException;

    ReadablePurchaseOrderList getReadablePurchaseOrders(int page, int count);
}
