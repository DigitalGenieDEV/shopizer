package com.salesmanager.shop.populator.manager;

import javax.inject.Inject;
import javax.inject.Named;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.manager.Manager;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.manager.PersistableManager;

@Component
public class PersistableManagerPopulator extends AbstractDataPopulator<PersistableManager, Manager> {
	
	  @Inject
	  @Named("passwordEncoder")
	  private PasswordEncoder passwordEncoder;
	
	public Manager populate(PersistableManager source, Manager target)
			throws ConversionException {

		try {

			Validate.notNull(target, "Manager target cannot be null");
			
			target.setEmplId(source.getEmplId());
			target.setAdminName(source.getAdminName());
			
			if(!StringUtils.isBlank(source.getPassword())) {
		      target.setAdminPassword(passwordEncoder.encode(source.getPassword()));
		    }
	
			target.setAdminEmail(source.getAdminEmail());
			target.setActive(source.isActive());
			target.setDeptId(source.getDeptId());
			target.setDeptName(source.getDeptName());
			target.setPositionId(source.getPositionId());
			target.setPosition(source.getPosition());
			target.setTelId1(source.getTelId1());
			target.setTel1(source.getTel1());
			target.setTelId2(source.getTelId2());
			target.setTel2(source.getTel2());
			target.setFaxId(source.getFaxId());
			target.setFax(source.getFax());
			target.setGrpId(source.getGrpId());
			target.setGrpName(source.getGrpName());
			target.setContent(source.getContent());
			target.getAuditSection().setRegId(source.getUserId());
			target.getAuditSection().setRegIp(source.getUserIp());
			target.getAuditSection().setModId(source.getUserId());
			target.getAuditSection().setModIp(source.getUserIp());
			target.setMerchantId(1);
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public Manager populate(PersistableManager source, Manager target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Manager createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
