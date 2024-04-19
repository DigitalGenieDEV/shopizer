package com.salesmanager.shop.model.customer.shoppingcart;

import com.salesmanager.shop.model.entity.ShopEntity;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Scope(value = "prototype")
public class CustomerShoppingCartData extends ShopEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private String message;
    private String code;
    private int quantity;
    private String total;
    private String subTotal;
    private Long orderId;

    private List<CustomerShoppingCartItem> shoppingCartItems;
    private List<CustomerShoppingCartItem> unavailables;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(String subTotal) {
        this.subTotal = subTotal;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<CustomerShoppingCartItem> getShoppingCartItems() {
        return shoppingCartItems;
    }

    public void setShoppingCartItems(List<CustomerShoppingCartItem> shoppingCartItems) {
        this.shoppingCartItems = shoppingCartItems;
    }

    public List<CustomerShoppingCartItem> getUnavailables() {
        return unavailables;
    }

    public void setUnavailables(List<CustomerShoppingCartItem> unavailables) {
        this.unavailables = unavailables;
    }
}
