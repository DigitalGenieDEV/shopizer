package com.salesmanager.core.business.services.terms;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.terms.TermsRepository;
import com.salesmanager.core.business.repositories.terms.TermsRepositoryCustom;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.term.Terms;

@Service("termsService")
public class TermsServiceImpl extends SalesManagerEntityServiceImpl<Long, Terms> implements TermsService {

	private TermsRepository termsRepository;
	private TermsRepositoryCustom termsRepositoryCustom;
	
	public TermsServiceImpl(
			TermsRepository termsRepository,
			TermsRepositoryCustom termsRepositoryCustom
			) {
		super(termsRepository);
		this.termsRepository = termsRepository;
		this.termsRepositoryCustom = termsRepositoryCustom;
	}

	@Override
	public Terms createTerms(Terms term) {
		return termsRepository.save(term);
	}
	
	@Override
	public List<Terms> findAll() {
		return termsRepository.findAll();
	}
}
