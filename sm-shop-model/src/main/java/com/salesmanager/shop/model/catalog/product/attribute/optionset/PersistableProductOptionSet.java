package com.salesmanager.shop.model.catalog.product.attribute.optionset;

import com.salesmanager.core.model.catalog.product.attribute.OptionSetForSaleType;

import java.util.List;

public class PersistableProductOptionSet extends ProductOptionSetEntity{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Long> optionValues;
	private List<Long> productTypes;
	private Long option;

	/**
	 * categoryId
	 */
	private Long categoryId;

	private OptionSetForSaleType optionSetForSaleType;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public OptionSetForSaleType getOptionSetForSaleType() {
		return optionSetForSaleType;
	}

	public void setOptionSetForSaleType(OptionSetForSaleType optionSetForSaleType) {
		this.optionSetForSaleType = optionSetForSaleType;
	}

	public List<Long> getOptionValues() {
		return optionValues;
	}
	public void setOptionValues(List<Long> optionValues) {
		this.optionValues = optionValues;
	}
	public Long getOption() {
		return option;
	}
	public void setOption(Long option) {
		this.option = option;
	}
	public List<Long> getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(List<Long> productTypes) {
		this.productTypes = productTypes;
	}

	
	

}
