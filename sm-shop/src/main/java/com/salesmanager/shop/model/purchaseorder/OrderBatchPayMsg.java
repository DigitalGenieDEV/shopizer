package com.salesmanager.shop.model.purchaseorder;

import java.util.List;

public class OrderBatchPayMsg {

    private List<OrderPayResult> batchPay;

    public static class OrderPayResult {
        private String orderId;

        private String status;

        public String getOrderId() {
            return orderId;
        }

        public void setOrderId(String orderId) {
            this.orderId = orderId;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
    }

    public List<OrderPayResult> getBatchPay() {
        return batchPay;
    }

    public void setBatchPay(List<OrderPayResult> batchPay) {
        this.batchPay = batchPay;
    }
}
