package com.salesmanager.shop.utils;

import com.salesmanager.core.model.merchant.MerchantStore;

public interface CertificationFilePath {
	
	/**
	 * Context path configured in shopizer-properties.xml
	 * @return
	 */
	public String getContextPath();
	
	public String getBasePath(MerchantStore store);
	
	/**
	 * Build library files of store path that can be used by image servlet
	 * utility for getting the physical image
	 * @param store
	 * @param imageName
	 * @return
	 */
	public String buildCertificationFileUtils(MerchantStore store, String fileName);
	
}
