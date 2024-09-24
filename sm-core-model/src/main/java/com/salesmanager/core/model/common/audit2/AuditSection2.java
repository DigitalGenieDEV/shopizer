package com.salesmanager.core.model.common.audit2;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.utils.CloneUtils;

@Embeddable
public class AuditSection2 implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "REG_ID", length = 96, nullable = false, updatable = false , columnDefinition = "varchar(11) not null comment '등록ID'")
	private String regId;

	@NotEmpty
	@Column(name = "REG_IP", length = 30, nullable = false, updatable = false, columnDefinition = "varchar(30) not null comment '등록IP'")
	private String regIp;

	@Column(name = "MOD_ID",  length = 96, updatable = true, columnDefinition = "varchar(11) not null comment '수정ID'")
	private String modId;

	@Column(name = "MOD_IP", updatable = true, columnDefinition = "varchar(30) not null comment '수정IP'")
	private String modIp;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_DATE", nullable = false, updatable = false, columnDefinition = "varchar(30) not null comment '등록일자'")
	private Date regDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MOD_DATE", updatable = true, columnDefinition = "varchar(30) not null comment '수정일자'")
	private Date modDate;

	public AuditSection2() {
	}

	public String getRegId() {
		return regId;
	}

	public void setRegId(String regId) {
		this.regId = regId;
	}

	public String getRegIp() {
		return regIp;
	}

	public void setRegIp(String regIp) {
		this.regIp = regIp;
	}

	public String getModId() {
		return modId;
	}

	public void setModId(String modId) {
		this.modId = modId;
	}

	public String getModIp() {
		return modIp;
	}

	public void setModIp(String modIp) {
		this.modIp = modIp;
	}

	public Date getRegDate() {
		return CloneUtils.clone(regDate);
	}

	public void setRegDate(Date regDate) {
		this.regDate = CloneUtils.clone(regDate);
	}

	public Date getModDate() {
		return CloneUtils.clone(modDate);
	}

	public void setModDate(Date modDate) {
		this.modDate = CloneUtils.clone(modDate);
	}

}
