package com.salesmanager.shop.model.customer.order.transaction;

import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.order.v1.ReadableOrderAdditionalPayment;

import java.io.Serializable;
import java.util.List;

public class ReadableCombineTransactionList extends Entity implements Serializable {

    private List<ReadableCombineTransaction> combineTransactionList;


    private List<ReadableOrderAdditionalPayment> readableOrderAdditionalPaymentList;


    public List<ReadableCombineTransaction> getCombineTransactionList() {
        return combineTransactionList;
    }

    public void setCombineTransactionList(List<ReadableCombineTransaction> combineTransactionList) {
        this.combineTransactionList = combineTransactionList;
    }


    public List<ReadableOrderAdditionalPayment> getReadableOrderAdditionalPaymentList() {
        return readableOrderAdditionalPaymentList;
    }

    public void setReadableOrderAdditionalPaymentList(List<ReadableOrderAdditionalPayment> readableOrderAdditionalPaymentList) {
        this.readableOrderAdditionalPaymentList = readableOrderAdditionalPaymentList;
    }
}
