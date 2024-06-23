package com.salesmanager.core.model.customer.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "CUSTOMER_SHOPPING_CART_ITEM")
public class CustomerShoppingCartItem extends SalesManagerEntity<Long, CustomerShoppingCartItem> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CUS_SHP_CART_ITEM_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUS_SHP_CRT_ITM_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    @JsonIgnore
    @ManyToOne(targetEntity = CustomerShoppingCart.class)
    @JoinColumn(name = "CUS_SHP_CART_ID", nullable = false)
    private CustomerShoppingCart customerShoppingCart;

    @Column(name="QUANTITY")
    private Integer quantity = 1;

    @Deprecated //Use sku
    @Column(name="PRODUCT_ID", nullable=false)
    private Long productId;

    //SKU
    @Column(name="SKU", nullable=true)
    private String sku;


    @Column(name="CHECKED", nullable=true)
    private boolean checked;

    @Column(name="PRODUCT_VARIANT", nullable=true)
    private Long variant;

    @JsonIgnore
    @Transient
    private BigDecimal itemPrice;//item final price including all rebates

    @JsonIgnore
    @Transient
    private BigDecimal subTotal;//item final price * quantity

    @JsonIgnore
    @Transient
    private FinalPrice finalPrice;//contains price details (raw prices)

    @JsonIgnore
    @Transient
    private Product product;

    @JsonIgnore
    @Transient
    private boolean obsolete = false;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="MERCHANT_ID", nullable=false)
    private MerchantStore merchantStore;

    public CustomerShoppingCartItem(CustomerShoppingCart customerShoppingCart, Product product) {
        this(product);
        this.customerShoppingCart = customerShoppingCart;
    }

    public CustomerShoppingCartItem(Product product) {
        this.product = product;
        this.productId = product.getId();
        this.setSku(product.getSku());
        this.quantity = 1;
    }

    public CustomerShoppingCartItem() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public CustomerShoppingCart getCustomerShoppingCart() {
        return customerShoppingCart;
    }

    public void setCustomerShoppingCart(CustomerShoppingCart customerShoppingCart) {
        this.customerShoppingCart = customerShoppingCart;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }


    public Long getVariant() {
        return variant;
    }

    public void setVariant(Long variant) {
        this.variant = variant;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public FinalPrice getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(FinalPrice finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }

    public MerchantStore getMerchantStore() {
        return merchantStore;
    }

    public void setMerchantStore(MerchantStore merchantStore) {
        this.merchantStore = merchantStore;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }
}
