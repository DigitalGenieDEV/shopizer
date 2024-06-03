package com.salesmanager.core.business.repositories.terms;

import org.springframework.stereotype.Repository;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class TermsRepositoryImpl implements TermsRepositoryCustom{
	private final JPAQueryFactory factory;
}
