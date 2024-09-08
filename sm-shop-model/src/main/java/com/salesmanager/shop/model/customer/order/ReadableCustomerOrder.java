package com.salesmanager.shop.model.customer.order;

import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.order.total.ReadableTotal;
import com.salesmanager.shop.model.order.v0.ReadableOrder;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class ReadableCustomerOrder extends Entity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private ReadableTotal total;
    private PaymentType paymentType;

    /**
     * @see com.salesmanager.core.model.payments.ImportMainEnums
     */
    private String importMain;


    private String customsClearanceNumber;

    /**
     * @see orderType
     */
    private String orderType;


    private String paymentModule;


    private String shippingModule;
    private Date datePurchased;
    private String currency;
    private boolean customerAgreed;
    private boolean confirmedAddress;
    private ReadableCustomer customer;
    private ReadableBilling billing;
    private ReadableDelivery delivery;

    private List<ReadableOrder> orders;

    public ReadableTotal getTotal() {
        return total;
    }

    public void setTotal(ReadableTotal total) {
        this.total = total;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentModule() {
        return paymentModule;
    }

    public void setPaymentModule(String paymentModule) {
        this.paymentModule = paymentModule;
    }

    public String getShippingModule() {
        return shippingModule;
    }

    public void setShippingModule(String shippingModule) {
        this.shippingModule = shippingModule;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isCustomerAgreed() {
        return customerAgreed;
    }

    public void setCustomerAgreed(boolean customerAgreed) {
        this.customerAgreed = customerAgreed;
    }

    public boolean isConfirmedAddress() {
        return confirmedAddress;
    }

    public void setConfirmedAddress(boolean confirmedAddress) {
        this.confirmedAddress = confirmedAddress;
    }

    public ReadableCustomer getCustomer() {
        return customer;
    }

    public void setCustomer(ReadableCustomer customer) {
        this.customer = customer;
    }

    public ReadableBilling getBilling() {
        return billing;
    }

    public void setBilling(ReadableBilling billing) {
        this.billing = billing;
    }

    public ReadableDelivery getDelivery() {
        return delivery;
    }

    public void setDelivery(ReadableDelivery delivery) {
        this.delivery = delivery;
    }

    public List<ReadableOrder> getOrders() {
        return orders;
    }

    public void setOrders(List<ReadableOrder> orders) {
        this.orders = orders;
    }

    public String getCustomsClearanceNumber() {
        return customsClearanceNumber;
    }

    public void setCustomsClearanceNumber(String customsClearanceNumber) {
        this.customsClearanceNumber = customsClearanceNumber;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public String getImportMain() {
        return importMain;
    }

    public void setImportMain(String importMain) {
        this.importMain = importMain;
    }
}
