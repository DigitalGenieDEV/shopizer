package com.salesmanager.core.business.services.user;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.user.PageableUserRepository;
import com.salesmanager.core.business.repositories.user.UserAddressRepository;
import com.salesmanager.core.business.repositories.user.UserRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.user.User;
import com.salesmanager.core.model.user.UserAddress;
import com.salesmanager.core.model.user.UserCriteria;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.helper.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;


@Service
public class UserAddressServiceImpl extends SalesManagerEntityServiceImpl<Long, UserAddress> implements UserAddressService {

	private UserAddressRepository userAddressRepository;



	@Inject
	public UserAddressServiceImpl(UserAddressRepository userAddressRepository) {
		super(userAddressRepository);
		this.userAddressRepository = userAddressRepository;
	}

	@Override
	public List<UserAddress> findByUserId(Long userId, Language language) {
		return userAddressRepository.findByUserId(userId, language.getId());
	}

	@Override
	public UserAddress findDefaultAddressByUserId(Long userId) {
		return userAddressRepository.findDefaultAddressByUserId(userId);
	}

	@Override
	public void delete(UserAddress userAddress) throws ServiceException {
		UserAddress u = this.getById(userAddress.getId());
		super.delete(u);
	}

	@Override
	@Transactional
	public void saveOrUpdate(UserAddress userAddress) {
		List<UserAddress> userAddresses = userAddressRepository.findByUserId(userAddress.getUserId());
		if (CollectionUtils.isEmpty(userAddresses)){
			userAddress.setDefault(true);
		}else {
			boolean isHasDefault = false;
			for (UserAddress address : userAddresses){
				if(address.isDefault()){
					isHasDefault = true;
				}
			}
			if (isHasDefault){
				userAddress.setDefault(false);
			}
		}
		userAddressRepository.save(userAddress);
	}

	@Transactional
	@Override
	public void updateDefaultAddress(Long userId, Long addressId) {
		userAddressRepository.resetDefaultAddress(userId);
		userAddressRepository.setDefaultAddress(addressId, userId);
	}
}
