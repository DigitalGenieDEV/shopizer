package com.salesmanager.shop.model.shipping;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.shop.model.references.PersistableAddress;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class PersistableMerchantShippingConfiguration implements Serializable {

    private Long id;

//    private MerchantStore merchantStore;

    private String name;

    private Boolean defaultShipping;

    /**
     * @see ShippingType
     */
    private String shippingType;

//    private String shippingPackageType;

    /**
     * @see ShippingTransportationType
     */
    private List<String> shippingTransportationType;


    private PersistableAddress shippingOrigin;

    private PersistableAddress returnShippingOrigin;

//    private boolean freeShippingEnabled = false;
//
//    private String orderTotalFreeShipping;

    private String returnShippingPrice;

}
