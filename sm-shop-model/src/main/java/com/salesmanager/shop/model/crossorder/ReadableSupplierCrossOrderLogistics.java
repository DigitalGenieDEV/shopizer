package com.salesmanager.shop.model.crossorder;

import com.salesmanager.core.model.crossorder.logistics.Receiver;
import com.salesmanager.core.model.crossorder.logistics.Sender;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsOrderGoods;
import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

public class ReadableSupplierCrossOrderLogistics extends Entity implements Serializable {


    private String logisticsId;

    private String logisticsBillNo;

    private String orderEntryIds;

    private String status;

    private String logisticsCompanyId;

    private String logisticsCompanyName;

    private String remarks;

    private String serviceFeature;

    private String gmtSystemSend;

    private ReadableReceiver receiver;

    private ReadableSender sender;

    private List<ReadableSupplierCrossOrderLogisticsOrderGoods> logisticsOrderGoods;

    public String getLogisticsId() {
        return logisticsId;
    }

    public void setLogisticsId(String logisticsId) {
        this.logisticsId = logisticsId;
    }

    public String getLogisticsBillNo() {
        return logisticsBillNo;
    }

    public void setLogisticsBillNo(String logisticsBillNo) {
        this.logisticsBillNo = logisticsBillNo;
    }

    public String getOrderEntryIds() {
        return orderEntryIds;
    }

    public void setOrderEntryIds(String orderEntryIds) {
        this.orderEntryIds = orderEntryIds;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLogisticsCompanyId() {
        return logisticsCompanyId;
    }

    public void setLogisticsCompanyId(String logisticsCompanyId) {
        this.logisticsCompanyId = logisticsCompanyId;
    }

    public String getLogisticsCompanyName() {
        return logisticsCompanyName;
    }

    public void setLogisticsCompanyName(String logisticsCompanyName) {
        this.logisticsCompanyName = logisticsCompanyName;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getServiceFeature() {
        return serviceFeature;
    }

    public void setServiceFeature(String serviceFeature) {
        this.serviceFeature = serviceFeature;
    }

    public String getGmtSystemSend() {
        return gmtSystemSend;
    }

    public void setGmtSystemSend(String gmtSystemSend) {
        this.gmtSystemSend = gmtSystemSend;
    }

    public ReadableReceiver getReceiver() {
        return receiver;
    }

    public void setReceiver(ReadableReceiver receiver) {
        this.receiver = receiver;
    }

    public ReadableSender getSender() {
        return sender;
    }

    public void setSender(ReadableSender sender) {
        this.sender = sender;
    }

    public List<ReadableSupplierCrossOrderLogisticsOrderGoods> getLogisticsOrderGoods() {
        return logisticsOrderGoods;
    }

    public void setLogisticsOrderGoods(List<ReadableSupplierCrossOrderLogisticsOrderGoods> logisticsOrderGoods) {
        this.logisticsOrderGoods = logisticsOrderGoods;
    }
}
