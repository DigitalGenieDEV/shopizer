package com.salesmanager.shop.store.controller.crossorder.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrder;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsMsg;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsStatusChangeMsg;
import com.salesmanager.shop.model.purchaseorder.*;
import com.salesmanager.shop.utils.FieldMatch;

import java.util.List;

public interface SupplierCrossOrderFacade {

    List<ReadableSupplierCrossOrderLogistics> processSupplierCrossOrderLogisticsMsg(SupplierCrossOrderLogisticsMsg msg) throws ServiceException;

    ReadableSupplierCrossOrderLogistics processSupplierCrossOrderLogisticsStatusChangeMsg(SupplierCrossOrderLogisticsStatusChangeMsg msg) throws ServiceException;

    ReadableSupplierCrossOrder processOrderPayedMsg(OrderBuyerViewOrderPayMsg msg) throws ServiceException;

    List<ReadableSupplierCrossOrder> processOrderBatchPayMsg(OrderBatchPayMsg msg) throws ServiceException;

    ReadableSupplierCrossOrder processAnnounceSendGoodsMsg(OrderBuyerViewAnnounceSendGoodsMsg msg) throws ServiceException;

    ReadableSupplierCrossOrder processPartPartSendGoodsMsg(OrderBuyerViewPartPartSendGoodsMsg msg) throws ServiceException;

    ReadableSupplierCrossOrder processOrderConfirmReceiveGoodsMsg(OrderBuyerViewOrderConfirmReceiveGoodsMsg msg) throws ServiceException;

    ReadableSupplierCrossOrder processOrderSuccessMsg(OrderBuyerViewOrderSuccessMsg msg) throws ServiceException;

    ReadableSupplierCrossOrder processOrderBuyerClose(OrderBuyerViewOrderBuyerCloseMsg msg) throws ServiceException;

    List<ReadableSupplierCrossOrder> processLogisticsBuyerViewTraceMsg(LogisticsBuyerViewTraceMsg msg) throws ServiceException;

}
