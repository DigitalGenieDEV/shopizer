package com.salesmanager.shop.model.term;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CustomerTermsEntity extends CustomerTerms implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private Long id;
	private boolean consented = true;
	private Date modifiedDate;
	private Date expiredDate;
	private String privacyCode;
	private String privacyValue;
}
