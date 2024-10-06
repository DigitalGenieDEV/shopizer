package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import lombok.Data;


@Data
public class ReadableProductAdditionalService extends ProductAdditionalService{


    private String status;
}
