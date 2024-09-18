package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;

import javax.persistence.Column;
import java.util.Date;

@Data
public class PersistableFulfillmentLogisticsUpdateReqDTO {


    private Long id;

    private String type;

    /**
     * 物流单号
     */
    private String logisticsNumber;


    /**
     * 物流公司
     */
    private String nationalLogisticsCompany;


    /**
     * 发货时间
     */
    private Date nationalShippingTime;

    /**
     * 运输信息
     */
    private String  transportInformation;


    private String nationalDriverName;


    private String nationalDriverPhone;



}
