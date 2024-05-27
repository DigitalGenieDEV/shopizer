package com.salesmanager.core.model.dept;

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
@Table(name = "DEPT", indexes = @Index(columnList = "DEPT_CODE, DEPT_NAME, VISIBLE"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class Dept extends SalesManagerEntity<Integer, Dept> implements Auditable2 {
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "DEPT_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "PARENT_ID", nullable = false, updatable = false)
	private Integer parentId;

	@NotEmpty
	@Column(name = "DEPT_CODE", length = 30, nullable = false, updatable = false)
	private String deptCode;

	@NotEmpty
	@Column(name = "DEPT_NAME", length = 100, nullable = false)
	private String deptName;

	@Column(name = "TEL", length = 13, nullable = false)
	private String tel;

	@NotEmpty
	@Column(name = "CONTENT", length = 1000, nullable = true)
	private String content;

	@NotEmpty
	@Column(name = "ORD", nullable = false, updatable = false)
	private Integer ord;

	@NotEmpty
	@Column(name = "VISIBLE", nullable = false)
	private Integer visible;


	@Embedded
	private AuditSection2 auditSection = new AuditSection2();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getOrd() {
		return ord;
	}

	public void setOrd(Integer ord) {
		this.ord = ord;
	}

	public Integer getVisible() {
		return visible;
	}

	public void setVisible(Integer visible) {
		this.visible = visible;
	}

	public AuditSection2 getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection2 auditSection) {
		this.auditSection = auditSection;
	}

}
