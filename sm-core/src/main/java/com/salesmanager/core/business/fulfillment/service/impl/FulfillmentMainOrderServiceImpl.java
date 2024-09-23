package com.salesmanager.core.business.fulfillment.service.impl;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.fulfillment.service.*;
import com.salesmanager.core.business.repositories.fulfillment.FulfillmentMainOrderRepository;
import com.salesmanager.core.business.repositories.order.OrderRepository;
import com.salesmanager.core.business.repositories.order.orderproduct.OrderProductRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.enmus.*;
import com.salesmanager.core.model.fulfillment.*;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.utils.StringUtil;
import org.apache.commons.collections4.CollectionUtils;
import org.codehaus.plexus.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service("fulfillmentMainOrderService")
public class FulfillmentMainOrderServiceImpl extends SalesManagerEntityServiceImpl<Long, FulfillmentMainOrder>  implements FulfillmentMainOrderService {

    private FulfillmentMainOrderRepository fulfillmentMainOrderRepository;

    @Autowired
    private FulfillmentSubOrderService fulfillmentSubOrderService;

    @Autowired
    private FulfillmentHistoryService fulfillmentHistoryService;

    @Autowired
    private QcInfoService qcInfoService;

    @Autowired
    private AdditionalServicesService additionalServicesService;

    @Autowired
    private OrderProductRepository orderProductRepository;

    @Autowired
    private OrderRepository orderRepository;


    @Inject
    public FulfillmentMainOrderServiceImpl(FulfillmentMainOrderRepository fulfillmentMainOrderRepository) {
        super(fulfillmentMainOrderRepository);
        this.fulfillmentMainOrderRepository = fulfillmentMainOrderRepository;
    }


    @Override
    public Long saveFulfillmentMainOrder(FulfillmentMainOrder fulfillmentMainOrder){
        fulfillmentMainOrderRepository.save(fulfillmentMainOrder);
        return fulfillmentMainOrder.getId();
    }

    @Override
    @Transactional
    public void createFulfillmentOrderByOrder(Order order) {
        // 创建履约主订单
        FulfillmentMainOrder fulfillmentMainOrder = createFulfillmentMainOrder(order);

        // 获取增值服务映射
        Map<Long, AdditionalServices> additionalServicesMap = additionalServicesService.queryAdditionalServicesByMerchantIds(Long.valueOf(order.getMerchant().getId()));

        // 处理订单产品
        order.getOrderProducts().forEach(orderProduct -> {
            processOrderProduct(order, orderProduct, additionalServicesMap);

            // 创建履约子订单
            createFulfillmentSubOrder(order, orderProduct, fulfillmentMainOrder);
        });
    }

    // 创建履约主订单的方法
    private FulfillmentMainOrder createFulfillmentMainOrder(Order order) {
        FulfillmentMainOrder fulfillmentMainOrder = new FulfillmentMainOrder();
        fulfillmentMainOrder.setPartialDelivery(false);
        fulfillmentMainOrder.setDelivery(order.getDelivery());
        fulfillmentMainOrder.setBilling(order.getBilling());
        fulfillmentMainOrder.setOrder(order);

        // 设置 Order 的 FulfillmentMainOrder 属性
        order.setFulfillmentMainOrder(fulfillmentMainOrder);

        // 先保存 FulfillmentMainOrder
        fulfillmentMainOrderRepository.save(fulfillmentMainOrder);

        // 再保存 Order，确保两者关系更新
        orderRepository.save(order);

        return fulfillmentMainOrder;
    }

    // 处理订单产品的方法
    private void processOrderProduct(Order order, OrderProduct orderProduct, Map<Long, AdditionalServices> additionalServicesMap) {
        if (StringUtils.isNotEmpty(orderProduct.getAdditionalServicesMap())) {
            Map<String, String> additionalServiceMap = (Map<String, String>) JSON.parse(orderProduct.getAdditionalServicesMap());
            for (Map.Entry<String, String> entry : additionalServiceMap.entrySet()) {
                handleAdditionalService(order, orderProduct, additionalServicesMap, entry);
            }
        } else {
            createQcInfo(order, orderProduct, QcStatusEnums.APPROVE);
        }
    }

