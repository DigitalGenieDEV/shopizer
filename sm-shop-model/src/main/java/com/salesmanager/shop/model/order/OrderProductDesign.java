package com.salesmanager.shop.model.order;

import com.salesmanager.shop.model.entity.Entity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class OrderProductDesign extends Entity {
    private static final long serialVersionUID = 1L;

    private Long orderProductId;

    private String stickerDesignImage;
    private String packageDesignImage;
    private String remark;
}
