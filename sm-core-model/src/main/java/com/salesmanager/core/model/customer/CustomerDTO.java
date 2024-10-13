package com.salesmanager.core.model.customer;

import java.util.Date;

public interface CustomerDTO {
	Long getCustomer_id();
	String getCustomer_email_address();
	String getCompany();
	String getCountry();
	Integer getGrade();
	Integer getTotal_gq_count();
	Integer getSubmit_gq_count();
	Integer getPurchase_count();
	Integer getPurchase_price();
	Long getAvg_purchase();
	Date getJoin_date();
}
