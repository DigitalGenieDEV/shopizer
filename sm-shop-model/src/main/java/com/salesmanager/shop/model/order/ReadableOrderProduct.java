package com.salesmanager.shop.model.order;

import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.shipping.ShippingOption;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.fulfillment.ReadableFulfillmentSubOrder;
import com.salesmanager.shop.model.fulfillment.ReadableInvoicePackingForm;
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
	
	private List<ReadableOrderProductAttribute> attributes = null;

	private ReadableBilling billing;
	private ReadableDelivery delivery;
//	private ShippingOption shippingOption;
	private ReadablePayment payment;

	private ReadableFulfillmentSubOrder readableFulfillmentSubOrder;
	
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
}
