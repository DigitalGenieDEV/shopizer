package com.salesmanager.core.model.payments;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import org.hibernate.annotations.Type;
import org.json.simple.JSONAware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SM_COMBINE_TRANSACTION")
public class CombineTransaction extends SalesManagerEntity<Long, CombineTransaction> implements Serializable, Auditable, JSONAware {

    private static final Logger LOGGER = LoggerFactory.getLogger(CombineTransaction.class);
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "COMBINE_TRANSACTION_ID")
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "COMBINE_TRANSACT_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Embedded
    private AuditSection auditSection = new AuditSection();


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="CUSTOMER_ORDER_ID", nullable=true)
    private CustomerOrder customerOrder;

    @Column(name="AMOUNT")
    private BigDecimal amount;

    @Column(name="TRANSACTION_DATE")
    @Temporal(TemporalType.TIMESTAMP)
    private Date transactionDate;

    @Column(name="TRANSACTION_TYPE")
    @Enumerated(value = EnumType.STRING)
    private TransactionType transactionType;

    @Column(name="PAYMENT_TYPE")
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;

    @Column(name="DETAILS")
    @Type(type = "org.hibernate.type.TextType")
    private String details;

    @Column(name = "PAY_ORDER_NO")
    private String payOrderNo;

    @Column(name = "RELATION_ORDER_IDS", length = 4096)
    private String relationOrderIds;

    @Transient
    private Map<String,String> transactionDetails= new HashMap<String,String>();

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }

    public CustomerOrder getCustomerOrder() {
        return customerOrder;
    }

    public void setCustomerOrder(CustomerOrder customerOrder) {
        this.customerOrder = customerOrder;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public String getTransactionTypeName() {
        return this.getTransactionType()!=null?this.getTransactionType().name():"";
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Map<String, String> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(Map<String, String> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public String getPayOrderNo() {
        return payOrderNo;
    }

    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo;
    }

    public String getRelationOrderIds() {
        return relationOrderIds;
    }

    public void setRelationOrderIds(String relationOrderIds) {
        this.relationOrderIds = relationOrderIds;
    }

    public List<Long> getRelationOrderIdList() {
        if (relationOrderIds == null) {
            return null;
        }

        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.readValue(relationOrderIds, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot parse relationOrderIds",e);
        }
        return null;
    }

    public void setRelationOrderIdList(List<Long> relationOrderIdList) {
        if (relationOrderIdList == null) {
            this.relationOrderIds = null;
            return;
        }
        ObjectMapper mapper = new ObjectMapper();
        try {
            this.relationOrderIds = mapper.writeValueAsString(relationOrderIdList);
        } catch (JsonProcessingException e) {
            LOGGER.error("Cannot parse relationOrderIds",e);
        }
    }



    @Override
    public String toJSONString() {

        if(this.getTransactionDetails()!=null && this.getTransactionDetails().size()>0) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                return mapper.writeValueAsString(this.getTransactionDetails());
            } catch (Exception e) {
                LOGGER.error("Cannot parse transactions map",e);
            }

        }

        return null;
    }
}
