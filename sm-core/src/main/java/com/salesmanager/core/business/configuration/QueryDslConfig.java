package com.salesmanager.core.business.configuration;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class QueryDslConfig {
	@PersistenceContext
    private EntityManager entityManager;
	
	@Bean
    public JPAQueryFactory jpaQueryFactory(EntityManager em){		
    	//쿼리를 작성하는 JPAQueryFactory에 EntityManager를 넘겨 사용한다.
        return new JPAQueryFactory(em);
    }
}
