package com.salesmanager.shop.model.usermenu;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ReadableUserMenu {
	private static final long serialVersionUID = 1L;
	private int id;
	private int parentId;
	private String menuName;
	private String menuNameEn = "ROOT";
	private String menuNameCn = "ROOT";
	private String menuNameJp = "ROOT";
	private String menuDesc;
	private int ord = 0;
	private int depth = 0;
	private int visible = 0;
	private String menuNamePath = "ROOT";
	private String top = "";
	private String side = "";
	private String navi = "";
	private String tab = "";
	private String memberTarget = "";
	private String linkTarget = "";
	private String url = "";
	private ArrayList<ReadableUserMenu> children = null;

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

	public int getOrd() {
		return ord;
	}

	public void setOrd(int ord) {
		this.ord = ord;
	}

	public int getDepth() {
		return depth;
	}

	public void setDepth(int depth) {
		this.depth = depth;
	}

	public int getVisible() {
		return visible;
	}

	public void setVisible(int visible) {
		this.visible = visible;
	}

	public String getMenuNamePath() {
		return menuNamePath;
	}

	public void setMenuNamePath(String menuNamePath) {
		this.menuNamePath = menuNamePath;
	}

	public String getTop() {
		return top;
	}

	public void setTop(String top) {
		this.top = top;
	}

	public String getSide() {
		return side;
	}

	public void setSide(String side) {
		this.side = side;
	}

	public String getNavi() {
		return navi;
	}

	public void setNavi(String navi) {
		this.navi = navi;
	}

	public String getTab() {
		return tab;
	}

	public void setTab(String tab) {
		this.tab = tab;
	}

	public String getMemberTarget() {
		return memberTarget;
	}

	public void setMemberTarget(String memberTarget) {
		this.memberTarget = memberTarget;
	}

	public String getLinkTarget() {
		return linkTarget;
	}

	public void setLinkTarget(String linkTarget) {
		this.linkTarget = linkTarget;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<ReadableUserMenu> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ReadableUserMenu> children) {
		this.children = children;
	}

}
