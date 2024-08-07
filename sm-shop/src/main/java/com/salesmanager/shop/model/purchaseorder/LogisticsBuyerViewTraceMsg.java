package com.salesmanager.shop.model.purchaseorder;

import java.util.List;

public class LogisticsBuyerViewTraceMsg {

    private String logisticsId;

    private String cpCode;

    private String mailNo;

    private String statusChanged;

    private List<OrderLogsItem> orderLogsItems;

    public static class OrderLogsItem {
        private Long orderId;

        private Long orderEntryId;

        public Long getOrderId() {
            return orderId;
        }

        public void setOrderId(Long orderId) {
            this.orderId = orderId;
        }

        public Long getOrderEntryId() {
            return orderEntryId;
        }

        public void setOrderEntryId(Long orderEntryId) {
            this.orderEntryId = orderEntryId;
        }
    }

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getCpCode() {
        return cpCode;
    }

    public void setCpCode(String cpCode) {
        this.cpCode = cpCode;
    }

    public String getMailNo() {
        return mailNo;
    }

    public void setMailNo(String mailNo) {
        this.mailNo = mailNo;
    }

    public String getStatusChanged() {
        return statusChanged;
    }

    public void setStatusChanged(String statusChanged) {
        this.statusChanged = statusChanged;
    }

    public List<OrderLogsItem> getOrderLogsItems() {
        return orderLogsItems;
    }

    public void setOrderLogsItems(List<OrderLogsItem> orderLogsItems) {
        this.orderLogsItems = orderLogsItems;
    }
}
