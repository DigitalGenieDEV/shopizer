package com.salesmanager.core.model.code;

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
@Table(name = "COMMON_CODE", indexes = @Index(columnList = "CODE, VISIBLE"), uniqueConstraints = @UniqueConstraint(columnNames = {
		"ID" }))
public class Code extends SalesManagerEntity<Integer, Code> implements Auditable2 {
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CODE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "PARENT_ID", nullable = false, updatable = false)
	private Integer parentId;

	@NotEmpty
	@Column(name = "CODE", length = 30, nullable = false, updatable = false)
	private String code;

	@NotEmpty
	@Column(name = "CODE_NAME_KR", length = 50, nullable = false)
	private String codeNameKr;

	@Column(name = "CODE_NAME_EN", length = 100, nullable = false)
	private String codeNameEn;

	@Column(name = "CODE_NAME_CN", length = 50, nullable = false)
	private String codeNameCn;

	@Column(name = "CODE_NAME_JP", length = 50, nullable = false)
	private String codeNameJp;

	@Column(name = "VALUE", length = 50, nullable = false)
	private String value;

	@Column(name = "CODE_DESC", length = 1000, nullable = false)
	private String codeDesc;

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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeNameKr() {
		return codeNameKr;
	}

	public void setCodeNameKr(String codeNameKr) {
		this.codeNameKr = codeNameKr;
	}

	public String getCodeNameEn() {
		return codeNameEn;
	}

	public void setCodeNameEn(String codeNameEn) {
		this.codeNameEn = codeNameEn;
	}

	public String getCodeNameCn() {
		return codeNameCn;
	}

	public void setCodeNameCn(String codeNameCn) {
		this.codeNameCn = codeNameCn;
	}

	public String getCodeNameJp() {
		return codeNameJp;
	}

	public void setCodeNameJp(String codeNameJp) {
		this.codeNameJp = codeNameJp;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
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
