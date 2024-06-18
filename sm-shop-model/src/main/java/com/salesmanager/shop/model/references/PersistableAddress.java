package com.salesmanager.shop.model.references;


import lombok.Data;

@Data
public class PersistableAddress extends Address {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String name;

	private Long id;

	private Long userId;

	private String company;

	private boolean isDefault = true;

	private String telephone;


}
