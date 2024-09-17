package com.salesmanager.core.model.order.orderproduct;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.fulfillment.QcInfo;
import com.salesmanager.core.model.fulfillment.ShippingDocumentOrder;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.reference.zone.Zone;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import lombok.EqualsAndHashCode;

@Entity
@Table (name="ORDER_PRODUCT" )
public class OrderProduct extends SalesManagerEntity<Long, OrderProduct> {
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column (name="ORDER_PRODUCT_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "ORDER_PRODUCT_ID_NEXT_VALUE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column (name="PRODUCT_SKU")
	private String sku;

	@Column (name="PRODUCT_ID")
	private Long productId;

	/**
	 * 国内运输还是国外运输
	 */
	@Column(name = "SHIPPING_TYPE")
	@Enumerated(value = EnumType.STRING)
	private ShippingType shippingType;


	/**
	 * 委托配送还是自提
	 */
	@Column(name = "SHIPPING_TRANSPORTATION_TYPE")
	@Enumerated(value = EnumType.STRING)
	private ShippingTransportationType shippingTransportationType;


	/**
	 * 国际运输方式
	 * @see TransportationMethod
	 */
	@Column(name = "INTERNATIONAL_TRANSPORTATION_METHOD")
	@Enumerated(value = EnumType.STRING)
	private TransportationMethod internationalTransportationMethod;

	/**
	 * 国内运输方式
	 * @see TransportationMethod
	 */
	@Column(name = "NATIONAL_TRANSPORTATION_METHOD")
	@Enumerated(value = EnumType.STRING)
	private TransportationMethod nationalTransportationMethod;


	/**
	 * 通关选项
	 * @see PlayThroughOptionsEnums
	 */
	@Column(name = "PLAY_THROUGH_OPTION")
	@Enumerated(value = EnumType.STRING)
	private PlayThroughOptionsEnums playThroughOption;

	/**
	 * 货车型号
	 * @see TruckModelEnums
	 */
	@Column(name = "TRUCK_MODEL")
	@Enumerated(value = EnumType.STRING)
	private TruckModelEnums truckModel;

	/**
	 * 货车类型
	 * @see TruckTypeEnums
	 */
	@Column(name = "TRUCK_TYPE")
	@Enumerated(value = EnumType.STRING)
	private TruckTypeEnums truckType;


	/**
	 * 增值服务Id Map 结构，key 为id  value 为数量
	 */
	@Column(name = "ADDITIONAL_SERVICES_IDS")
	private String additionalServicesMap;


	@JsonIgnore
	@EqualsAndHashCode.Exclude
	@ManyToOne(targetEntity = ShippingDocumentOrder.class, cascade = CascadeType.ALL)
	@JoinColumn(name = "SHIPPING_DOCUMENT_ORDER_ID")
	private ShippingDocumentOrder shippingDocumentOrder;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="QC_INFO_ID")
	private QcInfo qcInfo;


	@Column (name="PRODUCT_NAME" , length=64 , nullable=false)
	private String productName;


	@Column (name="IS_IN_SHIPPING_ORDER")
	private Boolean isInShippingOrder;

	@Column (name="PRODUCT_QUANTITY")
	private int productQuantity;

	@Column (name="ONETIME_CHARGE" , nullable=false )
	private BigDecimal oneTimeCharge;

	@JsonIgnore
	@ManyToOne(targetEntity = Order.class)
	@JoinColumn(name = "ORDER_ID", nullable = false)
	private Order order;

	@OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private Set<OrderProductAttribute> orderAttributes = new HashSet<OrderProductAttribute>();

	@OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private Set<OrderProductPrice> prices = new HashSet<OrderProductPrice>();

	@OneToMany(mappedBy = "orderProduct", cascade = CascadeType.ALL)
	private Set<OrderProductDownload> downloads = new HashSet<OrderProductDownload>();

	@OneToOne(mappedBy = "orderProduct")
	private OrderProductDesign design;

	public OrderProduct() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}


	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getProductQuantity() {
		return productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}


	public PlayThroughOptionsEnums getPlayThroughOption() {
		return playThroughOption;
	}

	public void setPlayThroughOption(PlayThroughOptionsEnums playThroughOption) {
		this.playThroughOption = playThroughOption;
	}

	public Order getOrder() {
		return order;
	}

	public void setOrder(Order order) {
		this.order = order;
	}


	public Set<OrderProductAttribute> getOrderAttributes() {
		return orderAttributes;
	}

	public void setOrderAttributes(Set<OrderProductAttribute> orderAttributes) {
		this.orderAttributes = orderAttributes;
	}

	public Set<OrderProductPrice> getPrices() {
		return prices;
	}

	public void setPrices(Set<OrderProductPrice> prices) {
		this.prices = prices;
	}

	public Set<OrderProductDownload> getDownloads() {
		return downloads;
	}

	public void setDownloads(Set<OrderProductDownload> downloads) {
		this.downloads = downloads;
	}


	public void setSku(String sku) {
		this.sku = sku;
	}

	public String getSku() {
		return sku;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
	}

	public ShippingTransportationType getShippingTransportationType() {
		return shippingTransportationType;
	}

	public void setShippingTransportationType(ShippingTransportationType shippingTransportationType) {
		this.shippingTransportationType = shippingTransportationType;
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

	public TruckModelEnums getTruckModel() {
		return truckModel;
	}

	public void setTruckModel(TruckModelEnums truckModel) {
		this.truckModel = truckModel;
	}

	public TruckTypeEnums getTruckType() {
		return truckType;
	}

	public void setTruckType(TruckTypeEnums truckType) {
		this.truckType = truckType;
	}

	public void setOneTimeCharge(BigDecimal oneTimeCharge) {
		this.oneTimeCharge = oneTimeCharge;
	}

	public BigDecimal getOneTimeCharge() {
		return oneTimeCharge;
	}


	public String getAdditionalServicesMap() {
		return additionalServicesMap;
	}

	public void setAdditionalServicesMap(String additionalServicesMap) {
		this.additionalServicesMap = additionalServicesMap;
	}

	public ShippingDocumentOrder getShippingDocumentOrder() {
		return shippingDocumentOrder;
	}

	public void setShippingDocumentOrder(ShippingDocumentOrder shippingDocumentOrder) {
		this.shippingDocumentOrder = shippingDocumentOrder;
	}

	public Boolean getInShippingOrder() {
		return isInShippingOrder;
	}

	public void setInShippingOrder(Boolean inShippingOrder) {
		isInShippingOrder = inShippingOrder;
	}

	public QcInfo getQcInfo() {
		return qcInfo;
	}

	public void setQcInfo(QcInfo qcInfo) {
		this.qcInfo = qcInfo;
	}
}
