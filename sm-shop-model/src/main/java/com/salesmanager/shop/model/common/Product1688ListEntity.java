package com.salesmanager.shop.model.common;

public class Product1688ListEntity {
	private Long categoryId1688;
	private Long categoryId;
	private String keyword = "";

	public Long getCategoryId1688() {
		return categoryId1688;
	}

	public void setCategoryId1688(Long categoryId1688) {
		this.categoryId1688 = categoryId1688;
	}

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

}
