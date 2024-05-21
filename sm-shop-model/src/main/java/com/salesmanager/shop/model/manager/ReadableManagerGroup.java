package com.salesmanager.shop.model.manager;

public class ReadableManagerGroup {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private int id;
	private String grpName = "";
	private int cnt = 0;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public int getCnt() {
		return cnt;
	}

	public void setCnt(int cnt) {
		this.cnt = cnt;
	}

}
