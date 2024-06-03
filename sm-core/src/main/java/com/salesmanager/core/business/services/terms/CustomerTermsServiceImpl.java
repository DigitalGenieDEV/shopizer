package com.salesmanager.core.business.services.terms;

import java.util.List;

import org.springframework.stereotype.Service;

import com.salesmanager.core.business.repositories.terms.CustomerTermsRepository;
import com.salesmanager.core.business.repositories.terms.CustomerTermsRepositoryCustom;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.term.CustomerTerms;

@Service("customerTermsService")
public class CustomerTermsServiceImpl extends SalesManagerEntityServiceImpl<Long, CustomerTerms>
		implements CustomerTermsService {

	private CustomerTermsRepositoryCustom customRepository;
	private CustomerTermsRepository repository;

	public CustomerTermsServiceImpl(
			CustomerTermsRepository repository,
			CustomerTermsRepositoryCustom customRepository
	) {
		super(repository);
		this.customRepository = customRepository;
		this.repository = repository;
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<CustomerTerms> findByCustomerId(Long id) {
		// TODO Auto-generated method stub
		return customRepository.findByCustomerId(id);
	}
}
