package com.salesmanager.shop.model.adminmenu;

public class PersistableAdminMenu {
	private int id;
	private int parentId;
	private String menuName = "";
	private String menuNameEn = "";
	private String menuNameCn = "";
	private String menuNameJp = "";
	private String menuDesc = "";
	private String menuUrl = "";
	private String apiUrl = "";
	private int visible;
	private int ord;
	private String userId = "";
	private String userIp = "";

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

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuNameEn() {
		return menuNameEn;
	}

	public void setMenuNameEn(String menuNameEn) {
		this.menuNameEn = menuNameEn;
	}

	public String getMenuNameCn() {
		return menuNameCn;
	}

	public void setMenuNameCn(String menuNameCn) {
		this.menuNameCn = menuNameCn;
	}

	public String getMenuNameJp() {
		return menuNameJp;
	}

	public void setMenuNameJp(String menuNameJp) {
		this.menuNameJp = menuNameJp;
	}

	public String getMenuDesc() {
		return menuDesc;
	}

	public void setMenuDesc(String menuDesc) {
		this.menuDesc = menuDesc;
	}

	public String getMenuUrl() {
		return menuUrl;
	}

	public void setMenuUrl(String menuUrl) {
		this.menuUrl = menuUrl;
	}

	public String getApiUrl() {
		return apiUrl;
	}

	public void setApiUrl(String apiUrl) {
		this.apiUrl = apiUrl;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

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
