package com.salesmanager.shop.populator.code;

import javax.inject.Inject;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.code.CodeService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.code.Code;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.code.PersistableCode;

@Component
public class PersistableCodePopulator extends AbstractDataPopulator<PersistableCode, Code> {

	@Inject
	private CodeService codeService;

	public Code populate(PersistableCode source, Code target) throws ConversionException {

		try {

			Validate.notNull(target, "Dept target cannot be null");
			target.setId(source.getId());
			target.setParentId(source.getParentId());
			target.setCode(codeService.getCode());
			target.setCodeNameKr(source.getCodeNameKr());
			target.setCodeNameEn(source.getCodeNameEn());
			target.setCodeNameCn(source.getCodeNameCn());
			target.setCodeNameJp(source.getCodeNameJp());
			target.setCodeDesc(source.getCodeDesc());
			target.setValue(source.getValue());
			target.setVisible(source.getVisible());
			if (source.getOrd() == 0) {
				target.setOrd(codeService.getOrder(source.getParentId()));
			} else {
				target.setOrd(source.getOrd());
			}

			target.getAuditSection().setRegId(source.getUserId());
			target.getAuditSection().setRegIp(source.getUserIp());
			target.getAuditSection().setModId(source.getUserId());
			target.getAuditSection().setModIp(source.getUserIp());
			return target;

		} catch (Exception e) {
			throw new ConversionException(e);
		}

	}

	public Code populate(PersistableCode source, Code code, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	protected Code createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
