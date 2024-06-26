package com.salesmanager.shop.model.customer;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.shop.model.customer.address.Address;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomerEntity extends Customer implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	@ApiModelProperty(notes = "Customer email address. Required for registration")
	@Email(message = "{messages.invalid.email}")
	@NotEmpty(message = "{NotEmpty.customer.emailAddress}")
	private String emailAddress;
	@Valid
	@ApiModelProperty(notes = "Customer billing address")
	private Address billing;
	private Address delivery;
	@ApiModelProperty(notes = "Customer gender M | F")
	private String gender;

	@ApiModelProperty(notes = "2 letters language code en | fr | ...")
	private String language;
	private String firstName;
	private String lastName;

	private String provider;// online, facebook ...

	private String storeCode;

	private String company;
	private String businessNumber;
	private String businessRegistration;
	private Address companyAddress;
	private List<String> withdrawalReason;
	private String withdrawalResonDetail;
	private LocalDateTime withdrawalAt;
 
	// @ApiModelProperty(notes = "Username (use email address)")
	// @NotEmpty(message="{NotEmpty.customer.userName}")
	// can be email or anything else
	private String userName;

	private Double rating = 0D;
	private int ratingCount;
}
