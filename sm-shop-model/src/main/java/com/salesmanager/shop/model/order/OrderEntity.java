package com.salesmanager.shop.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.payment.CreditCard;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import com.salesmanager.shop.model.fulfillment.ReadableGeneralDocument;
import com.salesmanager.shop.model.order.total.OrderTotal;
import com.salesmanager.shop.model.order.v0.Order;

public class OrderEntity extends Order implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<OrderTotal> totals;
	private List<OrderAttribute> attributes = new ArrayList<OrderAttribute>();
	
	private PaymentType paymentType;
	private String paymentModule;
	private String shippingModule;
	private List<OrderStatus> previousOrderStatus;
	private OrderStatus orderStatus;
	private CreditCard creditCard;
	private Date datePurchased;

	private String currency;
	private boolean customerAgreed;
	private boolean confirmedAddress;
	private String comments;


	private String orderNo;



	/**
	 * 国内运输还是国外运输
	 */
	private String shippingType;

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
	 * 委托配送还是自提
	 */
	private String shippingTransportationType;


	/**
	 * 货车型号
	 * @see TruckModelEnums
	 */
	private String truckModel;


	/**
	 * 通关选项
	 * @see PlayThroughOptionsEnums
	 */
	private String playThroughOption;



	/**
	 * 货车类型
	 * @see TruckTypeEnums
	 */
	private String truckType;

	/**
	 * @see com.salesmanager.core.model.payments.ImportMainEnums
	 */
	private String importMain;


	private String customsClearanceNumber;

	/**
	 * @see orderType
	 */
	private String orderType;


	private Date dateCreated;

	private Date dateModified;




	public void setTotals(List<OrderTotal> totals) {
		this.totals = totals;
	}
	public List<OrderTotal> getTotals() {
		return totals;
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
	public String getShippingModule() {
		return shippingModule;
	}
	public void setShippingModule(String shippingModule) {
		this.shippingModule = shippingModule;
	}

	public CreditCard getCreditCard() {
		return creditCard;
	}
	public void setCreditCard(CreditCard creditCard) {
		this.creditCard = creditCard;
	}
	public Date getDatePurchased() {
		return datePurchased;
	}
	public void setDatePurchased(Date datePurchased) {
		this.datePurchased = datePurchased;
	}
	public void setPreviousOrderStatus(List<OrderStatus> previousOrderStatus) {
		this.previousOrderStatus = previousOrderStatus;
	}
	public List<OrderStatus> getPreviousOrderStatus() {
		return previousOrderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus) {
		this.orderStatus = orderStatus;
	}
	public OrderStatus getOrderStatus() {
		return orderStatus;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public boolean isCustomerAgreed() {
		return customerAgreed;
	}
	public void setCustomerAgreed(boolean customerAgreed) {
		this.customerAgreed = customerAgreed;
	}
	public boolean isConfirmedAddress() {
		return confirmedAddress;
	}
	public void setConfirmedAddress(boolean confirmedAddress) {
		this.confirmedAddress = confirmedAddress;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public List<OrderAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<OrderAttribute> attributes) {
		this.attributes = attributes;
	}


	public String getImportMain() {
		return importMain;
	}

	public void setImportMain(String importMain) {
		this.importMain = importMain;
	}

	public String getCustomsClearanceNumber() {
		return customsClearanceNumber;
	}

	public void setCustomsClearanceNumber(String customsClearanceNumber) {
		this.customsClearanceNumber = customsClearanceNumber;
	}

	public String getOrderType() {
		return orderType;
	}

	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getDateModified() {
		return dateModified;
	}

	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}

	public String getOrderNo() {
		return orderNo;
	}

	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}


	public String getShippingType() {
		return shippingType;
	}

	public void setShippingType(String shippingType) {
		this.shippingType = shippingType;
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

	public String getShippingTransportationType() {
		return shippingTransportationType;
	}

	public void setShippingTransportationType(String shippingTransportationType) {
		this.shippingTransportationType = shippingTransportationType;
	}

	public String getTruckModel() {
		return truckModel;
	}

	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}

	public String getPlayThroughOption() {
		return playThroughOption;
	}

	public void setPlayThroughOption(String playThroughOption) {
		this.playThroughOption = playThroughOption;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
}
