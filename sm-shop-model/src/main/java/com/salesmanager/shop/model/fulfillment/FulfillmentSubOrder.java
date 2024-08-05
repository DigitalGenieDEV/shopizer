package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;


@Data
public class FulfillmentSubOrder {

    private static final long serialVersionUID = 1L;

    private Long id;

    /**
     * 交易单id
     */
    private Long orderId;

    /**
     * 履约主单id
     */
    private Long fulfillmentMainOrderId;


    /**
     * 国内运输还是国外运输
     */
    private ShippingType shippingType;

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
     * 报关单号
     */
    private String customsDeclarationNumber;

    /**
     * 物流单号
     */
    private String logisticsNumber;

    /**
     * 跨境运输单号
     */
    private String crossBorderTransportationLogisticsNumber;

    /**
     * 运输信息
     */
    private String  transportInformation;

    /**
     * 送货单
     */
    private String  deliveryOrder;


    /**
     * 履约子单主状态
     */
    private String fulfillmentMainType;


    /**
     * 履约子单子状态
     */
    private String fulfillmentSubTypeEnums;


    /**
     * 增值服务Id list列表用逗号分隔
     * @see AdditionalServiceEnums
     */
    private String additionalServicesIds;


}
