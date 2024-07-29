package com.salesmanager.shop.application.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.salesmanager.shop.utils.CertificationFilePath;
import com.salesmanager.shop.utils.CertificationFilePathUtils;

@Configuration
public class LocationCertificationFileConfig {

	@Value("${config.cms.contentUrl}")
	private String contentUrl;
	  
	@Value("${config.cms.method}")
	private String method;
	  
	@Value("${config.cms.static.path}")
	private String staticPath;
	
	@Bean
	public CertificationFilePath certificationFile() {
		CertificationFilePathUtils certificationFilePathUtils = new CertificationFilePathUtils();
		certificationFilePathUtils.setBasePath(contentUrl);
		certificationFilePathUtils.setContentUrlPath(contentUrl);
		return certificationFilePathUtils;
	}
}
