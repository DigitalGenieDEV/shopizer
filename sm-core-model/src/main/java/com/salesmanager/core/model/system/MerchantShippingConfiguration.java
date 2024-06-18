package com.salesmanager.core.model.system;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Merchant configuration information
 *
 */
@Entity
@EntityListeners(AuditListener.class)
@Table(name = "MERCHANT_SHIPPING_CONFIGURATION")
public class MerchantShippingConfiguration extends SalesManagerEntity<Long, MerchantShippingConfiguration>
        implements Serializable, Auditable {

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

//  @Column(name = "CONFIG_KEY")
//  private String key;

  /**
   * activate and deactivate configuration
   */
  @Column(name = "ACTIVE")
  private boolean active;

  @Column(name = "DEFAULT_SHIPPING")
  private boolean defaultShipping;

  @Column(name = "VALUE")
  @Lob
  private String value;

  /**
   * NATIONAL, INTERNATIONAL
   * @see ShippingType
   * @see ShippingType
   */
  @Column(name = "SHIPPING_TYPE")
  private String shippingType;

//  @Enumerated(EnumType.STRING)
//  @Column(name = "SHIPPING_BASIS_TYPE")
//  private ShippingBasisType shippingBasisType = ShippingBasisType.SHIPPING;

  @Enumerated(EnumType.STRING)
  @Column(name = "SHIPPING_PACKAGE_TYPE")
  private ShippingPackageType shippingPackageType;

  /**
   * 	EXPRESS ,FREIGHT , EXPRESS_SERVICE , DIRECT_DELIVERY;
   * @see TransportationMethod
   */
  @Column(name = "TRANSPORTATION_METHOD")
  private String transportationMethod;


  /**
   * @see ShippingTransportationType
   */
  @Column(name = "SHIPPING_TRANSPORTATION_TYPE")
  private String shippingTransportationType;


//  @Enumerated(EnumType.STRING)
//  @Column(name = "SHIPPING_OPTION_PRICE_TYPE")
//  private ShippingOptionPriceType shippingOptionPriceType;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "SHIPPING_ORIGIN_ID")
  private ShippingOrigin shippingOrigin;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "RETURN_SHIPPING_ORIGIN_ID")
  private ShippingOrigin returnShippingOrigin;

  @Column(name = "FREE_SHIPPING_ENABLED")
  private boolean freeShippingEnabled;

  @Column(name = "ORDER_TOTAL_FREE_SHIPPING")
  private String orderTotalFreeShipping;

  @Column(name = "RETURN_SHIPPING_PRICE")
  private String returnShippingPrice;



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

  public ShippingPackageType getShippingPackageType() {
    return shippingPackageType;
  }

  public void setShippingPackageType(ShippingPackageType shippingPackageType) {
    this.shippingPackageType = shippingPackageType;
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

  public boolean isActive() {
    return active;
  }

  public void setActive(boolean active) {
    this.active = active;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isDefaultShipping() {
    return defaultShipping;
  }

  public void setDefaultShipping(boolean defaultShipping) {
    this.defaultShipping = defaultShipping;
  }

  public String getShippingType() {
    return shippingType;
  }

  public void setShippingType(String shippingType) {
    this.shippingType = shippingType;
  }

  public String getTransportationMethod() {
    return transportationMethod;
  }

  public void setTransportationMethod(String transportationMethod) {
    this.transportationMethod = transportationMethod;
  }

  public String getShippingTransportationType() {
    return shippingTransportationType;
  }

  public void setShippingTransportationType(String shippingTransportationType) {
    this.shippingTransportationType = shippingTransportationType;
  }
}
