package com.salesmanager.core.business.services.catalog.product;


import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.ProductRepository;
import com.salesmanager.core.business.repositories.catalog.product.SellerTextInfoRepository;
import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.business.services.catalog.product.availability.ProductAvailabilityService;
import com.salesmanager.core.business.services.catalog.product.image.ProductImageService;
import com.salesmanager.core.business.services.catalog.product.price.ProductPriceService;
import com.salesmanager.core.business.services.catalog.product.relationship.ProductRelationshipService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewService;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.utils.CatalogServiceHelper;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.*;
import com.salesmanager.core.model.catalog.product.description.ProductDescription;
import com.salesmanager.core.model.catalog.product.image.ProductImage;
import com.salesmanager.core.model.catalog.product.relationship.ProductRelationship;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.ImageContentFile;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.tax.taxclass.TaxClass;
import com.salesmanager.core.utils.LogPermUtil;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

@Service("sellerTextInfoService")
public class SellerTextInfoServiceImpl extends SalesManagerEntityServiceImpl<Long, SellerTextInfo> implements SellerTextInfoService {

	private static final Logger LOGGER = LoggerFactory.getLogger(SellerTextInfoServiceImpl.class);

	SellerTextInfoRepository sellerTextInfoRepository;



	@Inject
	public SellerTextInfoServiceImpl(SellerTextInfoRepository sellerTextInfoRepository) {
		super(sellerTextInfoRepository);
		this.sellerTextInfoRepository = sellerTextInfoRepository;
	}


	@Override
	public void saveOrUpdate(SellerTextInfo sellerTextInfo) throws ServiceException {

		if(sellerTextInfo.getId()!=null && sellerTextInfo.getId()>0) {
			super.update(sellerTextInfo);
		} else {
			super.create(sellerTextInfo);
		}
	}


	@Override
	public List<SellerTextInfo> querySellerTextInfoList(Long sellerId, String type){
		if (SellerTextType.valueOf(type) == null){
			return null;
		}
		List<SellerTextInfo> sellerTextInfos = sellerTextInfoRepository.querySellerTextInfoList(sellerId, SellerTextType.valueOf(type));
		return sellerTextInfos;
	}



}
