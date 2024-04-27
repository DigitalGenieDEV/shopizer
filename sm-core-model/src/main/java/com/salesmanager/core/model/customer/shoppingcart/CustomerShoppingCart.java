package com.salesmanager.core.model.customer.shoppingcart;

import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;

import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * <p>Shopping cart is responsible for storing and carrying
 * shopping cart information.Shopping Cart consists of {@link ShoppingCartItem}
 * which represents individual lines items associated with the shopping cart</p>
 * @author Umesh Awasthi
 * version 2.0
 *
 */
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "CUSTOMER_SHOPPING_CART", indexes= { @Index(name = "CUS_SHP_CART_CODE_IDX", columnList = "CUS_SHP_CART_CODE"), @Index(name = "CUS_SHP_CART_CUSTOMER_IDX", columnList = "CUSTOMER_ID")})
public class CustomerShoppingCart extends SalesManagerEntity<Long, CustomerShoppingCart> {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "CUS_SHP_CART_ID", unique=true, nullable=false)
    @TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CUS_SHP_CRT_SEQ_NEXT_VAL")
    @GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
    private Long id;

    /**
     * Will be used to fetch shopping cart model from the controller
     * this is a unique code that should be attributed from the client (UI)
     *
     */
    @Column(name = "CUS_SHP_CART_CODE", unique=true, nullable=false)
    private String customerShoppingCartCode;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "customerShoppingCart")
    private Set<CustomerShoppingCartItem> lineItems = new HashSet<CustomerShoppingCartItem>();

    @Column(name = "CUSTOMER_ID", nullable = true)
    private Long customerId;

    @Column (name ="IP_ADDRESS")
    private String ipAddress;

    @Column (name ="PROMO_CODE")
    private String promoCode;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "PROMO_ADDED")
    private Date promoAdded;

    @Transient
    private boolean obsolete = false;//when all items are obsolete

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getCustomerShoppingCartCode() {
        return customerShoppingCartCode;
    }

    public void setCustomerShoppingCartCode(String customerShoppingCartCode) {
        this.customerShoppingCartCode = customerShoppingCartCode;
    }

    public Set<CustomerShoppingCartItem> getLineItems() {
        return lineItems;
    }

    public void setLineItems(Set<CustomerShoppingCartItem> lineItems) {
        this.lineItems = lineItems;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public Date getPromoAdded() {
        return promoAdded;
    }

    public void setPromoAdded(Date promoAdded) {
        this.promoAdded = promoAdded;
    }

    public boolean isObsolete() {
        return obsolete;
    }

    public void setObsolete(boolean obsolete) {
        this.obsolete = obsolete;
    }
}
