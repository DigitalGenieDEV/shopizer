package com.salesmanager.shop.application.config;

import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.cloud.commons.httpclient.HttpClientConfiguration;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.Client;
import feign.Logger;
import feign.RequestInterceptor;

@ImportAutoConfiguration({FeignAutoConfiguration.class, HttpClientConfiguration.class})
@EnableFeignClients(basePackages = "com.salesmanager.shop.store.clients")
@Configuration
public class FeignConfig {
	@Bean
	Logger.Level feignLoggerLevel() {
		return Logger.Level.FULL;
	}
	
	@Bean
    public RequestInterceptor requestInterceptor() {
        return requestTemplate -> {
            requestTemplate.header("Content-Type", "application/json");
            requestTemplate.header("Accept", "application/json");
        };
    }
	
	@Bean
    public Client feignClient() {
        return new Client.Default(null, null);
    }
	
}
