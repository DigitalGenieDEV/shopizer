package com.salesmanager.shop.model.customer;

import java.util.List;

import com.salesmanager.shop.model.customer.attribute.PersistableCustomerAttribute;
import com.salesmanager.shop.model.security.PersistableGroup;
import com.salesmanager.shop.model.term.PersistableCustomerTerms;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;



@ApiModel(value="Customer", description="Customer model object")
@Getter
@Setter
public class PersistableCustomer extends CustomerEntity {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
    @ApiModelProperty(notes = "Customer password")
	private String password = null;
    private String repeatPassword = null;
	private String company;
	private String businessNumber;
	private String businessRegistration;
	private List<PersistableCustomerAttribute> attributes;
	private List<PersistableGroup> groups;
	private List<PersistableCustomerTerms> customerTerms;

}
