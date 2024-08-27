package com.salesmanager.shop.populator.catalog;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.Validate;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaDescription;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductQna;
import com.salesmanager.shop.utils.DateUtil;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PersistableProductQnaPopulator extends AbstractDataPopulator<PersistableProductQna, ProductQna> {
	
	private CustomerService customerService;
	private ProductService productService;
	private LanguageService languageService;
	
	@Override
	public ProductQna populate(PersistableProductQna source, ProductQna target, MerchantStore store, Language language)
			throws ConversionException {
		// TODO Auto-generated method stub
		
		Validate.notNull(customerService,"customerService cannot be null");
		Validate.notNull(productService,"productService cannot be null");
		Validate.notNull(languageService,"languageService cannot be null");
		
		try {
			if(target == null) {
				target = new ProductQna();
			}
			
			Customer customer = customerService.getById(source.getCustomerId());
			//check if customer belongs to store
//			if(customer == null || customer.getMerchantStore().getId().intValue() != store.getId().intValue()) {
//				throw new ConversionException("Invalid customer id for the given store");
//			}
			
			if(source.getDate() == null) {
				String date = DateUtil.formatDate(new Date());
				source.setDate(date);
			}
			
			target.setQnaDate(DateUtil.getDate(source.getDate()));
			target.setCustomer(customer);
			
			Product product = productService.getById(source.getProductId());
			
			if(product == null || product.getMerchantStore().getId().intValue() != store.getId().intValue()) {
				throw new ConversionException("Invalid product id for the given store");
			}
			
			target.setProduct(product);
			
			Language lang = languageService.getByCode(language.getCode());
			if(lang ==null) {
				throw new ConversionException("Invalid language code, use iso codes (en, fr ...)");
			}
			
			ProductQnaDescription description = new ProductQnaDescription();
			description.setTitle(source.getTitle());
			description.setDescription(source.getDescription());
			description.setLanguage(lang);
			description.setName("-");
			description.setProductQna(target);
			target.setDescription(description);
			
			target.setSecret(source.isSecret());
			target.setQuestionType(QuestionType.valueOf(source.getQuestionType()));
			
			return target;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionException("Cannot populate ProductReview", e);
		}
	}

	@Override
	protected ProductQna createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

}
