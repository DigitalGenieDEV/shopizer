package com.salesmanager.shop.model.shipping;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class PersistableMerchantShippingConfiguration implements Serializable {

    private Long id;

    private MerchantStore merchantStore;

    private Date dateCreated;

    private Date dateModified;

    private String modifiedBy;

    private String name;

    private String key;

//    private Boolean active;
    
    private Boolean defaultShipping;

    private String value;

    private List<String> shippingTypeList;
    
    private List<String> shippingWayList;

    private String shippingBasisType;
    
    private Boolean returnEnabled;

    private String shippingPackageType;

    private List<String> transportationMethods;

    private String shippingOptionPriceType;

    private PersistableAddress shippingOrigin;

    private PersistableAddress returnShippingOrigin;

    private boolean freeShippingEnabled = false;

    private String orderTotalFreeShipping;

    private String returnShippingPrice;

}
