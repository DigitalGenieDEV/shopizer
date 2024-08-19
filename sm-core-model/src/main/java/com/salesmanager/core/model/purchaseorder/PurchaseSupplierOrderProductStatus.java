package com.salesmanager.core.model.purchaseorder;

// 发货（CONSIGN）、揽收（ACCEPT）、运输（TRANSPORT）、派送（DELIVERING）、签收（SIGN）
public enum PurchaseSupplierOrderProductStatus {
    PENDING, // 待处理
    SHIPPED, // 已发货
    RECEIVED, // 已收货
    CANCELED, // 已取消
    RETURNED,// 已退货
    ERROR // 异常
    ;
}
