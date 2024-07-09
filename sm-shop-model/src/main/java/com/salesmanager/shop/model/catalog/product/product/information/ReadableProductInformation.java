package com.salesmanager.shop.model.catalog.product.product.information;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.entity.ReadableList;

public class ReadableProductInformation extends ReadableList {
	private static final long serialVersionUID = 1L;

	private List<ProductInformationEntity> data = new ArrayList<ProductInformationEntity>();

	public List<ProductInformationEntity> getData() {
		return data;
	}

	public void setData(List<ProductInformationEntity> data) {
		this.data = data;
	}
}
