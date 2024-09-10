package com.salesmanager.shop.model.fulfillment;

import com.salesmanager.shop.model.entity.ReadableList;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v1.ReadableOrder;

import java.io.Serializable;
import java.util.List;


public class ReadableOrderProductShippingList extends ReadableList implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	List<ReadableOrderProduct> products;

	public List<ReadableOrderProduct> getProducts() {
		return products;
	}

	public void setProducts(List<ReadableOrderProduct> products) {
		this.products = products;
	}
}
