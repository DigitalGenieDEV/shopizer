package com.salesmanager.shop.populator.dept;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.dept.DeptService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.dept.Dept;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.dept.PersistableDept;

@Component
public class PersistableDeptPopulator extends AbstractDataPopulator<PersistableDept, Dept> {
	
	@Inject
	private DeptService deptService;
	
	public Dept populate(PersistableDept source, Dept target)
			throws ConversionException {

		try {

			Validate.notNull(target, "Dept target cannot be null");
			target.setId(source.getId());
			target.setParentId(source.getParentId());
			target.setDeptCode(deptService.getDeptCode());
			target.setDeptName(source.getDeptName());
			target.setTel(source.getTel());
			if(source.getOrd() == 0) {
				target.setOrd(deptService.getOrder(source.getParentId()));
			}else {
				target.setOrd(source.getOrd());
			}
			target.setContent(source.getContent());
			target.setVisible(source.getVisible());
			target.getAuditSection().setRegId(source.getUserId());
			target.getAuditSection().setRegIp(source.getUserIp());
			target.getAuditSection().setModId(source.getUserId());
			target.getAuditSection().setModIp(source.getUserIp());
		
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public Dept populate(PersistableDept source, Dept target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Dept createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
