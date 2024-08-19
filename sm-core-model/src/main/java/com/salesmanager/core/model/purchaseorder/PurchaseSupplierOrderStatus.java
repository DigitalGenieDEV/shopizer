package com.salesmanager.core.model.purchaseorder;

public enum PurchaseSupplierOrderStatus {
    NEW, // 已创建
    PARTIAL_PAID, // 部分付款
    PAID, // 全部付款
    SHIPPED, // 发货中
    RECEIVED, // 已收货
    COMPLETED, // 交易完成
    CANCELED, // 交易取消
    RETURNED; // 交易退款
}
