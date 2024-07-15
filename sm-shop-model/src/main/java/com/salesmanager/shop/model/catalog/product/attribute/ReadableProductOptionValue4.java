package com.salesmanager.shop.model.catalog.product.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ReadableProductOptionValue4 {
	private int valueId=0;
	private String code="";
	private int descId=0;
	private String description= "";
}
