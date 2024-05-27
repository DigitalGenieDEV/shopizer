package com.salesmanager.core.model.accesscontrol;

public interface ReadAccessControl {
	Integer getId();
	Integer getVisible();
	String getTitle();
	String getIpAddress();
	String getTargetSite();
	String getRegType();
}
