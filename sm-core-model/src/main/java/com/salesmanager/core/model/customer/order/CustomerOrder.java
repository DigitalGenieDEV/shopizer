package com.salesmanager.core.model.customer.order;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTransportationCompanyEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.OrderChannel;
import com.salesmanager.core.model.order.OrderSource;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.payment.CreditCard;
import com.salesmanager.core.model.payments.ImportMainEnums;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.reference.currency.Currency;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Locale;

@Entity
@Table(name="CUSTOMER_ORDERS")
public class CustomerOrder extends SalesManagerEntity<Long, CustomerOrder> implements Auditable {

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

    /**
     * @deprecated use {@link AuditSection#getOperatorIp()}
     */
    @Column (name ="IP_ADDRESS")
    @Deprecated
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

    @Column (name ="ORDER_NO")
    private String orderNo;

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


    /**
     * 国内运输还是国外运输
     */
    @Column(name = "SHIPPING_TYPE")
    @Enumerated(EnumType.STRING)
    private ShippingType shippingType;

    /**
     * 国际运输方式
     * @see TransportationMethod
     */
    @Column(name = "INTERNATIONAL_TRANSPORTATION_METHOD")
    @Enumerated(EnumType.STRING)
    private TransportationMethod internationalTransportationMethod;

    /**
     * 国内运输方式
     * @see TransportationMethod
     */
    @Column(name = "NATIONAL_TRANSPORTATION_METHOD")
    @Enumerated(EnumType.STRING)
    private TransportationMethod nationalTransportationMethod;


    /**
     * 委托配送还是自提
     */
    @Column(name = "SHIPPING_TRANSPORTATION_TYPE")
    @Enumerated(EnumType.STRING)
    private ShippingTransportationType shippingTransportationType;


    /**
     * 货车型号
     * @see TruckModelEnums
     */
    @Column(name = "TRUCK_MODEL")
    @Enumerated(EnumType.STRING)
    private TruckModelEnums truckModel;


    /**
     * 货车运输公司
     * @see TruckModelEnums
     */
    @Column(name = "TRUCK_TRANSPORTATION_COMPANY")
    @Enumerated(value = EnumType.STRING)
    private TruckTransportationCompanyEnums truckTransportationCompany;


    /**
     * 通关选项
     * @see PlayThroughOptionsEnums
     */
    @Column(name = "PLAY_THROUGH_OPTION")
    @Enumerated(EnumType.STRING)
    private PlayThroughOptionsEnums playThroughOption;



    /**
     * 货车类型
     * @see TruckTypeEnums
     */
    @Column(name = "TRUCK_TYPE")
    @Enumerated(EnumType.STRING)
    private TruckTypeEnums truckType;

    /**
     * 订单来源
     * @see OrderSource
     */
    @Column(name = "SOURCE")
    @Enumerated(EnumType.STRING)
    private OrderSource source;

    @Embedded
    private AuditSection auditSection = new AuditSection();


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

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }


    public ShippingType getShippingType() {
        return shippingType;
    }

    public void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }

    public TransportationMethod getInternationalTransportationMethod() {
        return internationalTransportationMethod;
    }

    public void setInternationalTransportationMethod(TransportationMethod internationalTransportationMethod) {
        this.internationalTransportationMethod = internationalTransportationMethod;
    }

    public TransportationMethod getNationalTransportationMethod() {
        return nationalTransportationMethod;
    }

    public void setNationalTransportationMethod(TransportationMethod nationalTransportationMethod) {
        this.nationalTransportationMethod = nationalTransportationMethod;
    }

    public ShippingTransportationType getShippingTransportationType() {
        return shippingTransportationType;
    }

    public void setShippingTransportationType(ShippingTransportationType shippingTransportationType) {
        this.shippingTransportationType = shippingTransportationType;
    }

    public TruckModelEnums getTruckModel() {
        return truckModel;
    }

    public void setTruckModel(TruckModelEnums truckModel) {
        this.truckModel = truckModel;
    }

    public PlayThroughOptionsEnums getPlayThroughOption() {
        return playThroughOption;
    }

    public void setPlayThroughOption(PlayThroughOptionsEnums playThroughOption) {
        this.playThroughOption = playThroughOption;
    }

    public TruckTypeEnums getTruckType() {
        return truckType;
    }

    public void setTruckType(TruckTypeEnums truckType) {
        this.truckType = truckType;
    }


    public TruckTransportationCompanyEnums getTruckTransportationCompany() {
        return truckTransportationCompany;
    }

    public void setTruckTransportationCompany(TruckTransportationCompanyEnums truckTransportationCompany) {
        this.truckTransportationCompany = truckTransportationCompany;
    }

    public OrderSource getSource() {
        return source;
    }

    public void setSource(OrderSource source) {
        this.source = source;
    }

    @Override
    public AuditSection getAuditSection() {
        return auditSection;
    }

    @Override
    public void setAuditSection(AuditSection auditSection) {
        this.auditSection = auditSection;
    }
}
