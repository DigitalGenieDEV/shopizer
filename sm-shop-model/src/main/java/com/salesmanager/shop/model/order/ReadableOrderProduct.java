package com.salesmanager.shop.model.order;

import com.salesmanager.core.enmus.AdditionalServiceEnums;
import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.order.transaction.ReadablePayment;

import java.io.Serializable;
import java.util.List;

public class ReadableOrderProduct extends OrderProductEntity implements
		Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String productName;
	private String price;
	private String subTotal;
	private PaymentType paymentType;
	private String paymentModule;

	private String orderType;


	private List<ReadableOrderProductAttribute> attributes = null;

	private ReadableBilling billing;
	private ReadableDelivery delivery;
//	private ShippingOption shippingOption;
	private ReadablePayment payment;

	/**
	 * 国内运输还是国外运输
	 */
	private String shippingType;


	/**
	 * 委托配送还是自提
	 */
	private String shippingTransportationType;


	/**
	 * 国际运输方式
	 * @see TransportationMethod
	 */
	private String internationalTransportationMethod;

	/**
	 * 国内运输方式
	 * @see TransportationMethod
	 */
	private String nationalTransportationMethod;


	/**
	 * 通关选项
	 * @see PlayThroughOptionsEnums
	 */
	private String playThroughOption;

	/**
	 * 货车型号
	 * @see TruckModelEnums
	 */
	private String truckModel;

	/**
	 * 货车类型
	 * @see TruckTypeEnums
	 */
	private String truckType;


	private Long qcInfoId;


	private ReadableFulfillmentSubOrder readableFulfillmentSubOrder;

	/**
	 *
	 * @see AdditionalServiceEnums
	 */
	private List<ReadableProductAdditionalService> readableProductAdditionalServices;


	private ReadableShippingDocumentOrder readableShippingDocumentOrder;

	private ReadableOrderProductDesign readableOrderProductDesign;

	private String sku;
	private String image;
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public List<ReadableOrderProductAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<ReadableOrderProductAttribute> attributes) {
		this.attributes = attributes;
	}

	public ReadableBilling getBilling() {
		return billing;
	}

	public void setBilling(ReadableBilling billing) {
		this.billing = billing;
	}

	public ReadableDelivery getDelivery() {
		return delivery;
	}

	public void setDelivery(ReadableDelivery delivery) {
		this.delivery = delivery;
	}

	public ReadablePayment getPayment() {
		return payment;
	}

	public void setPayment(ReadablePayment payment) {
		this.payment = payment;
	}

	public PaymentType getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(PaymentType paymentType) {
		this.paymentType = paymentType;
	}

	public String getPaymentModule() {
		return paymentModule;
	}

	public void setPaymentModule(String paymentModule) {
		this.paymentModule = paymentModule;
	}

	public ReadableFulfillmentSubOrder getReadableFulfillmentSubOrder() {
		return readableFulfillmentSubOrder;
	}

	public void setReadableFulfillmentSubOrder(ReadableFulfillmentSubOrder readableFulfillmentSubOrder) {
		this.readableFulfillmentSubOrder = readableFulfillmentSubOrder;
	}

	public List<ReadableProductAdditionalService> getReadableProductAdditionalServices() {
		return readableProductAdditionalServices;
	}

	public void setReadableProductAdditionalServices(List<ReadableProductAdditionalService> readableProductAdditionalServices) {
		this.readableProductAdditionalServices = readableProductAdditionalServices;
	}


	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
	}

	public String getShippingTransportationType() {
		return shippingTransportationType;
	}

	public void setShippingTransportationType(String shippingTransportationType) {
		this.shippingTransportationType = shippingTransportationType;
	}

	public String getInternationalTransportationMethod() {
		return internationalTransportationMethod;
	}

	public void setInternationalTransportationMethod(String internationalTransportationMethod) {
		this.internationalTransportationMethod = internationalTransportationMethod;
	}

	public String getNationalTransportationMethod() {
		return nationalTransportationMethod;
	}

	public void setNationalTransportationMethod(String nationalTransportationMethod) {
		this.nationalTransportationMethod = nationalTransportationMethod;
	}

	public String getPlayThroughOption() {
		return playThroughOption;
	}

	public void setPlayThroughOption(String playThroughOption) {
		this.playThroughOption = playThroughOption;
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


	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public ReadableShippingDocumentOrder getReadableShippingDocumentOrder() {
		return readableShippingDocumentOrder;
	}

	public void setReadableShippingDocumentOrder(ReadableShippingDocumentOrder readableShippingDocumentOrder) {
		this.readableShippingDocumentOrder = readableShippingDocumentOrder;
	}

	public Long getQcInfoId() {
		return qcInfoId;
	}

	public void setQcInfoId(Long qcInfoId) {
		this.qcInfoId = qcInfoId;
	}

	public ReadableOrderProductDesign getReadableOrderProductDesign() {
		return readableOrderProductDesign;
	}

	public void setReadableOrderProductDesign(ReadableOrderProductDesign readableOrderProductDesign) {
		this.readableOrderProductDesign = readableOrderProductDesign;
	}
}