    // 处理增值服务的方法
    private void handleAdditionalService(Order order, OrderProduct orderProduct, Map<Long, AdditionalServices> additionalServicesMap, Map.Entry<String, String> entry) {
        Long serviceId = Long.parseLong(entry.getKey());
        AdditionalServices additionalServices = additionalServicesMap.get(serviceId);

        // 判断增值服务是否收费
        if (additionalServices != null &&
                (AdditionalServiceEnums.PACKAGING_SUPPLEMENT.name().equals(additionalServices.getCode()) ||
                        AdditionalServiceEnums.PRODUCT_PHOTOS.name().equals(additionalServices.getCode()))) {

            // 自动创建增值服务订单并通过
            createQcInfo(order, orderProduct, QcStatusEnums.APPROVE);
        }
    }

    // 创建QC信息的方法
    private void createQcInfo(Order order, OrderProduct orderProduct, QcStatusEnums status) {
        QcInfo qcInfo = new QcInfo();
        qcInfo.setOrderId(order.getId());
        qcInfo.setProductId(orderProduct.getProductId());
        qcInfo.setOrderProductId(orderProduct.getId());
        qcInfo.setStatus(status);
        Long id = qcInfoService.saveQcInfo(qcInfo);
        orderProductRepository.updateQcInfoIdByOrderProductId(id, orderProduct.getId());
    }

    // 创建履约子订单的方法
    private void createFulfillmentSubOrder(Order order, OrderProduct orderProduct, FulfillmentMainOrder fulfillmentMainOrder) {
        FulfillmentSubOrder fulfillmentSubOrder = new FulfillmentSubOrder();
        fulfillmentSubOrder.setFulfillmentMainType(FulfillmentTypeEnums.PAYMENT_COMPLETED);
        fulfillmentSubOrder.setTruckModel(orderProduct.getTruckModel());
        fulfillmentSubOrder.setTruckType(orderProduct.getTruckType());
        fulfillmentSubOrder.setNationalTransportationMethod(orderProduct.getNationalTransportationMethod());
        fulfillmentSubOrder.setShippingType(orderProduct.getShippingType());
        fulfillmentSubOrder.setShippingTransportationType(orderProduct.getShippingTransportationType());
        fulfillmentSubOrder.setInternationalTransportationMethod(orderProduct.getInternationalTransportationMethod());
        fulfillmentSubOrder.setFulfillmentMainOrder(fulfillmentMainOrder);
        fulfillmentSubOrder.setOrderId(order.getId());
        fulfillmentSubOrder.setOrderProductId(orderProduct.getId());
        fulfillmentSubOrderService.saveFulfillmentMainOrder(fulfillmentSubOrder);

        // 创建履约历史
        createFulfillmentHistory(order, orderProduct);
    }

    // 创建履约历史的方法
    private void createFulfillmentHistory(Order order, OrderProduct orderProduct) {
        FulfillmentHistory fulfillmentHistory = new FulfillmentHistory();
        fulfillmentHistory.setOrderId(order.getId());
        fulfillmentHistory.setOrderProductId(orderProduct.getId());
        if (order.getOrderType()!=null && OrderType.PRODUCT_1688 == order.getOrderType()){
            fulfillmentHistory.setStatus(FulfillmentHistoryTypeEnums.PENDING_REVIEW);
        }else {
            fulfillmentHistory.setStatus(FulfillmentHistoryTypeEnums.PAYMENT_COMPLETED);
        }
        fulfillmentHistoryService.saveFulfillmentHistory(fulfillmentHistory);
    }


    @Override
    public FulfillmentMainOrder queryFulfillmentMainOrderByOrderId(Long orderId) {
        return fulfillmentMainOrderRepository.queryFulfillmentMainOrderByOrderId(orderId);
    }
    @Override
    public void updatePartialDelivery(Long id, Boolean partialDelivery){
        fulfillmentMainOrderRepository.updatePartialDelivery(id, partialDelivery);
    }

}

