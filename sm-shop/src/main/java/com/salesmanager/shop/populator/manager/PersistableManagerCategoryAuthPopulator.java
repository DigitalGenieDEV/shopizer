package com.salesmanager.shop.populator.manager;

import org.apache.commons.lang3.Validate;
import org.springframework.stereotype.Component;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.manager.CategoryAuth;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.manager.ManagerAuthCategoryEntity;

@Component
public class PersistableManagerCategoryAuthPopulator   extends AbstractDataPopulator<ManagerAuthCategoryEntity, CategoryAuth> {
	
	public CategoryAuth populate(ManagerAuthCategoryEntity source, CategoryAuth target)
			throws ConversionException {

		try {

			Validate.notNull(target, "CategoryAuth target cannot be null");
			
			target.setId(source.getId());
			target.setCategoryId(source.getCategoryId());
			target.setGrpId(source.getGrpId());
			target.setRegId(source.getUserId());
			target.setRegIp(source.getUserIp());
			target.setModId(source.getUserId());
			target.setModIp(source.getUserIp());
			
			return target;


		} catch(Exception e) {
			throw new ConversionException(e);
		}

	}

	@Override
	public CategoryAuth populate(ManagerAuthCategoryEntity source, CategoryAuth target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected CategoryAuth createTarget() {
		// TODO Auto-generated method stub
		return null;
	}
}
