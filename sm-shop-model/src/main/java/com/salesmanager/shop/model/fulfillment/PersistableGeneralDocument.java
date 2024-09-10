package com.salesmanager.shop.model.fulfillment;


import lombok.Data;

@Data
public class PersistableGeneralDocument extends GeneralDocument{


    private Long shippingOrderId;

}
