package com.salesmanager.shop.model.customer.order;

import com.salesmanager.shop.model.customer.address.Address;
import com.salesmanager.shop.model.customer.order.transaction.ReadableCombineTransaction;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.total.ReadableTotal;

import java.util.List;

public class ReadableCustomerOrderConfirmation extends Entity {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Address billing;
    private Address delivery;
    private String shipping;
    private String payment;
    private ReadableTotal total;
    private List<ReadableOrderProduct> products;
    private ReadableCombineTransaction transaction;

    public Address getBilling() {
        return billing;
    }

    public ReadableCombineTransaction getTransaction() {
        return transaction;
    }

    public void setTransaction(ReadableCombineTransaction transaction) {
        this.transaction = transaction;
    }

    public void setBilling(Address billing) {
        this.billing = billing;
    }

    public Address getDelivery() {
        return delivery;
    }

    public void setDelivery(Address delivery) {
        this.delivery = delivery;
    }

    public String getShipping() {
        return shipping;
    }

    public void setShipping(String shipping) {
        this.shipping = shipping;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public ReadableTotal getTotal() {
        return total;
    }

    public void setTotal(ReadableTotal total) {
        this.total = total;
    }

    public List<ReadableOrderProduct> getProducts() {
        return products;
    }

    public void setProducts(List<ReadableOrderProduct> products) {
        this.products = products;
    }
}
