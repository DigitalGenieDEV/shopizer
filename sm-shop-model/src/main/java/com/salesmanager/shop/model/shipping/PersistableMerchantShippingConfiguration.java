package com.salesmanager.shop.model.shipping;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PersistableMerchantShippingConfiguration implements Serializable {

    private Long id;

    private MerchantStore merchantStore;

    private Date dateCreated;

    private Date dateModified;

    private String modifiedBy;

    private String name;

    private String key;

    private Boolean active;


    private Boolean defaultShipping;


    private String value;

    private List<String> shippingTypeList;

    private String shippingBasisType;

    private String shippingPackageType;

    private List<String> transportationMethods;

    private String shippingOptionPriceType;

    private PersistableAddress shippingOrigin;

    private PersistableAddress returnShippingOrigin;

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


    public String getShippingBasisType() {
        return shippingBasisType;
    }

    public void setShippingBasisType(String shippingBasisType) {
        this.shippingBasisType = shippingBasisType;
    }

    public String getShippingPackageType() {
        return shippingPackageType;
    }

    public void setShippingPackageType(String shippingPackageType) {
        this.shippingPackageType = shippingPackageType;
    }

    public List<String> getShippingTypeList() {
        return shippingTypeList;
    }

    public void setShippingTypeList(List<String> shippingTypeList) {
        this.shippingTypeList = shippingTypeList;
    }

    public List<String> getTransportationMethods() {
        return transportationMethods;
    }

    public void setTransportationMethods(List<String> transportationMethods) {
        this.transportationMethods = transportationMethods;
    }

    public String getShippingOptionPriceType() {
        return shippingOptionPriceType;
    }

    public void setShippingOptionPriceType(String shippingOptionPriceType) {
        this.shippingOptionPriceType = shippingOptionPriceType;
    }

    public PersistableAddress getShippingOrigin() {
        return shippingOrigin;
    }

    public void setShippingOrigin(PersistableAddress shippingOrigin) {
        this.shippingOrigin = shippingOrigin;
    }

    public PersistableAddress getReturnShippingOrigin() {
        return returnShippingOrigin;
    }

    public void setReturnShippingOrigin(PersistableAddress returnShippingOrigin) {
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
