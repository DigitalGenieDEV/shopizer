package com.salesmanager.shop.store.controller.user.facade;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.user.UserAddress;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;

import java.util.List;

public interface UserAddressFacade {

    List<ReadableAddress> findByUserId(Long userId, Language language);

    ReadableAddress findDefaultAddressByUserId(Long userId, Language language);

    ReadableAddress saveOrUpdate(PersistableAddress persistableAddress) throws ServiceException;


    void delete(Long id) throws ServiceException;


    void updateDefaultAddress(Long userId, Long addressId);
}
