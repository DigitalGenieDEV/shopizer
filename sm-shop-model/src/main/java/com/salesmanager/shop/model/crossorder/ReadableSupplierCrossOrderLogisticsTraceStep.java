package com.salesmanager.shop.model.crossorder;

import com.salesmanager.shop.model.entity.Entity;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.time.LocalDateTime;

public class ReadableSupplierCrossOrderLogisticsTraceStep extends Entity implements Serializable {

    private LocalDateTime acceptTime;

    private String remark;

    public LocalDateTime getAcceptTime() {
        return acceptTime;
    }

    public void setAcceptTime(LocalDateTime acceptTime) {
        this.acceptTime = acceptTime;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
