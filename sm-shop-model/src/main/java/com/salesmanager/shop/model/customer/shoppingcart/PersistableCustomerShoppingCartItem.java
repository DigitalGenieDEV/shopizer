package com.salesmanager.shop.model.customer.shoppingcart;

import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.List;

@Data
public class PersistableCustomerShoppingCartItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String sku;// or product sku (instance or product)
    private int quantity;
    private String promoCode;
//    private List<ProductAttribute> attributes;
    private boolean checked;

    /**
     * 增值服务Id list列表用逗号分隔
     * The value-added service ID list is separated by commas.
     */
    private String additionalServicesIds;

    /**
     * 国内运输还是国外运输
     * @see ShippingType
     */
    private String shippingType;

    /**
     * 国际运输方式
     * @see TransportationMethod
     */
    private String internationalTransportationMethod;

    /**
     * 国内运输方式
     * @see TransportationMethod
     */
    private String nationalTransportationMethod;


    /**
     * 委托配送还是自提
     */
    private String shippingTransportationType;



    /**
     * 货车型号
     * @see TruckModelEnums
     */
    private String truckModel;


    /**
     * 货车类型
     * @see TruckTypeEnums
     */
    private String truckType;


    /**
     * 通关选项
     * @see PlayThroughOptionsEnums
     */
    private String playThroughOption;

}
