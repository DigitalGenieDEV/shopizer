package com.salesmanager.core.model.dept;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@Table(name = "DEPT", indexes = @Index(columnList = "DEPT_CODE, DEPT_NAME, VISIBLE"), uniqueConstraints = @UniqueConstraint(columnNames = {"ID" }))
public class Dept extends SalesManagerEntity<Integer, Dept> {
	private static final long serialVersionUID = 1L;

	@Id
	@NotEmpty
	@Column(name = "ID", unique = true, nullable = false, updatable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "DEPT_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "PARENT_ID",  nullable = false, updatable = false)
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

	@NotEmpty
	@Column(name = "REG_ID", length = 30, nullable = false, updatable = false)
	private String reg_id;

	@NotEmpty
	@Column(name = "REG_IP", length = 30, nullable = false, updatable = false)
	private String reg_ip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_DATE", nullable = false, updatable = false)
	private Date reg_date;

	@Column(name = "MOD_ID", updatable = true)
	private String mod_id;

	@Column(name = "MOD_IP", updatable = true)
	private String mod_ip;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MOD_DATE", updatable = true)
	private Date mod_date;

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

	public String getReg_id() {
		return reg_id;
	}

	public void setReg_id(String reg_id) {
		this.reg_id = reg_id;
	}

	public String getReg_ip() {
		return reg_ip;
	}

	public void setReg_ip(String reg_ip) {
		this.reg_ip = reg_ip;
	}

	public Date getReg_date() {
		return reg_date;
	}

	public void setReg_date(Date reg_date) {
		this.reg_date = reg_date;
	}

	public String getMod_id() {
		return mod_id;
	}

	public void setMod_id(String mod_id) {
		this.mod_id = mod_id;
	}

	public String getMod_ip() {
		return mod_ip;
	}

	public void setMod_ip(String mod_ip) {
		this.mod_ip = mod_ip;
	}

	public Date getMod_date() {
		return mod_date;
	}

	public void setMod_date(Date mod_date) {
		this.mod_date = mod_date;
	}

	@Override
	public String toString() {
		return "Dept [id=" + id + ", parentId=" + parentId + ", deptCode=" + deptCode + ", deptName=" + deptName
				+ ", tel=" + tel + ", content=" + content + ", ord=" + ord + ", visible=" + visible + ", reg_id="
				+ reg_id + ", reg_ip=" + reg_ip + ", reg_date=" + reg_date + ", mod_id=" + mod_id + ", mod_ip=" + mod_ip
				+ ", mod_date=" + mod_date + "]";
	}
	
	

}
