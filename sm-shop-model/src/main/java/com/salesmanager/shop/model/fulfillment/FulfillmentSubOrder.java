package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.enmus.*;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Data
public class FulfillmentSubOrder {

    private Long id;

    /**
     * 交易单id
     */
    private Long orderId;


    /**
     * 交易单商品id
     */
    private Long orderProductId;

    /**
     * 国内运输还是国外运输
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
     * 国内司机名字
     */
    private String nationalDriverName;


    /**
     * 国内司机电话
     */
    private String nationalDriverPhone;

    /**
     * 物流公司
     */
    private String nationalLogisticsCompany;


    /**
     * 发货时间
     */
    private Date nationalShippingTime;

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
     * 1688单号
     */
    private String logisticsNumberBy1688;


    /**
     * 运输信息
     */
    private String  transportInformation;

    /**
     * 韩国境内快递单号
     */
    private String logisticsNumber;

    /**
     * 跨境运输单号
     */
    private String crossBorderTransportationLogisticsNumber;



    /**
     * 履约子单主状态
     */
    private String fulfillmentMainType;


    /**
     * 履约子单子状态
     */
    private String fulfillmentSubTypeEnums;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FulfillmentSubOrder that = (FulfillmentSubOrder) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(orderId, that.orderId) &&
                Objects.equals(orderProductId, that.orderProductId) &&
                Objects.equals(shippingType, that.shippingType) &&
                Objects.equals(internationalTransportationMethod, that.internationalTransportationMethod) &&
                Objects.equals(nationalTransportationMethod, that.nationalTransportationMethod) &&
                Objects.equals(shippingTransportationType, that.shippingTransportationType) &&
                Objects.equals(truckModel, that.truckModel) &&
                Objects.equals(truckType, that.truckType) &&
                Objects.equals(logisticsNumber, that.logisticsNumber) &&
                Objects.equals(crossBorderTransportationLogisticsNumber, that.crossBorderTransportationLogisticsNumber) &&
                Objects.equals(transportInformation, that.transportInformation) &&
                Objects.equals(fulfillmentMainType, that.fulfillmentMainType) &&
                Objects.equals(fulfillmentSubTypeEnums, that.fulfillmentSubTypeEnums);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, orderId, orderProductId, shippingType, internationalTransportationMethod,
                nationalTransportationMethod, shippingTransportationType, truckModel, truckType,
                 logisticsNumber, crossBorderTransportationLogisticsNumber,
                transportInformation, fulfillmentMainType, fulfillmentSubTypeEnums);
    }

}
