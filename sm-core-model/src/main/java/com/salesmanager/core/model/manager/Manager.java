package com.salesmanager.core.model.manager;

import java.util.Date;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.common.CredentialsReset;
import com.salesmanager.core.model.common.audit2.AuditSection2;
import com.salesmanager.core.model.common.audit2.Auditable2;
import com.salesmanager.core.model.generic.SalesManagerEntity;

@Entity
@EntityListeners(value = com.salesmanager.core.model.common.audit2.AuditListener2.class)
@Table(name = "MANAGER", indexes = { @Index(name = "MANAGER_NAME_IDX", columnList = "ADMIN_NAME") })
public class Manager extends SalesManagerEntity<Long, Manager> implements Auditable2 {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "USER_ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "MANAGER_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@NotEmpty
	@Column(name = "EMPL_ID", nullable = false, length = 12, updatable = false)
	private String emplId;

	@NotEmpty
	@Column(name = "ADMIN_NAME", length = 100)
	private String adminName;

	@NotEmpty
	@Email
	@Column(name = "ADMIN_EMAIL", nullable = false, length = 100)
	private String adminEmail;

	@NotEmpty
	@Column(name = "ADMIN_PASSWORD", length = 60)
	private String adminPassword;

	@Column(name = "TEL_ID1")
	private int telId1;

	@Column(name = "TEL1")
	private String tel1;

	@Column(name = "TEL_ID2")
	private int telId2;

	@Column(name = "TEL2")
	private String tel2;

	@Column(name = "FAX_ID")
	private int faxId;

	@Column(name = "FAX")
	private String fax;

	@Column(name = "CONTENT", length = 500)
	private String content;

	@Column(name = "ACTIVE")
	private boolean active = true;

	@Column(name = "LOGIN_FAIL_COUNT")
	private int loginFailCount = 0;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LOGIN_DATE")
	private Date loginDate;

	@NotEmpty
	@Column(name = "POSITION_ID")
	private int positionId;

	@Column(name = "POSITION", length = 100)
	private String position;

	@NotEmpty
	@Column(name = "DEPT_ID")
	private int deptId;

	@Column(name = "DEPT_NAME", length = 100)
	private String deptName;

	@NotEmpty
	@Column(name = "GRP_ID")
	private int grpId;

	@Column(name = "GRP_NAME", length = 100)
	private String grpName;

	@Embedded
	private CredentialsReset credentialsResetRequest = null;

	public CredentialsReset getCredentialsResetRequest() {
		return credentialsResetRequest;
	}

	public void setCredentialsResetRequest(CredentialsReset credentialsResetRequest) {
		this.credentialsResetRequest = credentialsResetRequest;
	}

	@Embedded
	private AuditSection2 auditSection = new AuditSection2();

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmplId() {
		return emplId;
	}

	public void setEmplId(String emplId) {
		this.emplId = emplId;
	}

	public String getAdminName() {
		return adminName;
	}

	public void setAdminName(String adminName) {
		this.adminName = adminName;
	}

	public String getAdminEmail() {
		return adminEmail;
	}

	public void setAdminEmail(String adminEmail) {
		this.adminEmail = adminEmail;
	}

	public String getAdminPassword() {
		return adminPassword;
	}

	public void setAdminPassword(String adminPassword) {
		this.adminPassword = adminPassword;
	}

	public int getTelId1() {
		return telId1;
	}

	public void setTelId1(int telId1) {
		this.telId1 = telId1;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public int getTelId2() {
		return telId2;
	}

	public void setTelId2(int telId2) {
		this.telId2 = telId2;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public int getFaxId() {
		return faxId;
	}

	public void setFaxId(int faxId) {
		this.faxId = faxId;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getLoginFailCount() {
		return loginFailCount;
	}

	public void setLoginFailCount(int loginFailCount) {
		this.loginFailCount = loginFailCount;
	}

	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public int getGrpId() {
		return grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public AuditSection2 getAuditSection() {
		return auditSection;
	}

	public void setAuditSection(AuditSection2 auditSection) {
		this.auditSection = auditSection;
	}

}
