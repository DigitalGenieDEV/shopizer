package com.salesmanager.shop.model.customer.order.transaction;

import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.payments.TransactionType;
import com.salesmanager.shop.model.entity.Entity;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
public class ReadableCombineTransaction extends Entity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private Long customerOrderId;
    private String details;
    private String transactionDate;
    private String amount;
    private PaymentType paymentType;
    private TransactionType transactionType;
    private String payOrderNo;
    private List<Long> relationOrderIdList;

}
