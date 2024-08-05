package com.salesmanager.core.model.purchaseorder;

public enum PurchaseOrderStatus {
    NEW, // 已创建
    PENDING_APPROVAL, // 待审批
    APPROVED, // 已审批
    PENDING_PAY, // 待支付
    PARTIAL_PAID, // 部分支付
    PAID, // 已支付
    PROCESSING, // 处理中
    RECEIVED, // 已收货
    COMPLETED, // 已完成
    CANCELLED, // 已取消
    RETURNED; // 已退货
}
