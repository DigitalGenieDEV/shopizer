package com.salesmanager.core.business.repositories.terms;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.salesmanager.core.model.term.CustomerTerms;
//import com.salesmanager.core.model.term.QCustomerTerms;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class CustomerTermsRepositoryImpl implements CustomerTermsRepositoryCustom{
	private final JPAQueryFactory factory;
//	private static QCustomerTerms ct = QCustomerTerms.customerTerms;
	
	@Override
	public List<CustomerTerms> findByCustomerId(Long id) {
		return new ArrayList<>();
//		return factory.selectFrom(ct)
//				.where(ct.customer.id.eq(id))
//				.fetch();
	}
}
