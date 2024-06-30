package com.salesmanager.core.business.services.catalog.product.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.attribute.PageableProductAnnouncementAttributeRepository;
import com.salesmanager.core.business.repositories.catalog.product.attribute.ProductAnnouncementAttributeRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("productAnnouncementAttributeService")
public class ProductAnnouncementAttributeServiceImpl extends SalesManagerEntityServiceImpl<Long, ProductAnnouncementAttribute>
		implements ProductAnnouncementAttributeService {

	private ProductAnnouncementAttributeRepository productAnnouncementAttributeRepository;
	@Autowired
	private PageableProductAnnouncementAttributeRepository pageableProductAnnouncementAttributeRepository;

	@Inject
	public ProductAnnouncementAttributeServiceImpl(ProductAnnouncementAttributeRepository ProductAnnouncementAttributeRepository) {
		super(ProductAnnouncementAttributeRepository);
		this.productAnnouncementAttributeRepository = ProductAnnouncementAttributeRepository;
	}

	@Override
	public ProductAnnouncementAttribute getById(Long id) {
		ProductAnnouncementAttribute ProductAnnouncementAttribute = productAnnouncementAttributeRepository.findOne(id);
		return ProductAnnouncementAttribute;

	}

	@Override
	public List<ProductAnnouncementAttribute> getByIdList(List<Long> idList) {
		List<ProductAnnouncementAttribute> ProductAnnouncementAttribute = productAnnouncementAttributeRepository.findByIds(idList);
		return ProductAnnouncementAttribute;
	}

	@Override
	public List<ProductAnnouncementAttribute> getByProductId(Long productId) {
		List<ProductAnnouncementAttribute> ProductAnnouncementAttribute = productAnnouncementAttributeRepository.findByProductId(productId);
		return ProductAnnouncementAttribute;
	}

	@Override
	public Boolean existsByProductId(Long productId) {
		return productAnnouncementAttributeRepository.existsByProductId(productId);
	}

	@Override
	public void deleteByProductId(Long productId) {
		productAnnouncementAttributeRepository.deleteByProductId(productId);
	}



	@Override
	public ProductAnnouncementAttribute saveOrUpdate(ProductAnnouncementAttribute ProductAnnouncementAttribute) throws ServiceException {
		ProductAnnouncementAttribute = productAnnouncementAttributeRepository.save(ProductAnnouncementAttribute);
		return ProductAnnouncementAttribute;

	}

	@Override
	public void delete(ProductAnnouncementAttribute attribute) throws ServiceException {
		attribute = this.getById(attribute.getId());
		super.delete(attribute);

	}



}
