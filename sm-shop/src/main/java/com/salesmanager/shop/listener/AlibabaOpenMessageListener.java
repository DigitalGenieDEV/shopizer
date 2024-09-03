package com.salesmanager.shop.listener;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.shop.listener.alibaba.tuna.client.api.MessageProcessException;
import com.salesmanager.shop.listener.alibaba.tuna.client.httpcb.HttpCbMessage;
import com.salesmanager.shop.listener.alibaba.tuna.client.httpcb.HttpCbMessageHandler;
import com.salesmanager.shop.listener.alibaba.tuna.client.httpcb.TunaHttpCbClient;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsMsg;
import com.salesmanager.shop.model.purchaseorder.*;
import com.salesmanager.shop.store.controller.crossorder.facade.SupplierCrossOrderFacade;
import com.salesmanager.shop.store.controller.purchaseorder.facade.PurchaseOrderAlibabaMsgerFacade;
import com.salesmanager.shop.store.controller.purchaseorder.facade.PurchaseOrderFacade;
import com.salesmanager.shop.store.facade.product.AlibabaProductFacadeImpl;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.salesmanager.core.constants.ApiFor1688Constants.SECRET_KEY;

@Component
public class AlibabaOpenMessageListener implements ApplicationListener<ApplicationReadyEvent> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlibabaOpenMessageListener.class);


    TunaHttpCbClient client = new TunaHttpCbClient(8018, false);

    @Autowired
    private PurchaseOrderFacade purchaseOrderFacade;

    @Autowired
    private SupplierCrossOrderFacade supplierCrossOrderFacade;

    @Autowired
    private PurchaseOrderAlibabaMsgerFacade purchaseOrderAlibabaMsgerFacade;

    private final static String ORDER_BUYER_VIEW_ORDER_PAY = "ORDER_BUYER_VIEW_ORDER_PAY";

    private final static String ORDER_BUYER_VIEW_ANNOUNCE_SENDGOODS = "ORDER_BUYER_VIEW_ANNOUNCE_SENDGOODS";

    private final static String ORDER_BUYER_VIEW_PART_PART_SENDGOODS = "ORDER_BUYER_VIEW_PART_PART_SENDGOODS";

    private final static String ORDER_BUYER_VIEW_ORDER_COMFIRM_RECEIVEGOODS = "ORDER_BUYER_VIEW_ORDER_COMFIRM_RECEIVEGOODS";

    private final static String ORDER_BUYER_VIEW_ORDER_SUCCESS = "ORDER_BUYER_VIEW_ORDER_SUCCESS";

    private final static String ORDER_BUYER_VIEW_ORDER_BUYER_CLOSE = "ORDER_BUYER_VIEW_ORDER_BUYER_CLOSE";

    private final static String ORDER_BATCH_PAY = "ORDER_BATCH_PAY";

    private final static String LOGISTICS_BUYER_VIEW_TRACE = "LOGISTICS_BUYER_VIEW_TRACE";

