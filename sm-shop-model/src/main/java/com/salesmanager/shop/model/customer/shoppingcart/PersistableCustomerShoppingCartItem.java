package com.salesmanager.shop.model.customer.shoppingcart;

import com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute;

import java.io.Serializable;
import java.util.List;

public class PersistableCustomerShoppingCartItem implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String product;// or product sku (instance or product)
    private int quantity;
    private String promoCode;
    private List<ProductAttribute> attributes;
    private boolean checked;

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public List<ProductAttribute> getAttributes() {
        return attributes;
    }

    public void setAttributes(List<ProductAttribute> attributes) {
        this.attributes = attributes;
    }


    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
