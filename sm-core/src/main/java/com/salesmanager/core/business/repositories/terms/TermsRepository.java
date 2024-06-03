package com.salesmanager.core.business.repositories.terms;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.term.Terms;

public interface TermsRepository extends JpaRepository<Terms, Long>{
	
}
