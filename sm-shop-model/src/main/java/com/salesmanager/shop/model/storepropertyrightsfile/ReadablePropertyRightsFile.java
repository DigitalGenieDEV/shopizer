package com.salesmanager.shop.model.storepropertyrightsfile;

import java.io.Serializable;

import com.salesmanager.core.model.merchant.propertyrights.PropertyRightsFileEntity;
import com.salesmanager.shop.model.entity.ReadableAudit;
import com.salesmanager.shop.model.entity.ReadableAuditable;

public class ReadablePropertyRightsFile extends PropertyRightsFileEntity  implements ReadableAuditable, Serializable  {
	
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
