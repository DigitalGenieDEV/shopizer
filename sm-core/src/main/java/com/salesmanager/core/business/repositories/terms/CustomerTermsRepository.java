package com.salesmanager.core.business.repositories.terms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.term.CustomerTerms;

public interface CustomerTermsRepository extends JpaRepository<CustomerTerms, Long>{
	
}
