package com.salesmanager.shop.utils;

import java.util.Properties;

import javax.annotation.Resource;

import com.salesmanager.core.model.merchant.MerchantStore;

public abstract class AbstractCertificationFilePath implements CertificationFilePath {
	
	public abstract String getBasePath(MerchantStore store);

	public abstract void setBasePath(String basePath);
	
	public abstract void setContentUrlPath(String contentUrl);
	
	protected static final String CONTEXT_PATH = "CONTEXT_PATH";
	
	public @Resource(name="shopizer-properties") Properties properties = new Properties();//shopizer-properties
	
	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}
	
	
}
