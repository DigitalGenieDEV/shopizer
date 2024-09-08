package com.salesmanager.core.business.repositories.exchangeRate;

import com.salesmanager.core.model.catalog.product.ExchangeRate;
import com.salesmanager.core.model.catalog.product.ExchangeRatePOJO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {


	@Query("SELECT er.baseCurrency AS baseCurrency, er.targetCurrency AS targetCurrency, er.id AS id, er.auditSection AS auditSection, er.rate AS rate " +
			"FROM ExchangeRate er " +
			"WHERE (er.baseCurrency = ?1 and er.targetCurrency = ?2)")
	ExchangeRatePOJO findExchangeRate(String baseCurrency, String targetCurrency);
}
