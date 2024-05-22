package com.salesmanager.core.model.system;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Merchant configuration information
 * 
 * @author Carl Samson
 *
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "MERCHANT_SHIPPING_CONFIGURATION")
public class MerchantShippingConfiguration extends SalesManagerEntity<Long, MerchantShippingConfiguration>
    implements Serializable, Auditable {

  /**
   * 
   */
  private static final long serialVersionUID = 4246917986731953459L;

  @Id
  @Column(name = "MERCHANT_SHIPPING_CONFIG_ID")
  @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME",
      valueColumnName = "SEQ_COUNT", pkColumnValue = "MERCH_SHIP_CONF_SEQ_NEXT_VAL")
  @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
  private Long id;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "MERCHANT_ID", nullable = true)
  private MerchantStore merchantStore;

  @Embedded
  private AuditSection auditSection = new AuditSection();

  @Column(name = "NAME")
  private String name;

  @Column(name = "CONFIG_KEY")
  private String key;

  /**
   * activate and deactivate configuration
   */
  @Column(name = "ACTIVE", nullable = true)
  private Boolean active = new Boolean(false);


  @Column(name = "DEFAULT_SHIPPING", nullable = true)
  private Boolean defaultShipping = new Boolean(false);


  @Column(name = "VALUE")
  @Type(type = "org.hibernate.type.TextType")
  private String value;

  @Column(name = "SHIPPING_TYPE")
  @Enumerated(value = EnumType.STRING)
  private ShippingType shippingType =
          ShippingType.NATIONAL;

  @Column(name = "SHIPPING_BASIS_TYPE")
  @Enumerated(value = EnumType.STRING)
  private ShippingBasisType shippingBasisType;

  @Column(name = "SHIPPING_PACKAGE_TYPE")
  @Enumerated(value = EnumType.STRING)
  private ShippingPackageType shippingPackageType;


  @Column(name = "TRANSPORTATION_METHOD")
  @Enumerated(value = EnumType.STRING)
  private TransportationMethod transportationMethod;

  @Column(name = "SHIPPING_OPTION_PRICE_TYPE")
  @Enumerated(value = EnumType.STRING)
  private ShippingOptionPriceType shippingOptionPriceType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SHIPPING_ORIGIN_ID")
  private ShippingOrigin shippingOrigin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RETURN_SHIPPING_ORIGIN_ID")
  private ShippingOrigin returnShippingOrigin;

  @Column(name = "FREE_SHIPPING_ENABLED")
  private boolean freeShippingEnabled = false;


  @Column(name = "ORDER_TOTAL_FREE_SHIPPING")
  private String orderTotalFreeShipping;

  @Column(name = "RETURN_SHIPPING_PRICE")
  private String returnShippingPrice;

  public void setKey(String key) {
    this.key = key;
  }

  public String getKey() {
    return key;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public String getValue() {
    return value;
  }

  public AuditSection getAuditSection() {
    return auditSection;
  }

  public void setAuditSection(AuditSection auditSection) {
    this.auditSection = auditSection;
  }

  @Override
  public Long getId() {
    return id;
  }

  @Override
  public void setId(Long id) {
    this.id = id;
  }



  public MerchantStore getMerchantStore() {
    return merchantStore;
  }

  public void setMerchantStore(MerchantStore merchantStore) {
    this.merchantStore = merchantStore;
  }


  public ShippingType getShippingType() {
    return shippingType;
  }

  public void setShippingType(ShippingType shippingType) {
    this.shippingType = shippingType;
  }

  public ShippingBasisType getShippingBasisType() {
    return shippingBasisType;
  }

  public void setShippingBasisType(ShippingBasisType shippingBasisType) {
    this.shippingBasisType = shippingBasisType;
  }

  public ShippingPackageType getShippingPackageType() {
    return shippingPackageType;
  }

  public void setShippingPackageType(ShippingPackageType shippingPackageType) {
    this.shippingPackageType = shippingPackageType;
  }

  public ShippingOptionPriceType getShippingOptionPriceType() {
    return shippingOptionPriceType;
  }

  public void setShippingOptionPriceType(ShippingOptionPriceType shippingOptionPriceType) {
    this.shippingOptionPriceType = shippingOptionPriceType;
  }

  public ShippingOrigin getShippingOrigin() {
    return shippingOrigin;
  }

  public void setShippingOrigin(ShippingOrigin shippingOrigin) {
    this.shippingOrigin = shippingOrigin;
  }

  public ShippingOrigin getReturnShippingOrigin() {
    return returnShippingOrigin;
  }

  public void setReturnShippingOrigin(ShippingOrigin returnShippingOrigin) {
    this.returnShippingOrigin = returnShippingOrigin;
  }

  public boolean isFreeShippingEnabled() {
    return freeShippingEnabled;
  }

  public void setFreeShippingEnabled(boolean freeShippingEnabled) {
    this.freeShippingEnabled = freeShippingEnabled;
  }

  public String getOrderTotalFreeShipping() {
    return orderTotalFreeShipping;
  }

  public void setOrderTotalFreeShipping(String orderTotalFreeShipping) {
    this.orderTotalFreeShipping = orderTotalFreeShipping;
  }

  public String getReturnShippingPrice() {
    return returnShippingPrice;
  }

  public void setReturnShippingPrice(String returnShippingPrice) {
    this.returnShippingPrice = returnShippingPrice;
  }

  public Boolean getActive() {
    return active;
  }

  public void setActive(Boolean active) {
    this.active = active;
  }


  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Boolean getDefaultShipping() {
    return defaultShipping;
  }

  public void setDefaultShipping(Boolean defaultShipping) {
    this.defaultShipping = defaultShipping;
  }

  public TransportationMethod getTransportationMethod() {
    return transportationMethod;
  }

  public void setTransportationMethod(TransportationMethod transportationMethod) {
    this.transportationMethod = transportationMethod;
  }
}
