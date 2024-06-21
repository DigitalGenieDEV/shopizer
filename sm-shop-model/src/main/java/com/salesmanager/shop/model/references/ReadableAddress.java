package com.salesmanager.shop.model.references;

import lombok.Data;

@Data
public class ReadableAddress extends Address {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	private Long id;

	private Long userId;

	private String email;

	private String name;

	private String company;

	private boolean isDefault;

	private String telephone;


}
