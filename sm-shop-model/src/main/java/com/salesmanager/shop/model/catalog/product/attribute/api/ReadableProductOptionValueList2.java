package com.salesmanager.shop.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOptionValue2;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableProductOptionValueList2 {
	 /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private List<ReadableProductOptionValue2> data = new ArrayList<ReadableProductOptionValue2>();

}
