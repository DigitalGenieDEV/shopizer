package com.salesmanager.shop.model.catalog.product.product.information;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ProductInformationEntity {
	private Long id;
	private String division="";
	private String desc1="";
	private String desc2="";
	private String desc3="";
	private String language="";
	private String store="";
	
}
