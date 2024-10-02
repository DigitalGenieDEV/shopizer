package com.salesmanager.shop.model.order.v1;

import com.salesmanager.shop.model.fulfillment.ReadableAdditionalServices;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ReadableOrderProductAdditionalServiceInstance extends OrderProductAdditionalServiceInstance {

    private ReadableAdditionalServices additionalServices;

}
