package com.salesmanager.core.model.purchaseorder;

public enum PurchaseOrderStatus {
    NEW, // 已创建
    PARTIAL_PAID, // 部分支付
    PAID, // 已支付
    PARTIAL_SHIPPED, //部分发货
    SHIPPED, // 已发货
    RECEIVED, // 已收货
    COMPLETED, // 已完成
    CANCELLED, // 已取消
    RETURNED; // 已退货
}
