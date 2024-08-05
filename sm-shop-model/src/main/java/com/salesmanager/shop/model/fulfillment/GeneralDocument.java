package com.salesmanager.shop.model.fulfillment;


import com.salesmanager.core.enmus.DocumentTypeEnums;
import lombok.Data;

import java.util.List;
import java.util.Set;

@Data
public class GeneralDocument  {


    private Long id;


    /**
     * @see DocumentTypeEnums
     */
    private String documentType;


    private String documentNumber;


    private String documentUrl;

    /**
     * 履约子单号
     */
    private List<FulfillmentSubOrder> fulfillSubOrders;


    private Long orderId;

}
