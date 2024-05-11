package com.salesmanager.core.model.common.audit2;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.salesmanager.core.utils.CloneUtils;

@Embeddable
public class AuditSection2 implements Serializable {

	private static final long serialVersionUID = 1L;


	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_DATE", nullable = false, updatable = false)
	private Date regDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MOD_DATE", updatable = true)
	private Date modDate;

	public AuditSection2() {
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
