package com.salesmanager.core.model.manager;

public interface ReadableManager {
	Long getId();
	String getAdminName();
	String getAdminEmail();
	String getAdminPassword();
	String getEmplId();
	Integer getGrpId();
	String getGrpName();
	String getCode();
	Integer getDeptId();
	String getDeptName();
	Integer getPositionId();
	String getPosition();
}
