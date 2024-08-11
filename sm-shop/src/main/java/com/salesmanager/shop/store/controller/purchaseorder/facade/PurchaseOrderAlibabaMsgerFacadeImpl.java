package com.salesmanager.shop.store.controller.purchaseorder.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.model.purchaseorder.*;
import com.salesmanager.shop.store.controller.crossorder.facade.SupplierCrossOrderFacade;
import com.salesmanager.shop.store.controller.crossorder.facade.SupplierCrossOrderLogisticsFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseOrderAlibabaMsgerFacadeImpl implements PurchaseOrderAlibabaMsgerFacade{

    @Autowired
    private PurchaseSupplierOrderFacade purchaseSupplierOrderFacade;

    @Autowired
    private SupplierCrossOrderFacade supplierCrossOrderFacade;

    @Autowired
    private SupplierCrossOrderLogisticsFacade supplierCrossOrderLogisticsFacade;

    @Override
    public void orderBuyerViewOrderPay(OrderBuyerViewOrderPayMsg msg) throws ServiceException {
        // 1688跨境订单买家支付处理
        supplierCrossOrderFacade.processOrderPayedMsg(msg);
    }

    @Override
    public void orderBatchPay(OrderBatchPayMsg msg) throws ServiceException {
        // 1688跨境订单批量支付处理
        supplierCrossOrderFacade.processOrderBatchPayMsg(msg);
    }

    @Override
    public void orderBuyerViewAnnounceSendGoods(OrderBuyerViewAnnounceSendGoodsMsg msg) throws ServiceException {
        // 1688跨境订单卖家发货处理
        supplierCrossOrderFacade.processAnnounceSendGoodsMsg(msg);
    }

    @Override
    public void orderBuyerViewPartPartSendGoods(OrderBuyerViewPartPartSendGoodsMsg msg) throws ServiceException {
        // 1688跨境订单卖家部分发货处理
        supplierCrossOrderFacade.processPartPartSendGoodsMsg(msg);
    }

    @Override
    public void orderBuyerViewOrderConfirmReceiveGoods(OrderBuyerViewOrderConfirmReceiveGoodsMsg msg) throws ServiceException {
        // 1688跨境订单买家确认收货处理
        supplierCrossOrderFacade.processOrderConfirmReceiveGoodsMsg(msg);
    }

    @Override
    public void orderBuyerViewOrderSuccess(OrderBuyerViewOrderSuccessMsg msg) throws ServiceException {
        // 1688跨境订单交易成功处理
        supplierCrossOrderFacade.processOrderSuccessMsg(msg);
    }

    @Override
    public void orderBuyerViewOrderBuyerClose(OrderBuyerViewOrderBuyerCloseMsg msg) throws ServiceException {
        // 1688跨境订单交易关闭处理
        supplierCrossOrderFacade.processOrderBuyerClose(msg);
    }

    @Override
    public void logisticsBuyerViewTrace(LogisticsBuyerViewTraceMsg msg) throws ServiceException {
        // 1688跨境订单物流信息更新
        supplierCrossOrderFacade.processLogisticsBuyerViewTraceMsg(msg);
    }
}
