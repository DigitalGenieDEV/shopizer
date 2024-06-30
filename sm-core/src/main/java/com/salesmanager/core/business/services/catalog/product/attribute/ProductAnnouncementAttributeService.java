package com.salesmanager.core.business.services.catalog.product.attribute;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductAnnouncementAttributeService extends
		SalesManagerEntityService<Long, ProductAnnouncementAttribute> {

	ProductAnnouncementAttribute saveOrUpdate(ProductAnnouncementAttribute productAttribute)
			throws ServiceException;

	Boolean existsByProductId(Long productId);

	void deleteByProductId(Long productId);


	List<ProductAnnouncementAttribute> getByIdList(List<Long> idList);

	List<ProductAnnouncementAttribute> getByProductId(Long productId);

}
