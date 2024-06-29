package com.salesmanager.shop.model.catalog.product;

import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.shop.model.references.ReadableAddress;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class ReadableProductDetailShippingPrice implements Serializable {

	/**
	 * @see ShippingType
	 */
	private List<String> shippingType;


	/**
	 * 运费价格
	 */
	private String shippingPrice;

	/**
	 * 发货地
	 */
	private ReadableAddress shippingOrigin;
}
