package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.order.ReadableOrderProduct;

import java.io.Serializable;
import java.util.List;


public class ReadableShippingDocumentOrderList extends ReadableList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<ReadableShippingDocumentOrder> readableShippingDocumentOrders;


	public List<ReadableShippingDocumentOrder> getReadableShippingDocumentOrders() {
		return readableShippingDocumentOrders;
	}

	public void setReadableShippingDocumentOrders(List<ReadableShippingDocumentOrder> readableShippingDocumentOrders) {
		this.readableShippingDocumentOrders = readableShippingDocumentOrders;
	}
}
