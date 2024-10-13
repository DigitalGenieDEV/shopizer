package com.salesmanager.shop.model.customer;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.content.OutputContentFile;
import com.salesmanager.core.model.customer.CustomerDTO;
import com.salesmanager.shop.model.customer.address.Address;
import com.salesmanager.shop.model.customer.attribute.ReadableCustomerAttribute;
import com.salesmanager.shop.model.security.ReadableGroup;
import com.salesmanager.shop.model.term.ReadableCustomerTerms;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableCustomer extends CustomerEntity implements Serializable {
	
	private Integer grade;
	private Integer totalGqCount;
	private Integer submitGqCount;
	private Integer purchaseCount;
	private Integer purchasePrice;
	private Long avgPurchase;
	private Date joinDate;
	
	public ReadableCustomer(CustomerDTO dto) {
		this.setId(dto.getCustomer_id());
		this.setEmailAddress(dto.getCustomer_email_address());
		this.setCompany(dto.getCompany());
		Address billing = new Address();
		billing.setCountry(dto.getCountry());
		this.setBilling(billing);
		this.grade = dto.getGrade();
		this.totalGqCount =  dto.getTotal_gq_count();
		this.submitGqCount =  dto.getSubmit_gq_count();
		this.purchaseCount = dto.getPurchase_count();
		this.purchasePrice = dto.getPurchase_price();
		this.avgPurchase = dto.getAvg_purchase();
		this.joinDate =  dto.getJoin_date();
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<ReadableCustomerAttribute> attributes = new ArrayList<ReadableCustomerAttribute>();
	private List<ReadableGroup> groups = new ArrayList<ReadableGroup>();
	private List<ReadableCustomerTerms> terms = new ArrayList<>();
	
}
