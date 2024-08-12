package com.salesmanager.core.business.services.catalog.product;


import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.catalog.product.SellerTextInfoRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.catalog.product.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.*;

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
