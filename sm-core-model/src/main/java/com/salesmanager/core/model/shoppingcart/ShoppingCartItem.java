package com.salesmanager.core.model.shoppingcart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.price.FinalPrice;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;


@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "SHOPPING_CART_ITEM")
public class ShoppingCartItem extends SalesManagerEntity<Long, ShoppingCartItem> implements Auditable, Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SHP_CART_ITEM_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "SHP_CRT_ITM_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@JsonIgnore
	@ManyToOne(targetEntity = ShoppingCart.class)
	@JoinColumn(name = "SHP_CART_ID", nullable = false)
	private ShoppingCart shoppingCart;

	@Column(name="QUANTITY")
	private Integer quantity = 1;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Deprecated //Use sku
	@Column(name="PRODUCT_ID", nullable=false) 
    private Long productId;
	
	//SKU
	@Column(name="SKU", nullable=true) 
	private String sku;

	@JsonIgnore
	@Transient
	private boolean productVirtual;

	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "shoppingCartItem")
	private Set<ShoppingCartAttributeItem> attributes = new HashSet<ShoppingCartAttributeItem>();
	
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


	/**
	 * 增值服务Id list列表用逗号分隔
	 * The value-added service ID list is separated by commas.
	 */
	@Column(name = "ADDITIONAL_SERVICES_IDS")
	private String additionalServicesIds;



	/**
	 * 国内运输还是国外运输
	 */
	@Column(name = "SHIPPING_TYPE")
	private ShippingType shippingType;

	/**
	 * 国际运输方式
	 * @see TransportationMethod
	 */
	@Column(name = "INTERNATIONAL_TRANSPORTATION_METHOD")
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



	/**
	 * 货车型号
	 * @see TruckModelEnums
	 */
	@Column(name = "TRUCK_MODEL", unique=true, nullable=false)
	private String truckModel;


	/**
	 * 货车类型
	 * @see TruckTypeEnums
	 */
	@Column(name = "TRUCK_TYPE", unique=true, nullable=false)
	private String truckType;



	public ShoppingCartItem(ShoppingCart shoppingCart, Product product) {
		this(product);
		this.shoppingCart = shoppingCart;
	}

	public ShoppingCartItem(Product product) {
		this.product = product;
		this.productId = product.getId();
//		this.setSku(product.getSku());
		this.quantity = 1;
	}

	/** remove usage to limit possibility to implement bugs, would use constructors above to make sure all needed attributes are set correctly **/
	@Deprecated
	public ShoppingCartItem() {
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		this.auditSection = audit;

	}

	@Override
	public Long getId() {
		return id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;

	}

	public String getAdditionalServicesIds() {
		return additionalServicesIds;
	}

	public void setAdditionalServicesIds(String additionalServicesIds) {
		this.additionalServicesIds = additionalServicesIds;
	}

	public void setAttributes(Set<ShoppingCartAttributeItem> attributes) {
		this.attributes = attributes;
	}

	public Set<ShoppingCartAttributeItem> getAttributes() {
		return attributes;
	}

	public void setItemPrice(BigDecimal itemPrice) {
		this.itemPrice = itemPrice;
	}

	public BigDecimal getItemPrice() {
		return itemPrice;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public ShoppingCart getShoppingCart() {
		return shoppingCart;
	}

	public void setShoppingCart(ShoppingCart shoppingCart) {
		this.shoppingCart = shoppingCart;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Product getProduct() {
		return product;
	}

	public void addAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem)
	{
	    this.attributes.add(shoppingCartAttributeItem);
	}

	public void removeAttributes(ShoppingCartAttributeItem shoppingCartAttributeItem)
	{
	    this.attributes.remove(shoppingCartAttributeItem);
	}

	public void removeAllAttributes(){
		this.attributes.removeAll(Collections.EMPTY_SET);
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal;
	}

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setFinalPrice(FinalPrice finalPrice) {
		this.finalPrice = finalPrice;
	}

	public FinalPrice getFinalPrice() {
		return finalPrice;
	}

	public boolean isObsolete() {
		return obsolete;
	}

	public void setObsolete(boolean obsolete) {
		this.obsolete = obsolete;
	}


	public boolean isProductVirtual() {
		return productVirtual;
	}

	public void setProductVirtual(boolean productVirtual) {
		this.productVirtual = productVirtual;
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

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
}
