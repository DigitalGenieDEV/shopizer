package com.salesmanager.shop.model.privacy;

public class PersistablePrivacy extends PrivacyEntity {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
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
