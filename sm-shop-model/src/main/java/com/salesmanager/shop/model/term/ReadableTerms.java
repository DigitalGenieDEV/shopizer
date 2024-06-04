package com.salesmanager.shop.model.term;

import java.io.Serializable;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ReadableTerms extends TermsEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ReadableTerms(
			Long id,
			String description,
			boolean required,
			boolean used
	) {
		super(id, description, required, used);
	}

}
