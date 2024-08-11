package com.salesmanager.shop.store.controller.crossorder.facade;

import com.salesmanager.core.business.alibaba.param.*;
import com.salesmanager.core.business.constants.CrossOrderConstants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.alibaba.logistics.AlibabaLogisticsService;
import com.salesmanager.core.business.services.alibaba.trade.AlibabaTradeOrderService;
import com.salesmanager.core.business.services.crossorder.SupplierCrossOrderLogisticsService;
import com.salesmanager.core.business.services.crossorder.SupplierCrossOrderLogisticsTraceService;
import com.salesmanager.core.business.services.crossorder.SupplierCrossOrderService;
import com.salesmanager.core.business.services.purchaseorder.PurchaseOrderService;
import com.salesmanager.core.business.services.purchaseorder.supplier.PurchaseSupplierOrderProductService;
import com.salesmanager.core.business.services.purchaseorder.supplier.PurchaseSupplierOrderService;
import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.crossorder.SupplierCrossOrderProduct;
import com.salesmanager.core.model.crossorder.logistics.*;
import com.salesmanager.core.model.purchaseorder.*;
import com.salesmanager.shop.listener.AlibabaOpenMessageListener;
import com.salesmanager.shop.mapper.crossorder.ReadableSupplierCrossOrderLogisticsMapper;
import com.salesmanager.shop.mapper.crossorder.ReadableSupplierCrossOrderMapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrder;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsMsg;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsStatusChangeMsg;
import com.salesmanager.shop.model.purchaseorder.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class SupplierCrossOrderFacadeImpl implements SupplierCrossOrderFacade{

    private static final Logger LOGGER = LoggerFactory.getLogger(SupplierCrossOrderFacadeImpl.class);

    @Autowired
    private SupplierCrossOrderService supplierCrossOrderService;

    @Autowired
    private SupplierCrossOrderLogisticsService supplierCrossOrderLogisticsService;

    @Autowired
    private SupplierCrossOrderLogisticsTraceService supplierCrossOrderLogisticsTraceService;

    @Autowired
    private PurchaseSupplierOrderService purchaseSupplierOrderService;

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private PurchaseSupplierOrderProductService purchaseSupplierOrderProductService;

    @Autowired
    private AlibabaLogisticsService alibabaLogisticsService;

    @Autowired
    private AlibabaTradeOrderService alibabaTradeOrderService;

    @Autowired
    private ReadableSupplierCrossOrderLogisticsMapper readableSupplierCrossOrderLogisticsMapper;

    @Autowired
    private ReadableSupplierCrossOrderMapper readableSupplierCrossOrderMapper;

    @Override
    public List<ReadableSupplierCrossOrderLogistics> processSupplierCrossOrderLogisticsMsg(SupplierCrossOrderLogisticsMsg msg) throws ServiceException {
        SupplierCrossOrder supplierCrossOrder = supplierCrossOrderService.getByOrderIdStr(String.valueOf(msg.getOrderId()));

        if (supplierCrossOrder == null) {
            throw new ServiceException("supplier cross order id [" + msg.getOrderId() + "] not found");
        }

        AlibabaTradeGetLogisticsInfosBuyerViewParam param = new AlibabaTradeGetLogisticsInfosBuyerViewParam();
        param.setOrderId(msg.getOrderId());
        param.setWebSite("1688");
        AlibabaLogisticsOpenPlatformLogisticsOrder[] logisticsOpenPlatformLogisticsOrders = alibabaLogisticsService.getLogisticsInfosBuyerView(param);

        List<SupplierCrossOrderLogistics> supplierCrossOrderLogistics = Arrays.stream(logisticsOpenPlatformLogisticsOrders).map(logisticsOrder -> {
            SupplierCrossOrderLogistics logistics = supplierCrossOrderLogisticsService.getByLogisticsId(logisticsOrder.getLogisticsId());

            if (logistics == null) {
                logistics = convertToSupplierCrossOrderLogistics(logisticsOrder);
//                logistics.setSupplierCrossOrder(supplierCrossOrder);
                logistics = supplierCrossOrderLogisticsService.saveAndUpdate(logistics);
            }

            return logistics;
        }).collect(Collectors.toList());

        // 更新采购单商品物流状态
        supplierCrossOrder.getProducts().forEach(supplierCrossOrderProduct -> {
            PurchaseSupplierOrderProduct purchaseSupplierOrderProduct = supplierCrossOrderProduct.getPsoOrderProduct();
            purchaseSupplierOrderProduct.setStatus(PurchaseSupplierOrderProductStatus.SHIPPED);

            purchaseSupplierOrderProductService.saveAndUpdate(purchaseSupplierOrderProduct);
        });

//        PurchaseSupplierOrder purchaseSupplierOrder = supplierCrossOrder.getPsoOrder();


        return supplierCrossOrderLogistics.stream().map(orderLogistics -> readableSupplierCrossOrderLogisticsMapper.convert(orderLogistics, null, null))
                .collect(Collectors.toList());
    }



    @Override
    public ReadableSupplierCrossOrderLogistics processSupplierCrossOrderLogisticsStatusChangeMsg(SupplierCrossOrderLogisticsStatusChangeMsg msg) throws ServiceException {
        SupplierCrossOrderLogistics supplierCrossOrderLogistics = supplierCrossOrderLogisticsService.getByLogisticsId(msg.getLogisticsId());

        if (supplierCrossOrderLogistics == null) {
            throw new ServiceException("supplier cross order logistics id [" + msg.getLogisticsId() + "] not found");
        }

        supplierCrossOrderLogistics.setStatus(msg.getStatusChanged());

        supplierCrossOrderLogisticsService.saveAndUpdate(supplierCrossOrderLogistics);

//        supplierCrossOrderLogistics.getSupplierCrossOrder().getProducts().forEach(supplierCrossOrderProduct -> {
//            // 发货（CONSIGN）、揽收（ACCEPT）、运输（TRANSPORT）、派送（DELIVERING）、签收（SIGN）
//            PurchaseSupplierOrderProduct purchaseSupplierOrderProduct = supplierCrossOrderProduct.getPsoOrderProduct();
//            purchaseSupplierOrderProduct.setStatus(getPurchaseSupplierOrderProductStatus(msg.getStatusChanged()));
//
//            purchaseSupplierOrderProductService.saveAndUpdate(purchaseSupplierOrderProduct);
//        });

        return readableSupplierCrossOrderLogisticsMapper.convert(supplierCrossOrderLogistics, null, null);
    }

    @Transactional
    @Override
    public ReadableSupplierCrossOrder processOrderPayedMsg(OrderBuyerViewOrderPayMsg msg) throws ServiceException {
        SupplierCrossOrder supplierCrossOrder = supplierCrossOrderService.getByOrderIdStr(String.valueOf(msg.getOrderId()));

        if (supplierCrossOrder == null) {
            throw new ServiceException("supplier cross order [" + msg.getOrderId() +"] not found");
        }

        // 更新供应商采购单状态
        supplierCrossOrder.setStatus(msg.getCurrentStatus());

        // 更新供应商采购单商品状态
        updateCrossOrderProductListStatus(supplierCrossOrder);

        supplierCrossOrderService.saveAndUpdate(supplierCrossOrder);

        updatePurchaseSupplierOrderProductStatus(supplierCrossOrder);

        // 更新采购单支付状态
        checkAndUpdatePurchaseSupplierOrderPaidStatus(supplierCrossOrder.getPsoOrder());

        return readableSupplierCrossOrderMapper.convert(supplierCrossOrder, null, null);
    }

    @Transactional
    @Override
    public List<ReadableSupplierCrossOrder> processOrderBatchPayMsg(OrderBatchPayMsg msg) throws ServiceException {
        List<OrderBatchPayMsg.OrderPayResult> successedOrderPayResults =
                msg.getBatchPay().stream().filter(orderPayResult -> StringUtils.equals(orderPayResult.getStatus(), "successed")).collect(Collectors.toList());

        List<ReadableSupplierCrossOrder> readableSupplierCrossOrders = new ArrayList<>();
        for (OrderBatchPayMsg.OrderPayResult orderPayResult : successedOrderPayResults) {
            try {
                OrderBuyerViewOrderPayMsg orderBuyerViewOrderPayMsg = new OrderBuyerViewOrderPayMsg();
                orderBuyerViewOrderPayMsg.setOrderId(Long.valueOf(orderPayResult.getOrderId()));
                orderBuyerViewOrderPayMsg.setCurrentStatus(CrossOrderConstants.STATUS_WAITSELLERSEND);

                readableSupplierCrossOrders.add(processOrderPayedMsg(orderBuyerViewOrderPayMsg));
            } catch (ServiceException e) {
                LOGGER.error("batch pay process order [" + orderPayResult.getOrderId() +"] exception", e);
            }
        }

        return readableSupplierCrossOrders;
    }

    private void updateCrossOrderProductListStatus(SupplierCrossOrder supplierCrossOrder) {
        AlibabaTradeGetBuyerViewParam buyerViewParam = new AlibabaTradeGetBuyerViewParam();
        buyerViewParam.setOrderId(Long.valueOf(supplierCrossOrder.getOrderIdStr()));
        buyerViewParam.setWebSite("1688");

        AlibabaOpenplatformTradeModelTradeInfo tradeInfo = alibabaTradeOrderService.getCrossOrderBuyerView(buyerViewParam);

        supplierCrossOrder.getProducts().stream().forEach(crossOrderProduct -> {
            updateCrossOrderProductStatus(crossOrderProduct, tradeInfo.getProductItems());
        });
    }

    private void updateCrossOrderProductStatus(SupplierCrossOrderProduct crossOrderProduct, AlibabaOpenplatformTradeModelProductItemInfo[] productItemInfos) {
        Arrays.stream(productItemInfos).filter(productItemInfo -> crossOrderProduct.getSpecId().equals(productItemInfo.getSpecId())).findFirst()
                .ifPresent(productItemInfo -> {
                    crossOrderProduct.setStatus(productItemInfo.getStatus());
                    crossOrderProduct.setStatusStr(productItemInfo.getStatusStr());
                });
    }


    @Transactional
    @Override
    public ReadableSupplierCrossOrder processAnnounceSendGoodsMsg(OrderBuyerViewAnnounceSendGoodsMsg msg) throws ServiceException {
        SupplierCrossOrder supplierCrossOrder = supplierCrossOrderService.getByOrderIdStr(String.valueOf(msg.getOrderId()));

        if (supplierCrossOrder == null) {
            throw new ServiceException("supplier cross order [" + msg.getOrderId() +"] not found");
        }

        supplierCrossOrder.setStatus(msg.getCurrentStatus());

        updateCrossOrderProductListStatus(supplierCrossOrder);

        // 查询更新物流状态
        getAndUpdateSupplierCrossOrderLogisticsList(supplierCrossOrder);

        // 查询更逊物流追踪信息
        getAndUpdateSupplierCrossOrderLogisticsTrace(supplierCrossOrder);

        supplierCrossOrderService.saveAndUpdate(supplierCrossOrder);

        // 更新采购单物流状态
        checkAndUpdatePurchaseSupplierOrderShippedStatus(supplierCrossOrder.getPsoOrder());

        return readableSupplierCrossOrderMapper.convert(supplierCrossOrder, null, null);
    }

    @Transactional
    @Override
    public ReadableSupplierCrossOrder processPartPartSendGoodsMsg(OrderBuyerViewPartPartSendGoodsMsg msg) throws ServiceException {
        SupplierCrossOrder supplierCrossOrder = supplierCrossOrderService.getByOrderIdStr(String.valueOf(msg.getOrderId()));

        if (supplierCrossOrder == null) {
            throw new ServiceException("supplier cross order [" + msg.getOrderId() +"] not found");
        }

        supplierCrossOrder.setStatus(msg.getCurrentStatus());

        updateCrossOrderProductListStatus(supplierCrossOrder);

        // 查询更新物流状态
        getAndUpdateSupplierCrossOrderLogisticsList(supplierCrossOrder);

        // 查询更新物流追踪信息
        getAndUpdateSupplierCrossOrderLogisticsTrace(supplierCrossOrder);

        supplierCrossOrderService.saveAndUpdate(supplierCrossOrder);

        // 更新采购单物流状态
        checkAndUpdatePurchaseSupplierOrderShippedStatus(supplierCrossOrder.getPsoOrder());

        return readableSupplierCrossOrderMapper.convert(supplierCrossOrder, null, null);
    }

    @Transactional
    @Override
    public ReadableSupplierCrossOrder processOrderConfirmReceiveGoodsMsg(OrderBuyerViewOrderConfirmReceiveGoodsMsg msg) throws ServiceException {
        SupplierCrossOrder supplierCrossOrder = supplierCrossOrderService.getByOrderIdStr(String.valueOf(msg.getOrderId()));

        if (supplierCrossOrder == null) {
            throw new ServiceException("supplier cross order [" + msg.getOrderId() +"] not found");
        }

        supplierCrossOrder.setStatus(CrossOrderConstants.STATUS_CONFIRM_GOODS);

        updateCrossOrderProductListStatus(supplierCrossOrder);

        // 查询更新物流状态
        getAndUpdateSupplierCrossOrderLogisticsList(supplierCrossOrder);

        // 查询更新物流追踪信息
        getAndUpdateSupplierCrossOrderLogisticsTrace(supplierCrossOrder);

        supplierCrossOrderService.saveAndUpdate(supplierCrossOrder);

        // 更新采购单确认收货状态
        checkAndUpdatePurchaseSupplierOrderReceivedStatus(supplierCrossOrder.getPsoOrder());

        return readableSupplierCrossOrderMapper.convert(supplierCrossOrder, null, null);
    }

    @Transactional
    @Override
    public ReadableSupplierCrossOrder processOrderSuccessMsg(OrderBuyerViewOrderSuccessMsg msg) throws ServiceException {
        SupplierCrossOrder supplierCrossOrder = supplierCrossOrderService.getByOrderIdStr(String.valueOf(msg.getOrderId()));

        if (supplierCrossOrder == null) {
            throw new ServiceException("supplier cross order [" + msg.getOrderId() +"] not found");
        }

        supplierCrossOrder.setStatus(msg.getCurrentStatus());

        updateCrossOrderProductListStatus(supplierCrossOrder);

        // 查询更新物流状态
        getAndUpdateSupplierCrossOrderLogisticsList(supplierCrossOrder);

        // 查询更新物流追踪信息
        getAndUpdateSupplierCrossOrderLogisticsTrace(supplierCrossOrder);

        supplierCrossOrderService.saveAndUpdate(supplierCrossOrder);

        // 更新采购单交易完成
        checkAndUpdatePurchaseSupplierOrderCompletedStatus(supplierCrossOrder.getPsoOrder());

        return readableSupplierCrossOrderMapper.convert(supplierCrossOrder, null, null);
    }

    private void updatePurchaseSupplierOrderProductStatus(SupplierCrossOrder supplierCrossOrder) {

//        List<SupplierCrossOrderLogisticsOrderGoods> supplierCrossOrderLogisticsOrderGoodsList =
//                supplierCrossOrderLogistics.stream().flatMap(orderLogistics -> orderLogistics.getLogisticsOrderGoods().stream())
//                        .collect(Collectors.toList());

        // 更新
        supplierCrossOrder.getProducts().stream().forEach(crossOrderProduct -> {
            PurchaseSupplierOrderProduct purchaseSupplierOrderProduct = crossOrderProduct.getPsoOrderProduct();
            purchaseSupplierOrderProduct.setStatus(getPurchaseSupplierOrderProductStatus(crossOrderProduct.getStatus()));
            purchaseSupplierOrderProductService.saveAndUpdate(purchaseSupplierOrderProduct);
        });
    }


    @Transactional
    @Override
    public ReadableSupplierCrossOrder processOrderBuyerClose(OrderBuyerViewOrderBuyerCloseMsg msg) throws ServiceException {
        SupplierCrossOrder supplierCrossOrder = supplierCrossOrderService.getByOrderIdStr(String.valueOf(msg.getOrderId()));

        if (supplierCrossOrder == null) {
            throw new ServiceException("supplier cross order [" + msg.getOrderId() +"] not found");
        }

        supplierCrossOrder.setStatus(msg.getCurrentStatus());

        updateCrossOrderProductListStatus(supplierCrossOrder);

        supplierCrossOrderService.saveAndUpdate(supplierCrossOrder);

        // 更改采购单取消状态
        checkAndUpdatePurchaseSupplierOrderCancelStatus(supplierCrossOrder.getPsoOrder());

        return readableSupplierCrossOrderMapper.convert(supplierCrossOrder, null, null);
    }

    @Transactional
    @Override
    public List<ReadableSupplierCrossOrder> processLogisticsBuyerViewTraceMsg(LogisticsBuyerViewTraceMsg msg) throws ServiceException {
        List<LogisticsBuyerViewTraceMsg.OrderLogsItem> orderLogsItems = msg.getOrderLogsItems();

        // 查询物流单关联的 1688跨境订单列表
        List<SupplierCrossOrder> supplierCrossOrders =
                orderLogsItems.stream().map(orderLogsItem -> orderLogsItem.getOrderEntryId()).map(orderEntryId -> supplierCrossOrderService.getByOrderIdStr(String.valueOf(orderEntryId)))
                        .filter(supplierCrossOrder -> supplierCrossOrder != null)
                .collect(Collectors.toList());

        if (supplierCrossOrders.size() <= 0) {
            return new ArrayList<>();
        }

        // 采购单商品根据物流情况需要设置的状态
        PurchaseSupplierOrderProductStatus purchaseSupplierOrderProductStatus = getPurchaseSupplierOrderProductStatus(msg.getStatusChanged());

        // 供应商采购订单商品更新状态
        supplierCrossOrders.stream().forEach(supplierCrossOrder -> {
            // 更新物流状态
            getAndUpdateSupplierCrossOrderLogisticsList(supplierCrossOrder);

            // 更新物流追踪信息
            getAndUpdateSupplierCrossOrderLogisticsTrace(supplierCrossOrder);

            // 更新跨境订单商品状态
            supplierCrossOrder.getProducts().stream().forEach(crossOrderProduct -> {
                PurchaseSupplierOrderProduct purchaseSupplierOrderProduct = crossOrderProduct.getPsoOrderProduct();
                purchaseSupplierOrderProduct.setStatus(purchaseSupplierOrderProductStatus);


                purchaseSupplierOrderProductService.saveAndUpdate(purchaseSupplierOrderProduct);
            });
        });


        return supplierCrossOrders.stream().map(supplierCrossOrder -> readableSupplierCrossOrderMapper.convert(supplierCrossOrder, null, null)).collect(Collectors.toList());
    }


    private void checkAndUpdatePurchaseSupplierOrderCancelStatus(PurchaseSupplierOrder purchaseSupplierOrder) {
        int cancelCrossOrdersCount = purchaseSupplierOrder.getCrossOrders().stream()
                .filter(supplierCrossOrder -> supplierCrossOrder.isStatusGte(CrossOrderConstants.STATUS_CANCEL)).collect(Collectors.toList()).size();

        if (cancelCrossOrdersCount == purchaseSupplierOrder.getCrossOrders().size()) {
            purchaseSupplierOrder.setStatus(PurchaseSupplierOrderStatus.CANCELED);
            purchaseSupplierOrderService.saveAndUpdate(purchaseSupplierOrder);
        }

        checkAndUpdatePurchaseOrderCancelStatus(purchaseSupplierOrder.getPurchaseOrder());
    }

    private void checkAndUpdatePurchaseOrderCancelStatus(PurchaseOrder purchaseOrder) {
        int completedSupplierOrdersCount = purchaseOrder.getPurchaseSupplierOrders().stream()
                .filter(purchaseSupplierOrder -> purchaseSupplierOrder.isStatusGte(PurchaseSupplierOrderStatus.CANCELED)).collect(Collectors.toList()).size();

        if (completedSupplierOrdersCount == purchaseOrder.getPurchaseSupplierOrders().size()) {
            purchaseOrder.setStatus(PurchaseOrderStatus.CANCELLED);
        }

        purchaseOrderService.saveAndUpdate(purchaseOrder);
    }

    private void checkAndUpdatePurchaseSupplierOrderCompletedStatus(PurchaseSupplierOrder purchaseSupplierOrder) {
        int successCrossOrdersCount = purchaseSupplierOrder.getCrossOrders().stream()
                .filter(supplierCrossOrder -> supplierCrossOrder.isStatusGte(CrossOrderConstants.STATUS_SUCCESS)).collect(Collectors.toList()).size();

        // 如果当前状态小于 Completed，设置为 Completed
        if (successCrossOrdersCount == purchaseSupplierOrder.getCrossOrders().size() && purchaseSupplierOrder.isStatusLt(PurchaseSupplierOrderStatus.COMPLETED)) {
            purchaseSupplierOrder.setStatus(PurchaseSupplierOrderStatus.COMPLETED);
            purchaseSupplierOrderService.saveAndUpdate(purchaseSupplierOrder);
        }

        checkAndUpdatePurchaseOrderCompletedStatus(purchaseSupplierOrder.getPurchaseOrder());
    }

    private void checkAndUpdatePurchaseOrderCompletedStatus(PurchaseOrder purchaseOrder) {
        int completedSupplierOrdersCount = purchaseOrder.getPurchaseSupplierOrders().stream()
                .filter(purchaseSupplierOrder -> purchaseSupplierOrder.isStatusGte(PurchaseSupplierOrderStatus.COMPLETED)).collect(Collectors.toList()).size();

        if (completedSupplierOrdersCount == purchaseOrder.getPurchaseSupplierOrders().size() && purchaseOrder.isStatusLt(PurchaseOrderStatus.COMPLETED)) {
            purchaseOrder.setStatus(PurchaseOrderStatus.COMPLETED);
        }

        purchaseOrderService.saveAndUpdate(purchaseOrder);
    }

    private List<SupplierCrossOrderLogistics> getAndUpdateSupplierCrossOrderLogisticsList(SupplierCrossOrder supplierCrossOrder) {
        try {
            AlibabaTradeGetLogisticsInfosBuyerViewParam param = new AlibabaTradeGetLogisticsInfosBuyerViewParam();
            param.setOrderId(Long.valueOf(supplierCrossOrder.getOrderIdStr()));
            param.setWebSite("1688");

            AlibabaLogisticsOpenPlatformLogisticsOrder[] logisticsOpenPlatformLogisticsOrders = alibabaLogisticsService.getLogisticsInfosBuyerView(param);

            if (logisticsOpenPlatformLogisticsOrders == null) {
                return null;
            }

            List<SupplierCrossOrderLogistics> supplierCrossOrderLogistics = Arrays.stream(logisticsOpenPlatformLogisticsOrders).map(logisticsOrder -> {
                SupplierCrossOrderLogistics logistics = supplierCrossOrderLogisticsService.getByLogisticsId(logisticsOrder.getLogisticsId());

                if (logistics == null) {
                    logistics = convertToSupplierCrossOrderLogistics(logisticsOrder);
                    logistics.addSupplierCrossOrder(supplierCrossOrder);
                } else {
                    logistics.setStatus(logisticsOrder.getStatus());

                    if (!logistics.hasSupplierCrossOrder(supplierCrossOrder)) {
                        logistics.addSupplierCrossOrder(supplierCrossOrder);
                    }
                }

                logistics = supplierCrossOrderLogisticsService.saveAndUpdate(logistics);

                return logistics;
            }).collect(Collectors.toList());

            return supplierCrossOrderLogistics;
        } catch (Exception e) {
            LOGGER.error("get update order [" + supplierCrossOrder.getOrderIdStr() + "]  SupplierCrossOrderLogisticsList error", e);
        }

        return null;
    }

    private List<SupplierCrossOrderLogisticsTrace> getAndUpdateSupplierCrossOrderLogisticsTrace(SupplierCrossOrder supplierCrossOrder) {
        try {
            AlibabaTradeGetLogisticsTraceInfoBuyerViewParam param = new AlibabaTradeGetLogisticsTraceInfoBuyerViewParam();
            param.setOrderId(Long.valueOf(supplierCrossOrder.getOrderIdStr()));
            param.setWebSite("1688");
            AlibabaLogisticsOpenPlatformLogisticsTrace[] logisticsOpenPlatformLogisticsTraces = alibabaLogisticsService.getLogisticsTraceInfoBuyerView(param);

            if (logisticsOpenPlatformLogisticsTraces == null) {
                return null;
            }

            List<SupplierCrossOrderLogisticsTrace> supplierCrossOrderLogisticsTraces = Arrays.stream(logisticsOpenPlatformLogisticsTraces).map(logisticsTrace -> {
                SupplierCrossOrderLogisticsTrace supplierCrossOrderLogisticsTrace = supplierCrossOrderLogisticsTraceService.getByLogisticsId(logisticsTrace.getLogisticsId()).stream()
                        .filter(trace -> trace.getLogisticsBillNo().equals(logisticsTrace.getLogisticsBillNo())).findFirst().orElse(null);

                if (supplierCrossOrderLogisticsTrace == null) {
                    supplierCrossOrderLogisticsTrace = convertToSupplierCrossOrderLogisticsTrace(logisticsTrace);
                    supplierCrossOrderLogisticsTrace.setSupplierCrossOrder(supplierCrossOrder);
                } else {
                    supplierCrossOrderLogisticsTrace.setLogisticsSteps(convertToSupplierCrossOrderLogisticsTraceSteps(logisticsTrace.getLogisticsSteps(), supplierCrossOrderLogisticsTrace));
                }


                return supplierCrossOrderLogisticsTraceService.saveAndUpdate(supplierCrossOrderLogisticsTrace);
            }).collect(Collectors.toList());


            return supplierCrossOrderLogisticsTraces;
        } catch (Exception e) {
            LOGGER.error("get update cross order [" + supplierCrossOrder.getOrderIdStr() + "] SupplierCrossOrderLogisticsTrace exception", e);
        }

        return null;
    }

    private void checkAndUpdatePurchaseSupplierOrderReceivedStatus(PurchaseSupplierOrder purchaseSupplierOrder) {
        int receivedCrossOrdersCount = purchaseSupplierOrder.getCrossOrders().stream()
                .filter(supplierCrossOrder -> supplierCrossOrder.isStatusGte(CrossOrderConstants.STATUS_CONFIRM_GOODS)).collect(Collectors.toList()).size();

        // 如果当前状态小于 Received，设置为 Received
        if (receivedCrossOrdersCount == purchaseSupplierOrder.getCrossOrders().size() && purchaseSupplierOrder.isStatusLt(PurchaseSupplierOrderStatus.RECEIVED)) {
            purchaseSupplierOrder.setStatus(PurchaseSupplierOrderStatus.RECEIVED);
            purchaseSupplierOrderService.saveAndUpdate(purchaseSupplierOrder);
        }

        checkAndUpdatePurchaseOrderReceivedStatus(purchaseSupplierOrder.getPurchaseOrder());
    }

    private void checkAndUpdatePurchaseOrderReceivedStatus(PurchaseOrder purchaseOrder) {
        int receivedSupplierOrdersCount = purchaseOrder.getPurchaseSupplierOrders().stream()
                .filter(purchaseSupplierOrder -> purchaseSupplierOrder.isStatusGte(PurchaseSupplierOrderStatus.RECEIVED)).collect(Collectors.toList()).size();

        if (receivedSupplierOrdersCount == purchaseOrder.getPurchaseSupplierOrders().size() && purchaseOrder.isStatusLt(PurchaseOrderStatus.RECEIVED)) {
            purchaseOrder.setStatus(PurchaseOrderStatus.RECEIVED);
        }

        purchaseOrderService.saveAndUpdate(purchaseOrder);
    }

    private void checkAndUpdatePurchaseSupplierOrderShippedStatus(PurchaseSupplierOrder purchaseSupplierOrder) {
        int shippedCrossOrdersCount = purchaseSupplierOrder.getCrossOrders().stream()
                .filter(supplierCrossOrder -> supplierCrossOrder.isStatusGte(CrossOrderConstants.STATUS_WAITSELLERSEND)).collect(Collectors.toList()).size();

        // 如果当前状态小于 Shipped，设置为 Shipped
        if (shippedCrossOrdersCount > 0 && purchaseSupplierOrder.isStatusLt(PurchaseSupplierOrderStatus.SHIPPED)) {
            purchaseSupplierOrder.setStatus(PurchaseSupplierOrderStatus.SHIPPED);
            purchaseSupplierOrderService.saveAndUpdate(purchaseSupplierOrder);
        }

        checkAndUpdatePurchaseOrderShippedStatus(purchaseSupplierOrder.getPurchaseOrder());
    }

    private void checkAndUpdatePurchaseOrderShippedStatus(PurchaseOrder purchaseOrder) {
        int shippedSupplierOrdersCount = purchaseOrder.getPurchaseSupplierOrders().stream()
                .filter(purchaseSupplierOrder -> purchaseSupplierOrder.isStatusGte(PurchaseSupplierOrderStatus.SHIPPED)).collect(Collectors.toList()).size();

        if (shippedSupplierOrdersCount > 0 && shippedSupplierOrdersCount < purchaseOrder.getPurchaseSupplierOrders().size()  && purchaseOrder.isStatusLt(PurchaseOrderStatus.PARTIAL_SHIPPED)) {
            purchaseOrder.setStatus(PurchaseOrderStatus.PARTIAL_SHIPPED);
        }

        if (shippedSupplierOrdersCount == purchaseOrder.getPurchaseSupplierOrders().size() && purchaseOrder.isStatusLt(PurchaseOrderStatus.SHIPPED)) {
            purchaseOrder.setStatus(PurchaseOrderStatus.SHIPPED);
        }

        purchaseOrderService.saveAndUpdate(purchaseOrder);
    }

    private void checkAndUpdatePurchaseSupplierOrderPaidStatus(PurchaseSupplierOrder purchaseSupplierOrder) {
        int paidCrossOrdersCount = purchaseSupplierOrder.getCrossOrders().stream()
                .filter(supplierCrossOrder -> supplierCrossOrder.isStatusGt(CrossOrderConstants.STATUS_WAITBUYERPAY)).collect(Collectors.toList()).size();

        // 供应商采购单状态变更
        if (purchaseSupplierOrder.getStatus() == PurchaseSupplierOrderStatus.NEW ||
                purchaseSupplierOrder.getStatus() == PurchaseSupplierOrderStatus.PARTIAL_PAID
        ) {
            // 部分付款
            if (paidCrossOrdersCount > 0 && paidCrossOrdersCount < purchaseSupplierOrder.getCrossOrders().size()) {
                purchaseSupplierOrder.setStatus(PurchaseSupplierOrderStatus.PARTIAL_PAID);
                purchaseSupplierOrder.setPayStatus(PurchaseSupplierOrderPayStatus.PARTIAL_PAID);
            }

            // 全部付款
            if (paidCrossOrdersCount == purchaseSupplierOrder.getCrossOrders().size()) {
                purchaseSupplierOrder.setStatus(PurchaseSupplierOrderStatus.PAID);
                purchaseSupplierOrder.setPayStatus(PurchaseSupplierOrderPayStatus.PAID);
            }
        }

        purchaseSupplierOrderService.saveAndUpdate(purchaseSupplierOrder);

        // 更新采购单状态
        checkAndUpdatePurchaseOrderPaidStatus(purchaseSupplierOrder.getPurchaseOrder());
    }

    private void checkAndUpdatePurchaseOrderPaidStatus(PurchaseOrder purchaseOrder) {
        int paidPurchaseSupplierOrdersCount = purchaseOrder.getPurchaseSupplierOrders().stream()
                .filter(purchaseSupplierOrder -> purchaseSupplierOrder.isStatusGt(PurchaseSupplierOrderStatus.PARTIAL_PAID)).collect(Collectors.toList()).size();

        // 采购单状态变更
        if (purchaseOrder.getStatus() == PurchaseOrderStatus.NEW ||
                purchaseOrder.getStatus() == PurchaseOrderStatus.PARTIAL_PAID
        ) {
            if (paidPurchaseSupplierOrdersCount > 0 && paidPurchaseSupplierOrdersCount < purchaseOrder.getPurchaseSupplierOrders().size()) {
                purchaseOrder.setStatus(PurchaseOrderStatus.PARTIAL_PAID);
            }

            if (paidPurchaseSupplierOrdersCount == purchaseOrder.getPurchaseSupplierOrders().size()) {
                purchaseOrder.setStatus(PurchaseOrderStatus.PAID);
            }
        }

        purchaseOrderService.saveAndUpdate(purchaseOrder);
    }

    private PurchaseSupplierOrderProductStatus getPurchaseSupplierOrderProductStatus(String statusChanged) {
        if (StringUtils.equalsIgnoreCase("SIGN", statusChanged)) {
            return PurchaseSupplierOrderProductStatus.RECEIVED;
        }
        if (StringUtils.equalsIgnoreCase("CONSIGN", statusChanged)
            || StringUtils.equalsIgnoreCase("ACCEPT", statusChanged)
            || StringUtils.equalsIgnoreCase("TRANSPORT", statusChanged)
            || StringUtils.equalsIgnoreCase("DELIVERING", statusChanged)
        ) {
            return PurchaseSupplierOrderProductStatus.SHIPPED;
        }

        return PurchaseSupplierOrderProductStatus.PENDING;
    }

    private SupplierCrossOrderLogistics convertToSupplierCrossOrderLogistics(AlibabaLogisticsOpenPlatformLogisticsOrder source) {
        if (source == null) {
            return null;
        }

        SupplierCrossOrderLogistics target = new SupplierCrossOrderLogistics();

        target.setLogisticsId(source.getLogisticsId());
        target.setLogisticsBillNo(source.getLogisticsBillNo());
        target.setOrderEntryIds(source.getOrderEntryIds());
        target.setStatus(source.getStatus());
        target.setLogisticsCompanyId(source.getLogisticsCompanyId());
        target.setLogisticsCompanyName(source.getLogisticsCompanyName());
        target.setRemarks(source.getRemarks());
        target.setServiceFeature(source.getServiceFeature());
        target.setGmtSystemSend(source.getGmtSystemSend());

        // Convert Receiver
        if (source.getReceiver() != null) {
            Receiver targetReceiver = new Receiver();
            targetReceiver.setReceiverName(source.getReceiver().getReceiverName());
            targetReceiver.setReceiverPhone(source.getReceiver().getReceiverPhone());
            targetReceiver.setReceiverMobile(source.getReceiver().getReceiverMobile());
            targetReceiver.setEncrypt(source.getReceiver().getEncrypt());
            targetReceiver.setReceiverProvinceCode(source.getReceiver().getReceiverProvinceCode());
            targetReceiver.setReceiverCityCode(source.getReceiver().getReceiverCityCode());
            targetReceiver.setReceiverCountyCode(source.getReceiver().getReceiverCountyCode());
            targetReceiver.setReceiverAddress(source.getReceiver().getReceiverAddress());
            targetReceiver.setReceiverProvince(source.getReceiver().getReceiverProvince());
            targetReceiver.setReceiverCity(source.getReceiver().getReceiverCity());
            targetReceiver.setReceiverCounty(source.getReceiver().getReceiverCounty());
            target.setReceiver(targetReceiver);
        }

        // Convert Sender
        if (source.getSender() != null) {
            Sender targetSender = new Sender();
            targetSender.setSenderName(source.getSender().getSenderName());
            targetSender.setSenderPhone(source.getSender().getSenderPhone());
            targetSender.setSenderMobile(source.getSender().getSenderMobile());
            targetSender.setEncrypt(source.getSender().getEncrypt());
            targetSender.setSenderProvinceCode(source.getSender().getSenderProvinceCode());
            targetSender.setSenderCityCode(source.getSender().getSenderCityCode());
            targetSender.setSenderCountyCode(source.getSender().getSenderCountyCode());
            targetSender.setSenderAddress(source.getSender().getSenderAddress());
            targetSender.setSenderProvince(source.getSender().getSenderProvince());
            targetSender.setSenderCity(source.getSender().getSenderCity());
            targetSender.setSenderCounty(source.getSender().getSenderCounty());
            target.setSender(targetSender);
        }

        // Convert LogisticsOrderGoods
        if (source.getLogisticsOrderGoods() != null) {
            Set<SupplierCrossOrderLogisticsOrderGoods> logisticsOrderGoodsList = new HashSet<>();
            for (ComAlibabaOceanOpenplatformBizLogisticsCommonModelOpenPlatformLogisticsOrderSendGood logisticsOrderSendGood : source.getLogisticsOrderGoods()) {
                SupplierCrossOrderLogisticsOrderGoods targetLogisticsOrderSendGood = new SupplierCrossOrderLogisticsOrderGoods();
                // Assuming LogisticsOrderSendGood has similar structure and fields
                targetLogisticsOrderSendGood.setLogisticsOrderId(String.valueOf(logisticsOrderSendGood.getLogisticsOrderId()));
                targetLogisticsOrderSendGood.setLogisticsId(logisticsOrderSendGood.getLogisticsId());
                targetLogisticsOrderSendGood.setTradeOrderId(logisticsOrderSendGood.getTradeOrderId());
                targetLogisticsOrderSendGood.setTradeOrderItemId(logisticsOrderSendGood.getTradeOrderItemId());
                targetLogisticsOrderSendGood.setDescription(logisticsOrderSendGood.getDescription());
                targetLogisticsOrderSendGood.setQuantity(logisticsOrderSendGood.getQuantity());
                targetLogisticsOrderSendGood.setUnit(logisticsOrderSendGood.getUnit());
                targetLogisticsOrderSendGood.setProductName(logisticsOrderSendGood.getProductName());
                targetLogisticsOrderSendGood.setSupplierCrossOrderLogistics(target);
                logisticsOrderGoodsList.add(targetLogisticsOrderSendGood);
            }
            target.setLogisticsOrderGoods(logisticsOrderGoodsList);
        }

        return target;
    }


    private SupplierCrossOrderLogisticsTrace convertToSupplierCrossOrderLogisticsTrace(AlibabaLogisticsOpenPlatformLogisticsTrace source) {
        if (source == null) {
            return null;
        }

        SupplierCrossOrderLogisticsTrace target = new SupplierCrossOrderLogisticsTrace();
        target.setLogisticsId(source.getLogisticsId());
        target.setLogisticsBillNo(source.getLogisticsBillNo());
        target.setOrderId(source.getOrderId());

        if (source.getLogisticsSteps() != null) {
            Set<SupplierCrossOrderLogisticsTraceStep> steps = new HashSet<>();
            for (AlibabaLogisticsOpenPlatformLogisticsStep sourceStep : source.getLogisticsSteps()) {
                SupplierCrossOrderLogisticsTraceStep targetStep = new SupplierCrossOrderLogisticsTraceStep();

                // Assuming the acceptTime is in a valid date-time format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime acceptTime = LocalDateTime.parse(sourceStep.getAcceptTime(), formatter);
                targetStep.setAcceptTime(acceptTime);

                targetStep.setRemark(sourceStep.getRemark());
                targetStep.setLogisticsTrace(target);

                steps.add(targetStep);
            }
            target.setLogisticsSteps(steps);
        }

        return target;
    }

    private Set<SupplierCrossOrderLogisticsTraceStep> convertToSupplierCrossOrderLogisticsTraceSteps(AlibabaLogisticsOpenPlatformLogisticsStep[] sourceSteps, SupplierCrossOrderLogisticsTrace target) {
        Set<SupplierCrossOrderLogisticsTraceStep> steps = new HashSet<>();
        if (sourceSteps != null) {
            for (AlibabaLogisticsOpenPlatformLogisticsStep sourceStep : sourceSteps) {
                SupplierCrossOrderLogisticsTraceStep targetStep = new SupplierCrossOrderLogisticsTraceStep();

                // Assuming the acceptTime is in a valid date-time format
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime acceptTime = LocalDateTime.parse(sourceStep.getAcceptTime(), formatter);
                targetStep.setAcceptTime(acceptTime);

                targetStep.setRemark(sourceStep.getRemark());
                targetStep.setLogisticsTrace(target);

                steps.add(targetStep);
            }
            target.setLogisticsSteps(steps);
        }

        return steps;
    }
}
