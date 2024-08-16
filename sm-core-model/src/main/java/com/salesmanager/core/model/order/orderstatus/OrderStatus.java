package com.salesmanager.core.model.order.orderstatus;

public enum OrderStatus {

	ORDERED("ordered"),

	PAYMENT_COMPLETED("payment_completed"),

	PROCESSED("processed"),

	SELLER_QC("seller_qc"),

	DELIVERED("delivered"),

	DELIVERY_COMPLETED("delivery_completed"),

	/**
	 * 确认签收
	 */
	CONFIRM_RECEIPT("CONFIRM_RECEIPT"),

	REFUNDED("refunded"),

	CANCELED("canceled"),



	;
	
	private String value;
	
	private OrderStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
}
