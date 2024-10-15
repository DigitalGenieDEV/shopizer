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
	private Long id=0L;
	private String code="";
	private Long optionId=0L;
	private Long descId=0L;
	private int categoryId=0;
	private String description="";
	private List<ReadableProductOptionValue4> optionValue = new ArrayList<ReadableProductOptionValue4>();
	
	
}
