package com.salesmanager.shop.model.manager;

public class ManagerEntity {
	@SuppressWarnings("unused")
	private static final long serialVersionUID = 1L;
	private boolean active;
	private String content;
	private int deptId;
	private int positionId;
	private int faxId;
	private int telId1;
	private int telId2;
	private int grpId;
	private String tel1;
	private String tel2;
	private String fax;
	private String grpName;
	private String regDate;
	private String modDate;

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public int getDeptId() {
		return deptId;
	}

	public void setDeptId(int deptId) {
		this.deptId = deptId;
	}

	public int getPositionId() {
		return positionId;
	}

	public void setPositionId(int positionId) {
		this.positionId = positionId;
	}

	public int getFaxId() {
		return faxId;
	}

	public void setFaxId(int faxId) {
		this.faxId = faxId;
	}

	public int getTelId1() {
		return telId1;
	}

	public void setTelId1(int telId1) {
		this.telId1 = telId1;
	}

	public int getTelId2() {
		return telId2;
	}

	public void setTelId2(int telId2) {
		this.telId2 = telId2;
	}

	public int getGrpId() {
		return grpId;
	}

	public void setGrpId(int grpId) {
		this.grpId = grpId;
	}

	public String getTel1() {
		return tel1;
	}

	public void setTel1(String tel1) {
		this.tel1 = tel1;
	}

	public String getTel2() {
		return tel2;
	}

	public void setTel2(String tel2) {
		this.tel2 = tel2;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getGrpName() {
		return grpName;
	}

	public void setGrpName(String grpName) {
		this.grpName = grpName;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getModDate() {
		return modDate;
	}

	public void setModDate(String modDate) {
		this.modDate = modDate;
	}
	
	

}
