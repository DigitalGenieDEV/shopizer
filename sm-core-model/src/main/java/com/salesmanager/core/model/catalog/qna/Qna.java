package com.salesmanager.core.model.catalog.qna;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaDescription;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaReply;
import com.salesmanager.core.model.catalog.product.review.ProductReviewStat;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class Qna extends SalesManagerEntity<Long, Qna> {
	
	@Id
	@Column(name = "QNA_ID")
	private Long id;
	
	@Column(name = "QNA_DATE")
	private String qnaDate;
	
	@Column(name = "QNA_TITLE")
	private String qnaTitle;
	
	@Column(name="QNA_DESCRIPTION")
	private String qnaDescription;
	
	@Column(name = "SECRET")
	private boolean secret;
	
	@Column(name = "QUESTION_TYPE")
	private String questionType;
	
	@Column(name = "REPLY_STATUS")
	private String replyStatus;
	
	@Column(name = "CUSTOMER_ID")
	private Long customerId;
	
	@Column(name = "CUSTOMERS_FIRST_NAME")
	private String customerFirstName;
	
	@Column(name = "CUSTOMERS_LAST_NAME")
	private String customerLastName;
	
	@Column(name = "PRODUCT_TYPE")
	private String productType;
	
	@Column(name = "PRODUCT_ID")
	private Long productId;
	
	@Column(name = "PRODUCT_TITLE")
	private String productTitle;
	
	
}
