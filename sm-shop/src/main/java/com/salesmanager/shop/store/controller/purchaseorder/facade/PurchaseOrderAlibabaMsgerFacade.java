package com.salesmanager.shop.store.controller.purchaseorder.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.purchaseorder.*;

public interface PurchaseOrderAlibabaMsgerFacade {

    void orderBuyerViewOrderPay(OrderBuyerViewOrderPayMsg msg) throws ServiceException;

    void orderBatchPay(OrderBatchPayMsg msg) throws ServiceException;

    void orderBuyerViewAnnounceSendGoods(OrderBuyerViewAnnounceSendGoodsMsg msg) throws ServiceException;

    void orderBuyerViewPartPartSendGoods(OrderBuyerViewPartPartSendGoodsMsg msg) throws ServiceException;

    void orderBuyerViewOrderConfirmReceiveGoods(OrderBuyerViewOrderConfirmReceiveGoodsMsg msg) throws ServiceException;

    void orderBuyerViewOrderSuccess(OrderBuyerViewOrderSuccessMsg msg) throws ServiceException;

    void orderBuyerViewOrderBuyerClose(OrderBuyerViewOrderBuyerCloseMsg msg) throws ServiceException;

    void logisticsBuyerViewTrace(LogisticsBuyerViewTraceMsg msg) throws ServiceException;
}
