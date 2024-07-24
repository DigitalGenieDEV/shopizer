package com.salesmanager.shop.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ReadableProductOption3 {
	private int id=0;
	private String code="";
	private int optionId=0;
	private int descId=0;
	private int categoryId=0;
	private String description="";
	private List<ReadableProductOptionValue4> optionValue = new ArrayList<ReadableProductOptionValue4>();
	
	
}
