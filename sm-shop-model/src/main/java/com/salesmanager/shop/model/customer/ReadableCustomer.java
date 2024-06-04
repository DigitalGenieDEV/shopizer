package com.salesmanager.shop.model.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.customer.attribute.ReadableCustomerAttribute;
import com.salesmanager.shop.model.security.ReadableGroup;
import com.salesmanager.shop.model.term.ReadableCustomerTerms;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReadableCustomer extends CustomerEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ReadableCustomerAttribute> attributes = new ArrayList<ReadableCustomerAttribute>();
	private List<ReadableGroup> groups = new ArrayList<ReadableGroup>();
	private List<ReadableCustomerTerms> terms = new ArrayList<>();
}
