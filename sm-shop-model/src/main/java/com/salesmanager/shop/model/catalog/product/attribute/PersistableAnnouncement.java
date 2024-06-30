package com.salesmanager.shop.model.catalog.product.attribute;

import lombok.Data;

@Data
public class PersistableAnnouncement {

    private Long id;

    private boolean attributeRequired=false;

    private PersistableProductOption option;

    private PersistableProductOptionValue value;

    private Long productId;
}
