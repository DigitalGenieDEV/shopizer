package com.salesmanager.shop.model.favorites;

import com.salesmanager.shop.model.entity.Entity;

import java.io.Serializable;

public class FavoritesEntity extends Entity implements Serializable {

    private static final long serialVersionUID = 1L;


    private Long productId;

    private Long userId;

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