//    private Long orderId = Long.valueOf("3971108739576581101");

    public boolean doSth(HttpCbMessage message) {
        try {
            if (StringUtils.equalsIgnoreCase(message.getType(), ORDER_BUYER_VIEW_ORDER_PAY)) {
                orderBuyerViewOrderPay(message);
            }

            if (StringUtils.equalsIgnoreCase(message.getType(), ORDER_BATCH_PAY)) {
                orderBatchPay(message);
            }

            if (StringUtils.equalsIgnoreCase(message.getType(), ORDER_BUYER_VIEW_ANNOUNCE_SENDGOODS)) {
                orderBuyerViewAnnounceSendGoods(message);
            }

            if (StringUtils.equalsIgnoreCase(message.getType(), ORDER_BUYER_VIEW_PART_PART_SENDGOODS)) {
                orderBuyerViewPartPartSendGoods(message);
            }

            if (StringUtils.equalsIgnoreCase(message.getType(), ORDER_BUYER_VIEW_ORDER_COMFIRM_RECEIVEGOODS)) {
                orderBuyerViewOrderConfirmReceiveGoods(message);
            }

            if (StringUtils.equalsIgnoreCase(message.getType(), ORDER_BUYER_VIEW_ORDER_SUCCESS)) {
                orderBuyerViewOrderSuccess(message);
            }

            if (StringUtils.equalsIgnoreCase(message.getType(), ORDER_BUYER_VIEW_ORDER_BUYER_CLOSE)) {
                orderBuyerViewOrderBuyerClose(message);
            }

            if (StringUtils.equalsIgnoreCase(message.getType(), LOGISTICS_BUYER_VIEW_TRACE)) {
                logisticsBuyerViewTrace(message);
            }

        } catch (ServiceException e) {
            LOGGER.error("handle http cb message exception", e);
            return true;
        }


        return false;
    }

    public void orderBuyerViewOrderPay(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        OrderBuyerViewOrderPayMsg msg = new OrderBuyerViewOrderPayMsg();
        msg.setOrderId(Long.valueOf((String) data.get("orderId")));
        msg.setCurrentStatus((String)data.get("currentStatus"));
        msg.setBuyerMemberId((String)data.get("buyerMemberId"));
        msg.setSellerMemberId((String)data.get("sellerMemberId"));

        purchaseOrderAlibabaMsgerFacade.orderBuyerViewOrderPay(msg);
    }

    public void orderBuyerViewAnnounceSendGoods(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        OrderBuyerViewAnnounceSendGoodsMsg msg = new OrderBuyerViewAnnounceSendGoodsMsg();
        msg.setOrderId((Long) data.get("orderId"));
//        msg.setOrderId(orderId);
        msg.setCurrentStatus((String)data.get("currentStatus"));
        msg.setBuyerMemberId((String)data.get("buyerMemberId"));
        msg.setSellerMemberId((String)data.get("sellerMemberId"));

        purchaseOrderAlibabaMsgerFacade.orderBuyerViewAnnounceSendGoods(msg);
    }

    public void orderBuyerViewPartPartSendGoods(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        OrderBuyerViewPartPartSendGoodsMsg msg = new OrderBuyerViewPartPartSendGoodsMsg();
        msg.setOrderId((Long) data.get("orderId"));
        msg.setCurrentStatus((String)data.get("currentStatus"));
        msg.setBuyerMemberId((String)data.get("buyerMemberId"));
        msg.setSellerMemberId((String)data.get("sellerMemberId"));

        purchaseOrderAlibabaMsgerFacade.orderBuyerViewPartPartSendGoods(msg);
    }

    public void orderBuyerViewOrderConfirmReceiveGoods(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        OrderBuyerViewOrderConfirmReceiveGoodsMsg msg = new OrderBuyerViewOrderConfirmReceiveGoodsMsg();
        msg.setOrderId((Long) data.get("orderId"));
//        msg.setOrderId(orderId);
        msg.setCurrentStatus((String)data.get("currentStatus"));
        msg.setBuyerMemberId((String)data.get("buyerMemberId"));
        msg.setSellerMemberId((String)data.get("sellerMemberId"));

        purchaseOrderAlibabaMsgerFacade.orderBuyerViewOrderConfirmReceiveGoods(msg);
    }

    public void orderBuyerViewOrderSuccess(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        OrderBuyerViewOrderSuccessMsg msg = new OrderBuyerViewOrderSuccessMsg();
        msg.setOrderId((Long) data.get("orderId"));
//        msg.setOrderId(orderId);
        msg.setCurrentStatus((String)data.get("currentStatus"));
        msg.setBuyerMemberId((String)data.get("buyerMemberId"));
        msg.setSellerMemberId((String)data.get("sellerMemberId"));

        purchaseOrderAlibabaMsgerFacade.orderBuyerViewOrderSuccess(msg);
    }

    public void orderBuyerViewOrderBuyerClose(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        OrderBuyerViewOrderBuyerCloseMsg msg = new OrderBuyerViewOrderBuyerCloseMsg();
        msg.setOrderId((Long) data.get("orderId"));
        msg.setCurrentStatus((String)data.get("currentStatus"));
        msg.setBuyerMemberId((String)data.get("buyerMemberId"));
        msg.setSellerMemberId((String)data.get("sellerMemberId"));

        purchaseOrderAlibabaMsgerFacade.orderBuyerViewOrderBuyerClose(msg);
    }

    public void orderBatchPay(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        OrderBatchPayMsg msg = new OrderBatchPayMsg();
        List<Map<String, Object>> batchPay = (List<Map<String, Object>>) data.get("batchPay");

        List<OrderBatchPayMsg.OrderPayResult> orderPayResults = new ArrayList<>();

        for (Map<String, Object> itemMap : batchPay) {
            OrderBatchPayMsg.OrderPayResult orderPayResult = new OrderBatchPayMsg.OrderPayResult();
            orderPayResult.setOrderId((String)itemMap.get("orderId"));
            orderPayResult.setStatus((String)itemMap.get("status"));

            orderPayResults.add(orderPayResult);
        }

        msg.setBatchPay(orderPayResults);

        purchaseOrderAlibabaMsgerFacade.orderBatchPay(msg);
    }

    public void logisticsBuyerViewTrace(HttpCbMessage message) throws ServiceException {
        Map<String, Object> data = message.getData();
        LogisticsBuyerViewTraceMsg msg = new LogisticsBuyerViewTraceMsg();
        Map<String, Object> modelMap = (Map<String, Object>)data.get("OrderLogisticsTracingModel");

        msg.setLogisticsId((String)modelMap.get("logisticsId"));
        msg.setCpCode((String)modelMap.get("cpCode"));
        msg.setMailNo((String)modelMap.get("mailNo"));
        msg.setStatusChanged((String)modelMap.get("statusChanged"));

        List<Map<String, Object>> orderLogsItemsMapList = (List<Map<String, Object>>) modelMap.get("orderLogsItems");
        List<LogisticsBuyerViewTraceMsg.OrderLogsItem> orderLogsItems = new ArrayList<>();

        for (Map<String, Object> itemMap : orderLogsItemsMapList) {
            LogisticsBuyerViewTraceMsg.OrderLogsItem orderLogsItem = new LogisticsBuyerViewTraceMsg.OrderLogsItem();
            orderLogsItem.setOrderId(((Number) itemMap.get("orderId")).longValue());
            orderLogsItem.setOrderEntryId(((Number) itemMap.get("orderEntryId")).longValue());
//            orderLogsItem.setOrderId(orderId);
//            orderLogsItem.setOrderEntryId(orderId);
            orderLogsItems.add(orderLogsItem);
        }

        msg.setOrderLogsItems(orderLogsItems);

        purchaseOrderAlibabaMsgerFacade.logisticsBuyerViewTrace(msg);
    }


    public void init() {
        // 2. 创建 消息处理 Handler
        HttpCbMessageHandler messageHandler = new HttpCbMessageHandler<HttpCbMessage, Void>() {

            /**
             * 应用密钥
             */
            public String getSignKey() {
                return SECRET_KEY;
            }

            /**
             * 为了防止消息篡改，开放平台推送的数据包含签名信息。
             * 字段名为 _aop_signature，假设值为 serverSign。
             *
             * 接收到消息后，SDK 首先会使用秘钥对接收到的内容进行签名，
             * 假设值为 clientSign。
             *
             * 1. 若 serverSign 与 clientSign 相同，则直接调用 {@link #onMessage(Object)} 方法。
             * 2. 若 serverSign 与 clientSign 不同，则调用该方法。若该方法返回 true，则继续
             * 	调用 {@link #onMessage(Object)} 方法；否则直接返回状态码 401。
             */
            public boolean continueOnSignatureValidationFailed(String clientSign, String serverSign) {
                return true;
            }

            /**
             * 消费消息。
             * @throws MessageProcessException 消息消费不成功，如未达重试次数上限，开放平台将会择机重发消息
             */
            public Void onMessage(HttpCbMessage message) throws MessageProcessException {
                LOGGER.info("receive 1688 open message: " + message);
                // 真正的业务逻辑在这。如果消费失败或异常，请抛出 MessageProcessException 异常
                try {
                    boolean ret = doSth(message);
                    if(ret) {
                        throw new MessageProcessException("process alibaba open message error");
                    }
                } catch(Exception e) {
                    LOGGER.error("process alibaba open message error", e);
                    throw new MessageProcessException("process alibaba open message error");
                }
                return null;
            }
        };
        // 注册 Handler
        client.registerMessageHandler("/pushMessage", messageHandler);

        // 3. 启动 Client
        client.start();
    }

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        init();
    }
}
