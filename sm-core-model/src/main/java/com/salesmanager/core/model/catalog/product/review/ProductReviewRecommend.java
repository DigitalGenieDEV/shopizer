package com.salesmanager.core.model.catalog.product.review;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.ColumnDefault;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_REVIEW_RECOMMEND", uniqueConstraints={
		@UniqueConstraint(columnNames={
				"CUSTOMERS_ID",
				"PRODUCT_REVIEW_ID"
			})
		}
)
public class ProductReviewRecommend extends SalesManagerEntity<Long, ProductReviewRecommend> implements Auditable {
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "PRODUCT_REVIEW_RECOMMEND_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
	pkColumnValue = "PRODUCT_REVIEW_RECOMMEND_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Embedded
	private AuditSection audit = new AuditSection();
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="CUSTOMERS_ID")
	private Customer customer;
	
	@JsonIgnore
	@ManyToOne(targetEntity = ProductReview.class)
	@JoinColumn(name="PRODUCT_REVIEW_ID")
	private ProductReview productReview;
	
	@Column(name = "ACTIVE")
	@ColumnDefault("true")
	private boolean active;

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
