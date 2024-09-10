package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.shop.model.entity.ReadableAudit;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class ShippingDocumentOrder  {

    private static final long serialVersionUID = 1L;

    private Long id;

    private String shippingNo;



}
