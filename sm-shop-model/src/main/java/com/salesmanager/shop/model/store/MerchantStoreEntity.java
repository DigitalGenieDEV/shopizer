package com.salesmanager.shop.model.store;

import java.io.Serializable;
import java.util.List;

import javax.validation.constraints.NotNull;

import com.salesmanager.core.model.merchant.BusinessType;
import com.salesmanager.shop.model.references.MeasureUnit;
import com.salesmanager.shop.model.references.WeightUnit;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MerchantStoreEntity implements Serializable {
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int id;
	@NotNull
	private String code;
	@NotNull
	private String name;

	private String defaultLanguage;//code
	private String currency;//code
	private String inBusinessSince;
	@NotNull
	private String email;
	@NotNull
	private String phone;
	private String template;
	
	private boolean useCache;
	private boolean currencyFormatNational;
	private boolean retailer;
	private MeasureUnit dimension;
	private WeightUnit weight;
	
	private BusinessType type;
	private List<String> categories;
	private Integer headcount;
	private String description;
}
