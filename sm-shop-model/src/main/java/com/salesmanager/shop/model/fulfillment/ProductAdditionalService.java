package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import lombok.Data;


@Data
public class ProductAdditionalService {


    private AdditionalServices additionalServices;

    private Integer quantity;
}
