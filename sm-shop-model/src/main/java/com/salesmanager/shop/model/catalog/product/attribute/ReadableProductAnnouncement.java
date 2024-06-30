package com.salesmanager.shop.model.catalog.product.attribute;

import com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionEntity;
import lombok.Data;

@Data
public class ReadableProductAnnouncement {

    private Long id;

    private ReadableProductOptionEntity option;

    private com.salesmanager.shop.model.catalog.product.attribute.api.ReadableProductOptionValue value;

}
