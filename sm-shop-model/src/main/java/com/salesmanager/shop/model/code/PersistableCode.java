package com.salesmanager.shop.model.code;

public class PersistableCode {
	private int id;
	private int parentId;
	private String code = "";
	private String codeNameKr = "";
	private String codeNameEn = "";
	private String codeNameCn = "";
	private String codeNameJp = "";
	private String codeDesc = "";
	private String value = "";
	private int ord = 0;
	private int visible = 0;
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

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCodeNameKr() {
		return codeNameKr;
	}

	public void setCodeNameKr(String codeNameKr) {
		this.codeNameKr = codeNameKr;
	}

	public String getCodeNameEn() {
		return codeNameEn;
	}

	public void setCodeNameEn(String codeNameEn) {
		this.codeNameEn = codeNameEn;
	}

	public String getCodeNameCn() {
		return codeNameCn;
	}

	public void setCodeNameCn(String codeNameCn) {
		this.codeNameCn = codeNameCn;
	}

	public String getCodeNameJp() {
		return codeNameJp;
	}

	public void setCodeNameJp(String codeNameJp) {
		this.codeNameJp = codeNameJp;
	}

	public String getCodeDesc() {
		return codeDesc;
	}

	public void setCodeDesc(String codeDesc) {
		this.codeDesc = codeDesc;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
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
