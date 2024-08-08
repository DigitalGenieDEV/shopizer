package com.salesmanager.core.business.services.merchant.image;

import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.store.image.MerchantStoreImageRepository;
import com.salesmanager.core.model.merchant.image.MerchantStoreImage;

@Service("merchantStoreImageService")
public class MerchantStoreImageServiceImpl implements MerchantStoreImageService {
	
	@Inject
	private MerchantStoreImageRepository storeImageRepository;
	
	@Override
	public void save(MerchantStoreImage image) {
		// TODO Auto-generated method stub
		storeImageRepository.save(image);
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		storeImageRepository.deleteById(id);
	}

	@Override
	public void deleteByStoreId(Integer storeId) {
		// TODO Auto-generated method stub
		storeImageRepository.deleteByStoreId(storeId);
	}

}
