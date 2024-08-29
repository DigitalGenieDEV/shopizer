package com.salesmanager.shop.model.catalog.product;

import java.util.Date;

import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.shop.model.customer.ReadableCustomer;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableProductQna {
	private Long id;
	private Date qnaDate;
	private AuditSection auditSection;
	private ReadableCustomer customer;
//	private ReadableProduct product;
	private ReadableProductQnaDescription question;
	private ReadableProductQnaReply answer;
	private boolean secret;
	private String category;
	
}
