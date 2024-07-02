package com.salesmanager.shop.model.catalog.product;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PersistableProductPriceDiscount {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private int discount;


	private Long productId;

}
