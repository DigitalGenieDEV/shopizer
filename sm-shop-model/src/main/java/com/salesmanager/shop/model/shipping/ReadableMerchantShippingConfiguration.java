package com.salesmanager.shop.model.shipping;

import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;
import com.salesmanager.shop.model.references.ReadableAddress;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class ReadableMerchantShippingConfiguration implements Serializable {

    private Long id;

    private MerchantStore merchantStore;

    private Date dateCreated;

    private Date dateModified;

    private String modifiedBy;

    private String name;

    private String key;

    private Boolean active = new Boolean(false);


    private Boolean defaultShipping = new Boolean(false);


    private String value;

    private ShippingType shippingType;

    private ShippingBasisType shippingBasisType;

    private ShippingPackageType shippingPackageType;


    private TransportationMethod transportationMethods;

    private ShippingOptionPriceType shippingOptionPriceType;

    private ReadableAddress shippingOrigin;

    private ReadableAddress returnShippingOrigin;

    private boolean freeShippingEnabled = false;

    private String orderTotalFreeShipping;

    private String returnShippingPrice;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public Date getDateModified() {
        return dateModified;
    }

    public void setDateModified(Date dateModified) {
        this.dateModified = dateModified;
    }

    public String getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(String modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Boolean getDefaultShipping() {
        return defaultShipping;
    }

    public void setDefaultShipping(Boolean defaultShipping) {
        this.defaultShipping = defaultShipping;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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

    public TransportationMethod getTransportationMethods() {
        return transportationMethods;
    }

    public void setTransportationMethods(TransportationMethod transportationMethods) {
        this.transportationMethods = transportationMethods;
    }

    public ShippingOptionPriceType getShippingOptionPriceType() {
        return shippingOptionPriceType;
    }

    public void setShippingOptionPriceType(ShippingOptionPriceType shippingOptionPriceType) {
        this.shippingOptionPriceType = shippingOptionPriceType;
    }


    public ReadableAddress getShippingOrigin() {
        return shippingOrigin;
    }

    public void setShippingOrigin(ReadableAddress shippingOrigin) {
        this.shippingOrigin = shippingOrigin;
    }

    public ReadableAddress getReturnShippingOrigin() {
        return returnShippingOrigin;
    }

    public void setReturnShippingOrigin(ReadableAddress returnShippingOrigin) {
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
}
