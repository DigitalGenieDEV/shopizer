package com.salesmanager.core.model.common;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Embeddable
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyAddress implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column (name ="COMPANY_STREET_ADDRESS", length=256)
	private String address;

	@Column (name ="COMPANY_CITY", length=100)
	private String city;
	
	@Column (name ="COMPANY_POSTCODE", length=20)
	private String postalCode;
}
