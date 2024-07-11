package com.salesmanager.core.model.feature;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.common.audit.AuditListener;
import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import javax.persistence.*;

@Entity
@EntityListeners(value = AuditListener.class)
@Table(name = "PRODUCT_FEATURE",
		indexes = {
				@Index(name = "KEY_IDX", columnList = "KEY_NAME"),
				@Index(name = "PRODUCT_ID_IDX", columnList = "PRODUCT_ID")
		})
public class ProductFeature extends SalesManagerEntity<Long, ProductFeature> implements Auditable {
	private static final long serialVersionUID = 1L;

	@JsonIgnore
	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Id
	@Column(name = "PRODUCT_FEATURE_ID", unique=true, nullable=false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT",
			pkColumnValue = "PRODUCT_FEATURE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Column(name = "PRODUCT_ID", nullable = false)
	private Long productId;

	@Column(name = "KEY_NAME", nullable = false)
	private String key;

	@Column(name = "VALUE",length=256, nullable = false)
	private String value;

	@Column(name="STATUS", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private ProductFeatureStatus productFeatureStatus;

	@Column(name="SORT", nullable = true)
	private Integer sort;


	@Column(name="TYPE", nullable = true)
	@Enumerated(value = EnumType.STRING)
	private ProductFeatureType productFeatureType;


	public ProductFeatureType getProductFeatureType() {
		return productFeatureType;
	}

	public void setProductFeatureType(ProductFeatureType productFeatureType) {
		this.productFeatureType = productFeatureType;
	}

	public ProductFeatureStatus getProductFeatureStatus() {
		return productFeatureStatus;
	}

	public void setProductFeatureStatus(ProductFeatureStatus productFeatureStatus) {
		this.productFeatureStatus = productFeatureStatus;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}


	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	@Override
	public AuditSection getAuditSection() {
		return auditSection;
	}

	@Override
	public void setAuditSection(AuditSection auditSection) {
		this.auditSection = auditSection;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
}
