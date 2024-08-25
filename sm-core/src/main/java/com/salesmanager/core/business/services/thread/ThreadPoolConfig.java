package com.salesmanager.core.business.services.thread;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ThreadPoolConfig {

    @Bean
    public ThreadPoolManager threadPoolManager() {
        return new ThreadPoolManager(
                100,
                1000
        );
    }
}