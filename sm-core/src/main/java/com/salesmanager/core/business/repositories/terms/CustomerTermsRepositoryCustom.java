package com.salesmanager.core.business.repositories.terms;

import java.util.List;

import com.salesmanager.core.model.term.CustomerTerms;

public interface CustomerTermsRepositoryCustom {
	List<CustomerTerms> findByCustomerId(Long id);
}
