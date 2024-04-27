package com.salesmanager.shop.model.customer.shoppingcart;

import com.salesmanager.shop.model.entity.ShopEntity;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerShoppingCartItem extends ShopEntity implements Serializable {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    private String name;
    private String price;
    private String image;
    private BigDecimal productPrice;
    private int quantity;
    private String sku;
    private String code;
    private boolean productVirtual;
    private Integer merchantId;

    private String subTotal;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(BigDecimal productPrice) {
        this.productPrice = productPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isProductVirtual() {
        return productVirtual;
    }

    public void setProductVirtual(boolean productVirtual) {
        this.productVirtual = productVirtual;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public Integer getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Integer merchantId) {
        this.merchantId = merchantId;
    }
}
