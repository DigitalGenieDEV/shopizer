package com.salesmanager.shop.model.accesscontrol;

public class PersistableAccessControl extends ReadableAccessControl {
	private String userId = "";
	private String userIp = "";

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

}
