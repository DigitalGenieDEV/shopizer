package com.salesmanager.shop.model.shipping;

import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.*;
import com.salesmanager.shop.model.references.ReadableAddress;
import lombok.Data;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class ReadableMerchantShippingConfiguration implements Serializable {

    private Integer productNum;

    private Long id;

//    private MerchantStore merchantStore;

    private Date dateCreated;

    private Date dateModified;

    private String modifiedBy;

    private String name;

    private Boolean active = new Boolean(false);

    private Boolean defaultShipping = new Boolean(false);

    /**
     * @see ShippingType
     */
    private String shippingType;

//    /**
//     * @see ShippingPackageType
//     */
//    private String shippingPackageType;

    /**
     * @see ShippingTransportationType
     */
    private List<String> shippingTransportationType;


//    private List<TransportationMethod> transportationMethods;

    private ReadableAddress shippingOrigin;

    private ReadableAddress returnShippingOrigin;

//    private boolean freeShippingEnabled = false;

//    private String orderTotalFreeShipping;

    private String returnShippingPrice;

  }
