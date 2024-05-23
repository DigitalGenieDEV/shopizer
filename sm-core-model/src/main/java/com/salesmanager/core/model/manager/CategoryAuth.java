package com.salesmanager.core.model.manager;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.common.audit2.AuditSection2;
import com.salesmanager.core.model.common.audit2.Auditable2;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit2.AuditListener2.class)
@Table(name = "CATEGORY_AUTH", indexes = @Index(columnList = "CATEGORY_ID, GRP_ID"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class CategoryAuth extends SalesManagerEntity<Integer, CategoryAuth> implements Auditable2 {

	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CATEGORY_AUTH_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "CATEGORY_ID", nullable = false, updatable = false)
	private Integer categoryId;

	@NotEmpty
	@Column(name = "GRP_ID", nullable = false, updatable = false)
	private Integer grpId;


	@Embedded
	private AuditSection2 auditSection = new AuditSection2();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public Integer getGrpId() {
		return grpId;
	}

	public void setGrpId(Integer grpId) {
		this.grpId = grpId;
	}

	public AuditSection2 getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection2 auditSection) {
		this.auditSection = auditSection;
	}

}
