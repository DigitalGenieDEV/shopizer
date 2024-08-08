package com.salesmanager.shop.model.common;

import java.util.ArrayList;

public class Product1688AddEntity {

	private Integer beginPage;

	/**
	 * @return
	 */
	public Integer getBeginPage() {
		return beginPage;
	}

	/**
	 * *
	 * 
	 * 
	 */
	public void setBeginPage(Integer beginPage) {
		this.beginPage = beginPage;
	}

	private Integer pageSize;

	/**
	 * @return
	 */
	public Integer getPageSize() {
		return pageSize;
	}

	/**
	 * *
	 * 
	 * 
	 */
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}

	private String sort;

	/**
	 * @return
	 */
	public String getSort() {
		return sort;
	}

	/**
	 * *
	 * 
	 * 
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}

	private String country;

	/**
	 * @return
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * *
	 * 
	 * 
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	private ArrayList<Product1688ListEntity> keywordList = null;

	public ArrayList<Product1688ListEntity> getKeywordList() {
		return keywordList;
	}

	public void setKeywordList(ArrayList<Product1688ListEntity> keywordList) {
		this.keywordList = keywordList;
	}

}
