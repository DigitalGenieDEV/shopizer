package com.salesmanager.shop.model.catalog.product.attribute.api;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.salesmanager.shop.model.catalog.product.attribute.ReadableProductOption3;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonInclude(Include.NON_NULL)
public class ReadableProductOptionList3 {
	 private List<ReadableProductOption3> properties = new ArrayList<ReadableProductOption3>();

}
