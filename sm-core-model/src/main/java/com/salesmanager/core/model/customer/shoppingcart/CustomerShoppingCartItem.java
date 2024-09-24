package com.salesmanager.core.model.customer.shoppingcart;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTransportationCompanyEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.shipping.CartItemType;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.Getter;
import lombok.Setter;

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


    /**
     * 增值服务Id list列表用逗号分隔
     * The value-added service ID list is separated by commas.
     */
    @Column(name = "ADDITIONAL_SERVICES_IDS")
    private String additionalServicesIdMap;


    /**
     * 国内运输还是国外运输
     */
    @Column(name = "SHIPPING_TYPE")
    @Enumerated(EnumType.STRING)
    private ShippingType shippingType;

    /**
     * 国际运输方式
     * @see TransportationMethod
     */
    @Column(name = "INTERNATIONAL_TRANSPORTATION_METHOD")
    @Enumerated(EnumType.STRING)
    private TransportationMethod internationalTransportationMethod;

    /**
     * 国内运输方式
     * @see TransportationMethod
     */
    @Column(name = "NATIONAL_TRANSPORTATION_METHOD")
    @Enumerated(EnumType.STRING)
    private TransportationMethod nationalTransportationMethod;


    /**
     * 委托配送还是自提
     */
    @Column(name = "SHIPPING_TRANSPORTATION_TYPE")
    @Enumerated(EnumType.STRING)
    private ShippingTransportationType shippingTransportationType;

    @Column(name = "CART_ITEM_TYPE")
    @Enumerated(EnumType.STRING)
    @Getter
    @Setter
    private CartItemType cartItemType;

    /**
     * 货车型号
     * @see TruckModelEnums
     */
    @Column(name = "TRUCK_MODEL")
    private String truckModel;


    /**
     * 货车运输公司
     * @see TruckModelEnums
     */
    @Column(name = "TRUCK_TRANSPORTATION_COMPANY")
    @Enumerated(value = EnumType.STRING)
    private TruckTransportationCompanyEnums truckTransportationCompany;


    /**
     * 通关选项
     * @see PlayThroughOptionsEnums
     */
    @Column(name = "PLAY_THROUGH_OPTION")
    private String playThroughOption;



    /**
     * 货车类型
     * @see TruckTypeEnums
     */
    @Column(name = "TRUCK_TYPE")
    private String truckType;


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


    public String getAdditionalServicesIdMap() {
        return additionalServicesIdMap;
    }

    public void setAdditionalServicesIdMap(String additionalServicesIdMap) {
        this.additionalServicesIdMap = additionalServicesIdMap;
    }

    public ShippingType getShippingType() {
        return shippingType;
    }

    public void setShippingType(ShippingType shippingType) {
        this.shippingType = shippingType;
    }

    public TransportationMethod getInternationalTransportationMethod() {
        return internationalTransportationMethod;
    }

    public void setInternationalTransportationMethod(TransportationMethod internationalTransportationMethod) {
        this.internationalTransportationMethod = internationalTransportationMethod;
    }

    public TransportationMethod getNationalTransportationMethod() {
        return nationalTransportationMethod;
    }

    public void setNationalTransportationMethod(TransportationMethod nationalTransportationMethod) {
        this.nationalTransportationMethod = nationalTransportationMethod;
    }

    public ShippingTransportationType getShippingTransportationType() {
        return shippingTransportationType;
    }

    public void setShippingTransportationType(ShippingTransportationType shippingTransportationType) {
        this.shippingTransportationType = shippingTransportationType;
    }

    public String getTruckModel() {
        return truckModel;
    }

    public void setTruckModel(String truckModel) {
        this.truckModel = truckModel;
    }

    public String getTruckType() {
        return truckType;
    }

    public String getPlayThroughOption() {
        return playThroughOption;
    }

    public void setPlayThroughOption(String playThroughOption) {
        this.playThroughOption = playThroughOption;
    }

    public void setTruckType(String truckType) {
        this.truckType = truckType;
    }

    public TruckTransportationCompanyEnums getTruckTransportationCompany() {
        return truckTransportationCompany;
    }

    public void setTruckTransportationCompany(TruckTransportationCompanyEnums truckTransportationCompany) {
        this.truckTransportationCompany = truckTransportationCompany;
    }
}
