package com.salesmanager.core.model.manager;

public interface ReadCategoryAuth {
	Integer getId();
	Integer getParentId();
	Integer getDepth();
	String getCategoryName();
	String getCategoryPathName();
	String getLineage();
	String getCode();

}
