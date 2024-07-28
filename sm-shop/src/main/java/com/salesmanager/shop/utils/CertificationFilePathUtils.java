package com.salesmanager.shop.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.constants.Constants;

@Component
public class CertificationFilePathUtils extends AbstractCertificationFilePath {
	private String basePath = Constants.STATIC_URI;
	private String contentUrl = null;
	@Override
	public String getContextPath() {
		// TODO Auto-generated method stub
		return super.getProperties().getProperty(CONTEXT_PATH);
	}
	
	@Override
	public String getBasePath(MerchantStore store) {
		// TODO Auto-generated method stub
		return basePath;
	}
	
	@Override
	public void setBasePath(String basePath) {
		// TODO Auto-generated method stub
		this.basePath = basePath;
	}
	
	@Override
	public void setContentUrlPath(String contentUrl) {
		// TODO Auto-generated method stub
		this.contentUrl = contentUrl;
	}
	
	@Override
	public String buildCertificationFileUtils(MerchantStore store, String fileName) {
		// TODO Auto-generated method stub
		StringBuilder ceritificationFileUrl = new StringBuilder()
				.append(getBasePath(store))
				.append(Constants.FILES_URI)
				.append(Constants.SLASH)
				.append(store.getCode())
				.append(Constants.SLASH)
				.append("CERTIFICATION_INFORMATION")
				.append(Constants.SLASH);
		if(!StringUtils.isBlank(fileName)) {
			ceritificationFileUrl.append(fileName);
		}
		return ceritificationFileUrl.toString();
	}
	
}
