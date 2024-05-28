package com.salesmanager.core.business.repositories.reference.country;

import java.util.List;

import com.salesmanager.core.model.reference.country.Country;

public interface CountryRepositoryCustom {
	List<Country> listCountryZoneByLanguageAndSupported(Integer id);
}
