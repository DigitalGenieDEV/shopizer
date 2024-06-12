package com.salesmanager.shop.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOption2;
import com.salesmanager.shop.model.entity.ReadableList;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableProductOptionList2 extends ReadableList {

	  /**
	   * 
	   */
	  private static final long serialVersionUID = 1L;

	  private List<ReadableProductOption2> options = new ArrayList<ReadableProductOption2>();



}
