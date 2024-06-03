package com.salesmanager.shop.model.term;

import java.io.Serializable;
import java.util.Date;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ReadableCustomerTerms extends CustomerTermsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	public ReadableCustomerTerms(
			Long id,
			boolean consented,
			Date modifiedDate,
			Date expiredDate,
			String privacyCode,
			String privacyValue
	) {
		super(id, consented, modifiedDate, expiredDate, privacyCode, privacyValue);
	}
	
	
}
