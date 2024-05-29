package com.salesmanager.core.business.repositories.reference.country;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salesmanager.core.model.reference.country.Country;
//import com.salesmanager.core.model.reference.country.QCountry;
//import com.salesmanager.core.model.reference.country.QCountryDescription;
//import com.salesmanager.core.model.reference.zone.QZone;
//import com.salesmanager.core.model.reference.zone.QZoneDescription;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CountryRepositoryImpl implements CountryRepositoryCustom {
	private final JPAQueryFactory factory;
//	private static QCountry c = QCountry.country;
//	private static QCountryDescription cd = QCountryDescription.countryDescription;
//	private static QZone cz = QZone.zone;
//	private static QZoneDescription czd = QZoneDescription.zoneDescription;

	@Override
	public List<Country> listCountryZoneByLanguageAndSupported(Integer id) {

		return new ArrayList<>();
//		return factory
//				.selectFrom(c)
//				.distinct()
//				.leftJoin(c.descriptions, cd)
//				.fetchJoin()
//				.leftJoin(c.zones, cz)
//				.fetchJoin()
//				.leftJoin(cz.descriptions, czd)
//				.fetchJoin()
//				.where(c.supported.isTrue().and(cd.language.id.eq(id)))
//				.fetch();
	}
}
