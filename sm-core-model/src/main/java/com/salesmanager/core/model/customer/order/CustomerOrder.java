package com.salesmanager.core.model.customer.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderChannel;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.payment.CreditCard;
import com.salesmanager.core.model.payments.ImportMainEnums;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.reference.currency.Currency;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name="CUSTOMER_ORDERS")
public class CustomerOrder extends SalesManagerEntity<Long, CustomerOrder> {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name ="CUSTOMER_ORDER_ID" , unique=true , nullable=false )
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
            pkColumnValue = "CUSTOMER_ORDER_ID_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column (name ="LAST_MODIFIED")
    private Date lastModified;

    //the customer object can be detached. An order can exist and the customer deleted
    @Column (name ="CUSTOMER_ID")
    private Long customerId;

    @Temporal(TemporalType.DATE)
    @Column (name ="DATE_PURCHASED")
    private Date datePurchased;

    //used for an order payable on multiple installment
    @Temporal(TemporalType.TIMESTAMP)
    @Column (name ="ORDER_DATE_FINISHED")
    private Date orderDateFinished;

    //What was the exchange rate
    @Column (name ="CURRENCY_VALUE")
    private BigDecimal currencyValue = new BigDecimal(1);//default 1-1

    @Column (name ="ORDER_TOTAL")
    private BigDecimal total;

    @Column (name ="IP_ADDRESS")
    private String ipAddress;

    @Embedded
    private Delivery delivery = null;

    @Valid
    @Embedded
    private Billing billing = null;

    @Embedded
    @Deprecated
    private CreditCard creditCard = null;

    /**
     * 清关号
     */
    @Column (name ="CUSTOMS_CLEARANCE_NUMBER", length=100)
    private String customsClearanceNumber;

    @ManyToOne(targetEntity = Currency.class)
    @JoinColumn(name = "CURRENCY_ID")
    private Currency currency;

    @Type(type="locale")
    @Column (name ="LOCALE")
    private Locale locale;

    @Column (name ="ORDER_STATUS")
    @Enumerated(value = EnumType.STRING)
    private OrderStatus status;

    @JsonIgnore
    @ManyToOne(targetEntity = MerchantStore.class)
    @JoinColumn(name="MERCHANTID")
    private MerchantStore merchant;

    @JoinColumn(name="CUSTOMER_ORDER_ID")
    @OneToMany(targetEntity = Order.class)
    private List<Order> orders;

    @Column (name ="CUSTOMER_EMAIL_ADDRESS", length=50, nullable=false)
    private String customerEmailAddress;

    @Column (name ="CHANNEL")
    @Enumerated(value = EnumType.STRING)
    private OrderChannel channel;

    @Column (name ="ORDER_TYPE")
    @Enumerated(value = EnumType.STRING)
    private OrderType orderType = OrderType.PRODUCT;

    @Column (name ="PAYMENT_TYPE")
    @Enumerated(value = EnumType.STRING)
    private PaymentType paymentType;


    /**
     * 进口主体
     * IMPORT_AGENT
     * SELLER_RESPONSIBILITY
     */
    @Column (name ="IMPORT_MAIN")
    @Enumerated(value = EnumType.STRING)
    private ImportMainEnums importMain;

    @Column (name ="PAYMENT_MODULE_CODE")
    private String paymentModuleCode;

    @Column(name = "CUSTOMER_AGREED")
    private Boolean customerAgreement = false;

    @Column(name = "CONFIRMED_ADDRESS")
    private Boolean confirmedAddress = false;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerEmailAddress() {
        return customerEmailAddress;
    }

    public void setCustomerEmailAddress(String customerEmailAddress) {
        this.customerEmailAddress = customerEmailAddress;
    }

    public OrderChannel getChannel() {
        return channel;
    }

    public void setChannel(OrderChannel channel) {
        this.channel = channel;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public PaymentType getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(PaymentType paymentType) {
        this.paymentType = paymentType;
    }

    public String getPaymentModuleCode() {
        return paymentModuleCode;
    }

    public void setPaymentModuleCode(String paymentModuleCode) {
        this.paymentModuleCode = paymentModuleCode;
    }

    public Boolean getCustomerAgreement() {
        return customerAgreement;
    }

    public void setCustomerAgreement(Boolean customerAgreement) {
        this.customerAgreement = customerAgreement;
    }

    public Boolean getConfirmedAddress() {
        return confirmedAddress;
    }

    public void setConfirmedAddress(Boolean confirmedAddress) {
        this.confirmedAddress = confirmedAddress;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }

    public Date getLastModified() {
        return lastModified;
    }

    public void setLastModified(Date lastModified) {
        this.lastModified = lastModified;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Date getDatePurchased() {
        return datePurchased;
    }

    public void setDatePurchased(Date datePurchased) {
        this.datePurchased = datePurchased;
    }

    public Date getOrderDateFinished() {
        return orderDateFinished;
    }

    public void setOrderDateFinished(Date orderDateFinished) {
        this.orderDateFinished = orderDateFinished;
    }

    public BigDecimal getCurrencyValue() {
        return currencyValue;
    }

    public void setCurrencyValue(BigDecimal currencyValue) {
        this.currencyValue = currencyValue;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public Delivery getDelivery() {
        return delivery;
    }

    public void setDelivery(Delivery delivery) {
        this.delivery = delivery;
    }

    public Billing getBilling() {
        return billing;
    }

    public void setBilling(Billing billing) {
        this.billing = billing;
    }

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public MerchantStore getMerchant() {
        return merchant;
    }

    public void setMerchant(MerchantStore merchant) {
        this.merchant = merchant;
    }


    public ImportMainEnums getImportMain() {
        return importMain;
    }

    public void setImportMain(ImportMainEnums importMain) {
        this.importMain = importMain;
    }

    public String getCustomsClearanceNumber() {
        return customsClearanceNumber;
    }

    public void setCustomsClearanceNumber(String customsClearanceNumber) {
        this.customsClearanceNumber = customsClearanceNumber;
    }
}
