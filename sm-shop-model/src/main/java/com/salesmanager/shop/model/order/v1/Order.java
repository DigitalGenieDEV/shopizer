package com.salesmanager.shop.model.order.v1;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.model.order.OrderType;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.order.OrderAttribute;

public class Order extends Entity {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	/**
	 * @see OrderType
	 */
	private String orderType;


	/**
	 * 进口主体
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
}
