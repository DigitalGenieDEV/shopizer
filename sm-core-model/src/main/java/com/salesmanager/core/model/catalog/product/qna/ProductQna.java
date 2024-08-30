package com.salesmanager.core.model.catalog.product.qna;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.qna.ProductQnaImage;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_QNA")
public class ProductQna extends SalesManagerEntity<Long, ProductQna> implements Auditable {
	
	@Id
	@Column(name = "PRODUCT_QNA_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
	pkColumnValue = "PRODUCT_QNA_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Embedded
	private AuditSection audit = new AuditSection();
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "QNA_DATE")
	private Date qnaDate;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="CUSTOMERS_ID")
	private Customer customer;
	
	@OneToOne
	@JoinColumn(name="PRODUCT_ID")
	private Product product;
	
	@OneToOne(mappedBy = "productQna", cascade = CascadeType.ALL, orphanRemoval = true)
	private ProductQnaDescription description = new ProductQnaDescription();
	
	@OneToOne(mappedBy = "productQna", cascade = CascadeType.ALL, orphanRemoval = true)
	private ProductQnaReply reply;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "productQna")
	private Set<ProductQnaImage> images;
	
	@Column(name = "SECRET")
	private boolean secret;
	
	@Column(name = "QUESTION_TYPE")
	@Enumerated(value = EnumType.STRING)
	private QuestionType questionType;
	
	@Override
	public AuditSection getAuditSection() {
		// TODO Auto-generated method stub
		return this.audit;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		// TODO Auto-generated method stub
		this.audit = audit;
	}
	
}
