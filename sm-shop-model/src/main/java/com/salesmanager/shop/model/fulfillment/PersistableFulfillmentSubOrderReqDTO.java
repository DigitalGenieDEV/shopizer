package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;

@Data
public class PersistableFulfillmentSubOrderReqDTO {


    private Long id;

    /**
     * 国际运输方式
     * @see TransportationMethod
     */
    private TransportationMethod internationalTransportationMethod;

    /**
     * 国内运输方式
     * @see TransportationMethod
     */
    private TransportationMethod nationalTransportationMethod;

    /**
     * 报关单号
     */
    private String customsDeclarationNumber;

    /**
     * 物流单号
     */
    private String logisticsNumber;

    /**
     * 运输信息
     */
    private String  transportInformation;

    /**
     * 送货单
     */
    private String  deliveryOrder;


    /**
     * 国内运输还是国外运输
     */
    private ShippingType shippingType;


    /**
     * 履约子单主状态
     * FulfillmentTypeEnums
     */
    private String fulfillmentMainType;
    /**
     * 履约子单主状态
     * FulfillmentSubTypeEnums
     */
    private String fulfillmentSubTypeEnums;

    /**
     * 增值服务 list列表用逗号分隔
     */
    private String additionalServicesIds;




}