package com.salesmanager.shop.model.order;

import java.io.Serializable;

import com.salesmanager.shop.model.entity.Entity;


public class OrderProduct extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String sku;
	private Long orderId;
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
}
