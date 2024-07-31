package com.salesmanager.shop.model.storecertificationfile;

import java.io.Serializable;

import com.salesmanager.core.model.merchant.certificationfile.CertificationFileEntity;
import com.salesmanager.shop.model.entity.ReadableAudit;
import com.salesmanager.shop.model.entity.ReadableAuditable;

public class ReadableCertificationFile extends CertificationFileEntity  implements ReadableAuditable, Serializable  {
	
	private ReadableAudit audit;
	
	@Override
	public void setReadableAudit(ReadableAudit audit) {
		// TODO Auto-generated method stub
		this.audit = audit;
	}

	@Override
	public ReadableAudit getReadableAudit() {
		// TODO Auto-generated method stub
		return this.audit;
	}

}