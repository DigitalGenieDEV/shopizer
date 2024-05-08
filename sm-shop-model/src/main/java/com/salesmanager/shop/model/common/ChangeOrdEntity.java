package com.salesmanager.shop.model.common;

public class ChangeOrdEntity {
	private int id = 0;
	private int parentId = 0;
	private int changeOrd = 0;
	private String userId = "";

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public int getChangeOrd() {
		return changeOrd;
	}

	public void setChangeOrd(int changeOrd) {
		this.changeOrd = changeOrd;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
