package com.salesmanager.core.business.services.terms;

import java.util.List;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.term.Terms;

public interface TermsService extends SalesManagerEntityService<Long, Terms>{
	Terms createTerms(Terms term);
	List<Terms> findAll();
}
