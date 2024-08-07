package com.salesmanager.core.model.crossorder.logistics;

import com.salesmanager.core.model.crossorder.SupplierCrossOrder;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "SUPPLIER_CROSS_ORDER_LOGISTICS")
public class SupplierCrossOrderLogistics extends SalesManagerEntity<Long, SupplierCrossOrderLogistics> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "LOGISTICS_ID", unique = true)
    private String logisticsId;

    @Column(name = "LOGISTICS_BILL_NO", unique = true)
    private String logisticsBillNo;

    @Column(name = "ORDER_ENTRY_IDS")
    private String orderEntryIds;

    /**
     *  WAITACCEPT:未受理;
     *  CANCEL:已撤销;
     *  ACCEPT:已受理;
     *  TRANSPORT:运输中;
     *  NOGET:揽件失败;
     *  SIGN:已签收;
     *  UNSIGN:签收异常
     */
    @Column(name = "STATUS")
    private String status;

    @Column(name = "LOGISTICS_COMPANY_ID")
    private String logisticsCompanyId;

    @Column(name = "LOGISTICS_COMPANY_NAME")
    private String logisticsCompanyName;

    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "SERVICE_FEATURE")
    private String serviceFeature;

    @Column(name = "GMT_SYSTEM_SEND")
    private String gmtSystemSend;

    @Embedded
    private Receiver receiver;

    @Embedded
    private Sender sender;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "supplierCrossOrderLogistics", cascade = CascadeType.ALL)
    private List<SupplierCrossOrderLogisticsOrderGoods> logisticsOrderGoods;


    @ManyToMany(mappedBy = "logistics")
    private Set<SupplierCrossOrder> supplierCrossOrders = new HashSet<>();

    public Set<SupplierCrossOrder> getSupplierCrossOrders() {
        return supplierCrossOrders;
    }

    public void setSupplierCrossOrders(Set<SupplierCrossOrder> supplierCrossOrders) {
        this.supplierCrossOrders = supplierCrossOrders;
    }

    public void addSupplierCrossOrder(SupplierCrossOrder supplierCrossOrder) {
        supplierCrossOrders.add(supplierCrossOrder);
        supplierCrossOrder.getLogistics().add(this);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public List<SupplierCrossOrderLogisticsOrderGoods> getLogisticsOrderGoods() {
        return logisticsOrderGoods;
    }

    public void setLogisticsOrderGoods(List<SupplierCrossOrderLogisticsOrderGoods> logisticsOrderGoods) {
        this.logisticsOrderGoods = logisticsOrderGoods;
    }

    public boolean hasSupplierCrossOrder(SupplierCrossOrder supplierCrossOrder) {
       return supplierCrossOrders.stream().filter(so -> so.getOrderIdStr().equals(supplierCrossOrder.getOrderIdStr())).findFirst().isPresent();
    }
}
