package com.salesmanager.shop.model.order;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersistableOrderProductDesign extends OrderProductDesignEntity {
    private static final long serialVersionUID = 1L;

}
