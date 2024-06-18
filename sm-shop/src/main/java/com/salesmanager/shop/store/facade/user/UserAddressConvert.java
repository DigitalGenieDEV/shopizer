package com.salesmanager.shop.store.facade.user;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.zone.ZoneService;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.zone.Zone;
import com.salesmanager.core.model.user.UserAddress;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class UserAddressConvert {

    @Autowired
    private CountryService countryService;

    @Autowired
    private ZoneService zoneService;

    UserAddress buildUserAddress(PersistableAddress persistableAddress) throws ServiceException {
        UserAddress address = new UserAddress();
        if(persistableAddress != null) {
            Country country;
            try {
                if (persistableAddress.getCountry() !=null){
                    country = countryService.getByCode(persistableAddress.getCountry());
                    address.setCountry(country);
                }
                if (persistableAddress.getStateProvince() !=null){
                    Zone province = zoneService.getByCode(persistableAddress.getStateProvince());
                    address.setProvince(province);
                }
                if (persistableAddress.getCity() !=null){
                    Zone city = zoneService.getByCode(persistableAddress.getCity());
                    address.setProvince(city);
                }
                address.setId(persistableAddress.getId());
                address.setUserId(persistableAddress.getUserId());
                address.setName(persistableAddress.getName());
                address.setTelephone(persistableAddress.getTelephone());
                address.setAddress(address.getAddress());
                address.setCompany(address.getCompany());
                address.setPostalCode(address.getPostalCode());
            } catch (ServiceException e) {
                throw e;
            }
        }
        return address;
    }

    ReadableAddress convertPersistableAddress(UserAddress userAddress){
        ReadableAddress result = new ReadableAddress();
        result.setId(userAddress.getId());
        result.setUserId(userAddress.getUserId());
        result.setAddress(userAddress.getAddress());
        result.setCity(userAddress.getCity().getName());
        result.setPostalCode(userAddress.getPostalCode());
        result.setCountry(userAddress.getCountry().getName());
        result.setStateProvince(userAddress.getProvince().getName());
        result.setTelephone(userAddress.getTelephone());
        result.setDefault(userAddress.isDefault());
        result.setName(userAddress.getName());
        return result;
    }




}
