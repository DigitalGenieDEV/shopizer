package com.salesmanager.core.model.order.orderstatus;

public enum OrderStatus {


	/**
	 * 待审核订单
	 */
	PENDING_REVIEW("PENDING_REVIEW"),

	/**
	 * 已下单订单
	 */
	ORDERED("ORDERED"),

	/**
	 * 部分支付
	 */
	PARTIAL_PAYMENT("PARTIAL_PAYMENT"),

	/**
	 * 完成支付
	 */
	PAYMENT_COMPLETED("PAYMENT_COMPLETED"),

	/**
	 * 买家确认
	 */
	PROCESSED("PROCESSED"),


	/**
	 * 卖家qc
	 */
	SELLER_QC("SELLER_QC"),

	/**
	 * 运输中
	 */
	DELIVERED("DELIVERED"),

	/**
	 * 运输完成
	 */
	DELIVERY_COMPLETED("DELIVERY_COMPLETED"),

	/**
	 * 确认签收
	 */
	CONFIRM_RECEIPT("CONFIRM_RECEIPT"),

	REFUNDED("REFUNDED"),

	CANCELED("CANCELED"),


	;
	
	private String value;
	
	private OrderStatus(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}

	public static OrderStatus fromValue(String value) {
		for (OrderStatus status : OrderStatus.values()) {
			if (status.getValue().equals(value)) {
				return status;
			}
		}
		return null;
	}
}
