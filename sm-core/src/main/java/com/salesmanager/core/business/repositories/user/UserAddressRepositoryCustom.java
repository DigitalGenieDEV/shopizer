package com.salesmanager.core.business.repositories.user;

import com.salesmanager.core.model.user.PermissionCriteria;
import com.salesmanager.core.model.user.PermissionList;
import com.salesmanager.core.model.user.UserAddress;

import java.util.List;


public interface UserAddressRepositoryCustom {

	List<UserAddress> findByUserId(Long userId, Integer langId);



	UserAddress findDefaultAddressByUserId( Long userId);

}
