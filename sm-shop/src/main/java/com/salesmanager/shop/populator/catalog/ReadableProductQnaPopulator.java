package com.salesmanager.shop.populator.catalog;

import java.text.SimpleDateFormat;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.qna.ProductQna;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaDescription;
import com.salesmanager.core.model.catalog.product.qna.ProductQnaReply;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductQna;
import com.salesmanager.shop.model.catalog.product.ReadableProductQnaDescription;
import com.salesmanager.shop.model.catalog.product.ReadableProductQnaReply;
import com.salesmanager.shop.model.customer.ReadableCustomer;
import com.salesmanager.shop.populator.customer.ReadableCustomerPopulator;
import com.salesmanager.shop.utils.ImageFilePath;

public class ReadableProductQnaPopulator extends AbstractDataPopulator<ProductQna, ReadableProductQna> {

	private PricingService pricingService;
	private ImageFilePath imageUtils;
	
	@Override
	public ReadableProductQna populate(ProductQna source, ReadableProductQna target, MerchantStore store,
			Language language) throws ConversionException {
		// TODO Auto-generated method stub
		try {
			if(target == null) {
				target = new ReadableProductQna();
			}
			
			target.setId(source.getId());
			target.setAuditSection(source.getAuditSection());
			
			Customer customer = source.getCustomer();
			ReadableCustomerPopulator customerPopulator = new ReadableCustomerPopulator();
			ReadableCustomer readableCustomer = new ReadableCustomer();
			customerPopulator.populate(customer, readableCustomer, store, language);
			target.setCustomer(readableCustomer);
			
//			Product product = source.getProduct();
//			ReadableProduct readableProduct = new ReadableProduct();
//			ReadableProductPopulator productPopulator = new ReadableProductPopulator();
//			productPopulator.setPricingService(pricingService);;
//			productPopulator.setimageUtils(imageUtils);
//			productPopulator.populate(product, readableProduct, store, language);
//			target.setProduct(readableProduct);
			
			ReadableProductQnaDescription readableDescription = populateDescription(source.getDescription());
			target.setQuestion(readableDescription);
			if(source.getReply() != null) {
				ReadableProductQnaReply readableReply = populateReply(source.getReply());
				target.setAnswer(readableReply);
			}
			
			target.setSecret(source.isSecret());
			target.setCategory(source.getQuestionType().name());
			
			return target;
			
		} catch (Exception e) {
			// TODO: handle exception
			throw new ConversionException(e);
		}
	}

	private ReadableProductQnaReply populateReply(ProductQnaReply source) {
		// TODO Auto-generated method stub
		ReadableProductQnaReply target = new ReadableProductQnaReply();
		target.setId(source.getId());
		target.setTitle(source.getTitle());
		target.setContent(source.getDescription());
		target.setDate((new SimpleDateFormat("yyyy.MM.dd")).format(source.getAuditSection().getDateCreated()));
		return target;
	}

	private ReadableProductQnaDescription populateDescription(ProductQnaDescription source) {
		// TODO Auto-generated method stub
		ReadableProductQnaDescription target = new ReadableProductQnaDescription();
		target.setId(source.getId());
		target.setTitle(source.getTitle());
		target.setContent(source.getDescription());
		target.setDate((new SimpleDateFormat("yyyy.MM.dd")).format(source.getAuditSection().getDateCreated()));
		return target;
	}

	@Override
	protected ReadableProductQna createTarget() {
		// TODO Auto-generated method stub
		return null;
	}

	public PricingService getPricingService() {
		return pricingService;
	}

	public void setPricingService(PricingService pricingService) {
		this.pricingService = pricingService;
	}

	public ImageFilePath getImageUtils() {
		return imageUtils;
	}

	public void setImageUtils(ImageFilePath imageUtils) {
		this.imageUtils = imageUtils;
	}
	
}
