package com.salesmanager.shop.populator.ipSafetyCenter;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.iprsafecenter.IpSafetyCenter;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.ipSafetyCenter.PersistableIpSafetyCenter;

@Component
public class PersistableIpsafetyCenterPopulator extends AbstractDataPopulator<PersistableIpSafetyCenter, IpSafetyCenter> {
	public IpSafetyCenter populate(PersistableIpSafetyCenter source, IpSafetyCenter target)
			throws ConversionException {

		try {
	
			
			Validate.notNull(target, "IpSafetyCenter target cannot be null");
			target.setId(source.getId());
			target.setReportType(source.getReportType());
		
			target.setTitle(source.getTitle());
			target.setRegName(source.getRegName());
			target.setState(source.getState());
			target.setTel(source.getTel());
			target.setEmail(source.getEmail());
			target.setOpen(source.getOpen());
			target.setImage(source.getImage());
			target.setUrl(source.getUrl());
			target.setReason(source.getReason());
			target.setContent(source.getContent());
			target.setIprNation(source.getIprNation());
			target.setIprTitle(source.getIprTitle());
			target.setIprType(source.getIprType());
			target.setIprNo(source.getIprNo());
			target.setIprOwner(source.getIprOwner());
			target.setIprApplyDate(source.getIprApplyDate());
			target.setIprRegDate(source.getIprRegDate());
			target.setIprCompany(source.getIprCompany());
		
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
	public IpSafetyCenter populate(PersistableIpSafetyCenter source, IpSafetyCenter target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IpSafetyCenter createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
