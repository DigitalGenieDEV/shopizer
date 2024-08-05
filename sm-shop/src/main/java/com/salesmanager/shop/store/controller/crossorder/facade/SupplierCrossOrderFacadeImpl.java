package com.salesmanager.shop.store.controller.crossorder.facade;

import com.salesmanager.core.business.alibaba.param.AlibabaLogisticsOpenPlatformLogisticsOrder;
import com.salesmanager.core.business.alibaba.param.AlibabaTradeGetLogisticsInfosBuyerViewParam;
import com.salesmanager.core.business.alibaba.param.ComAlibabaOceanOpenplatformBizLogisticsCommonModelOpenPlatformLogisticsOrderSendGood;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.alibaba.logistics.AlibabaLogisticsService;
import com.salesmanager.core.business.services.crossorder.SupplierCrossOrderLogisticsService;
import com.salesmanager.core.business.services.crossorder.SupplierCrossOrderService;
import com.salesmanager.core.business.services.purchaseorder.supplier.PurchaseSupplierOrderProductService;
import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.crossorder.logistics.Receiver;
import com.salesmanager.core.model.crossorder.logistics.Sender;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsOrderGoods;
import com.salesmanager.core.model.purchaseorder.*;
import com.salesmanager.core.utils.StringUtil;
import com.salesmanager.shop.mapper.crossorder.ReadableSupplierCrossOrderLogisticsMapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsMsg;
import com.salesmanager.shop.model.crossorder.SupplierCrossOrderLogisticsStatusChangeMsg;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SupplierCrossOrderFacadeImpl implements SupplierCrossOrderFacade{

    @Autowired
    private SupplierCrossOrderService supplierCrossOrderService;

    @Autowired
    private SupplierCrossOrderLogisticsService supplierCrossOrderLogisticsService;

    @Autowired
    private PurchaseSupplierOrderProductService purchaseSupplierOrderProductService;

    @Autowired
    private AlibabaLogisticsService alibabaLogisticsService;

    @Autowired
    private ReadableSupplierCrossOrderLogisticsMapper readableSupplierCrossOrderLogisticsMapper;

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
                logistics.setSupplierCrossOrder(supplierCrossOrder);
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

        supplierCrossOrderLogistics.getSupplierCrossOrder().getProducts().forEach(supplierCrossOrderProduct -> {
            // 发货（CONSIGN）、揽收（ACCEPT）、运输（TRANSPORT）、派送（DELIVERING）、签收（SIGN）
            PurchaseSupplierOrderProduct purchaseSupplierOrderProduct = supplierCrossOrderProduct.getPsoOrderProduct();
            purchaseSupplierOrderProduct.setStatus(getPurchaseSupplierOrderProductStatus(msg.getStatusChanged()));

            purchaseSupplierOrderProductService.saveAndUpdate(purchaseSupplierOrderProduct);
        });

        return readableSupplierCrossOrderLogisticsMapper.convert(supplierCrossOrderLogistics, null, null);
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

        // Convert SendGoods
//        if (source.getSendGoods() != null) {
//            List<SendGood> sendGoodsList = new ArrayList<>();
//            for (AlibabaLogisticsOpenPlatformLogisticsSendGood sendGood : source.getSendGoods()) {
//                SendGood targetSendGood = new SendGood();
//                // Assuming SendGood has similar structure and fields
//                targetSendGood.setSomeField(sendGood.getSomeField());
//                // ... set other fields accordingly
//                sendGoodsList.add(targetSendGood);
//            }
//            target.setSendGoods(sendGoodsList);
//        }

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
            List<SupplierCrossOrderLogisticsOrderGoods> logisticsOrderGoodsList = new ArrayList<>();
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
                logisticsOrderGoodsList.add(targetLogisticsOrderSendGood);
            }
            target.setLogisticsOrderGoods(logisticsOrderGoodsList);
        }

        return target;
    }
}
