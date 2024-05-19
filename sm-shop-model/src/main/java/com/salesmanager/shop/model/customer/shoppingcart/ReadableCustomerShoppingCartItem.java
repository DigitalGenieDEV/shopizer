package com.salesmanager.shop.model.customer.shoppingcart;

import com.salesmanager.shop.model.catalog.product.ReadableMinimalProduct;
import com.salesmanager.shop.model.catalog.product.variation.ReadableProductVariation;
import com.salesmanager.shop.model.shoppingcart.ReadableShoppingCartAttribute;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class ReadableCustomerShoppingCartItem extends ReadableMinimalProduct implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal subTotal;
    private String displaySubTotal;
//    private List<ReadableShoppingCartAttribute> cartItemattributes = new ArrayList<ReadableShoppingCartAttribute>();

    public List<ReadableProductVariation> getVariants() {
        return variants;
    }

    public void setVariants(List<ReadableProductVariation> variants) {
        this.variants = variants;
    }

    private List<ReadableProductVariation> variants = null;



    public BigDecimal getSubTotal() {
        return subTotal;
    }
    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }
    public String getDisplaySubTotal() {
        return displaySubTotal;
    }
    public void setDisplaySubTotal(String displaySubTotal) {
        this.displaySubTotal = displaySubTotal;
    }
//    public List<ReadableShoppingCartAttribute> getCartItemattributes() {
//        return cartItemattributes;
//    }
//    public void setCartItemattributes(List<ReadableShoppingCartAttribute> cartItemattributes) {
//        this.cartItemattributes = cartItemattributes;
//    }

}
