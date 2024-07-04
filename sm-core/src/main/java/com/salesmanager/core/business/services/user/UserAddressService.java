package com.salesmanager.core.business.services.user;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.user.UserAddress;

import java.util.List;


public interface UserAddressService extends SalesManagerEntityService<Long, UserAddress> {

    List<UserAddress> findByUserId(Long userId, Language language);

    UserAddress findDefaultAddressByUserId(Long userId);

    void saveOrUpdate(UserAddress userAddress);

    void updateDefaultAddress(Long userId, Long addressId);
}
