package com.salesmanager.shop.store.facade.user;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.modules.email.Email;
import com.salesmanager.core.business.services.merchant.MerchantStoreService;
import com.salesmanager.core.business.services.reference.language.LanguageService;
import com.salesmanager.core.business.services.system.EmailService;
import com.salesmanager.core.business.services.user.PermissionService;
import com.salesmanager.core.business.services.user.UserAddressService;
import com.salesmanager.core.business.services.user.UserService;
import com.salesmanager.core.model.common.CredentialsReset;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.common.GenericEntityList;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.user.*;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.constants.EmailConstants;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;
import com.salesmanager.shop.model.security.PersistableGroup;
import com.salesmanager.shop.model.security.ReadableGroup;
import com.salesmanager.shop.model.security.ReadablePermission;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.model.user.PersistableUser;
import com.salesmanager.shop.model.user.ReadableUser;
import com.salesmanager.shop.model.user.ReadableUserList;
import com.salesmanager.shop.model.user.UserPassword;
import com.salesmanager.shop.populator.user.PersistableUserPopulator;
import com.salesmanager.shop.populator.user.ReadableUserPopulator;
import com.salesmanager.shop.store.api.exception.*;
import com.salesmanager.shop.store.controller.security.facade.SecurityFacade;
import com.salesmanager.shop.store.controller.user.facade.UserAddressFacade;
import com.salesmanager.shop.store.controller.user.facade.UserFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import com.salesmanager.shop.utils.*;
import org.apache.commons.codec.language.bm.Lang;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserAddressFacadeImpl implements UserAddressFacade {


	@Autowired
	private UserAddressService userAddressService;

	@Autowired
	private UserAddressConvert userAddressConvert;


	@Override
	public List<ReadableAddress> findByUserId(Long userId, Language language) {
		List<UserAddress> userAddresses =  userAddressService.findByUserId(userId, language);
		return userAddresses.stream().map(userAddress ->
				userAddressConvert.convertPersistableAddress(userAddress, language)).collect(Collectors.toList());
	}

	@Override
	public ReadableAddress findDefaultAddressByUserId(Long userId, Language language) {
		UserAddress userAddress = userAddressService.findDefaultAddressByUserId(userId, language);
		return userAddressConvert.convertPersistableAddress(userAddress, language);
	}

	@Override
	public ReadableAddress saveOrUpdate(PersistableAddress persistableAddress) throws ServiceException {
		UserAddress userAddress = userAddressConvert.buildUserAddress(persistableAddress);
		userAddressService.saveOrUpdate(userAddress);
		return userAddressConvert.convertPersistableAddress(userAddress, null);
	}

	@Override
	public void delete(Long id) throws ServiceException {
		UserAddress userAddress = new UserAddress();
		userAddress.setId(id);
		userAddressService.delete(userAddress);

	}

	@Override
	public void updateAddressToDefault(Long userId, Long addressId) {
		userAddressService.updateDefaultAddress(userId, addressId);
	}


}
