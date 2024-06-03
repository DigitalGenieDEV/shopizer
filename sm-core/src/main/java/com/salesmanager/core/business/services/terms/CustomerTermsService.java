package com.salesmanager.core.business.services.terms;

import java.util.List;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.term.CustomerTerms;

public interface CustomerTermsService extends SalesManagerEntityService<Long, CustomerTerms>{
	List<CustomerTerms> findByCustomerId(Long id);
}
