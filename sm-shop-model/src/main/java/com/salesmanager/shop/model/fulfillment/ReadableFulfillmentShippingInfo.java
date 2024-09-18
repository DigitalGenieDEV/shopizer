package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.enmus.FulfillmentTypeEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Data;

import java.util.List;

@Data
public class ReadableFulfillmentShippingInfo{


    /**
     * 履约状态
     */
    private ReadableFulfillmentSubOrder fulfillmentSubOrder;


    private List<FulfillmentHistory> fulfillmentHistoryList;

}
