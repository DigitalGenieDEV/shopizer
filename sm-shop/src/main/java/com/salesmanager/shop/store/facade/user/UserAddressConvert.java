package com.salesmanager.shop.store.facade.user;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.zone.ZoneService;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.reference.zone.Zone;
import com.salesmanager.core.model.user.UserAddress;
import com.salesmanager.shop.model.references.PersistableAddress;
import com.salesmanager.shop.model.references.ReadableAddress;
import org.apache.commons.codec.language.bm.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Set;


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
                    address.setCity(city);
                }
                address.setEmail(persistableAddress.getEmail());
                address.setId(persistableAddress.getId());
                address.setUserId(persistableAddress.getUserId());
                address.setName(persistableAddress.getName());
                address.setTelephone(persistableAddress.getTelephone());
                address.setAddress(persistableAddress.getAddress());
                address.setCompany(persistableAddress.getCompany());
                address.setPostalCode(persistableAddress.getPostalCode());
            } catch (Exception e) {
                throw e;
            }
        }
        return address;
    }

    ReadableAddress convertPersistableAddress(UserAddress userAddress, Language language){
        ReadableAddress result = new ReadableAddress();
        result.setId(userAddress.getId());
        result.setUserId(userAddress.getUserId());
        result.setAddress(userAddress.getAddress());
        result.setCity(userAddress.getCity() == null? null : findDescription(userAddress.getCity().getDescriptions(), language.getId()).getName());
        result.setPostalCode(userAddress.getPostalCode());
        result.setCountry(userAddress.getCountry() == null? null : findDescription(userAddress.getCountry().getDescriptions(), language.getId()).getName());
        result.setStateProvince(userAddress.getProvince() == null? null : findDescription(userAddress.getProvince().getDescriptions(), language.getId()).getName());
        result.setTelephone(userAddress.getTelephone());
        result.setCompany(userAddress.getCompany());
        result.setEmail(userAddress.getEmail());
        result.setDefault(userAddress.isDefault());
        result.setName(userAddress.getName());

        return result;
    }


    private <T extends Description> T findDescription(List<T> descriptions, Integer langId) {
        return descriptions.stream()
                .filter(Objects::nonNull)
                .filter(description -> description.getLanguage().getId().equals(langId))
                .findFirst()
                .orElse(null);
    }

    private <T extends Description> T findDescription(Set<T> descriptions, Integer langId) {
        return descriptions.stream()
                .filter(Objects::nonNull)
                .filter(description -> description.getLanguage().getId().equals(langId))
                .findFirst()
                .orElse(null);
    }


}
