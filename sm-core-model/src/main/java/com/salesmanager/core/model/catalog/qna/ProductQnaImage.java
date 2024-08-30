package com.salesmanager.core.model.catalog.qna;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_QNA_IMAGE")
public class ProductQnaImage extends SalesManagerEntity<Long, ProductQnaImage> implements Auditable {

	@Id
	@Column(name = "PRODUCT_QNA_IMAGE_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
	pkColumnValue = "PRODUCT_QNA_IMAGE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Embedded
	private AuditSection audit = new AuditSection();
	
	@Column(name = "PRODUCT_QNA_IMAGE_URL")
	private String imageUrl;
	
	@JsonIgnore
	@ManyToOne(targetEntity = ProductQna.class)
	@JoinColumn(name="PRODUCT_QNA_ID")
	private ProductQna productQna;

	@Override
	public AuditSection getAuditSection() {
		// TODO Auto-generated method stub
		return audit;
	}

	@Override
	public void setAuditSection(AuditSection audit) {
		// TODO Auto-generated method stub
		this.audit = audit;
	}
}
