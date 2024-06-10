package com.salesmanager.shop.model.catalog.product.attribute;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ReadableProductOptionValue2 {
	private int id=0;
	private String code="";
	private String valueName="";
	private String image="";
	private String type="";
}
