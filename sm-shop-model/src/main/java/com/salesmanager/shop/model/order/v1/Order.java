package com.salesmanager.shop.model.order.v1;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.core.model.payments.ImportMainEnums;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.order.OrderAttribute;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

public class Order extends Entity {


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
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see OrderType
	 */
	private String orderType;


	/**
	 */
	private String orderNo;

	/**
	 * 进口主体
	 * @see ImportMainEnums
	 */
	private String importMain;

	/**
	 * 清关号码
	 */
	private String customsClearanceNumber;


	private boolean customerAgreement;
	private String comments;
	private String currency;
	private List<OrderAttribute> attributes = new ArrayList<OrderAttribute>();

	private OrderInvoice orderInvoice;

	public boolean isCustomerAgreement() {
		return customerAgreement;
	}

	public void setCustomerAgreement(boolean customerAgreement) {
		this.customerAgreement = customerAgreement;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
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

	public OrderInvoice getInvoice() {
		return orderInvoice;
	}

	public void setInvoice(OrderInvoice orderInvoice) {
		this.orderInvoice = orderInvoice;
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

	public OrderInvoice getOrderInvoice() {
		return orderInvoice;
	}

	public void setOrderInvoice(OrderInvoice orderInvoice) {
		this.orderInvoice = orderInvoice;
	}
}
