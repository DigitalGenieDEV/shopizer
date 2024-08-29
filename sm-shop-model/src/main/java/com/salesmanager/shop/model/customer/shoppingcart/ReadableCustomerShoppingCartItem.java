package com.salesmanager.shop.model.customer.shoppingcart;

import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import com.salesmanager.shop.model.catalog.product.ReadableMinimalProduct;
import com.salesmanager.shop.model.catalog.product.variation.ReadableProductVariation;
import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import com.salesmanager.shop.model.shoppingcart.ReadableShoppingCartAttribute;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ReadableCustomerShoppingCartItem extends ReadableMinimalProduct implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal subTotal;
    private String displaySubTotal;
//    private List<ReadableShoppingCartAttribute> cartItemattributes = new ArrayList<ReadableShoppingCartAttribute>();

    public List<ReadableProductVariation> getVariants() {
        return variants;
    }

    public void setVariants(List<ReadableProductVariation> variants) {
        this.variants = variants;
    }

    private boolean checked;

    private Long productId;

    private String publishWay;

    private List<ReadableProductVariation> variants = null;


    /**
     * 增值服务Id list列表用逗号分隔
     * The value-added service ID list is separated by commas.
     */
    private List<ReadableAdditionalServices> additionalServices;

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

}
