package com.salesmanager.shop.model.adminmenu;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class ReadableAdminMenu {
	private static final long serialVersionUID = 1L;

	private int id;
	private int parentId;
	private String menuName = "ROOT";
	private String menuDesc = "ROOT";
	private String menuUrl = "/";
	private int ord = 0;
	private int depth = 0;
	private int visible = 0;
	private String menuNamePath = "ROOT";
	private ArrayList<ReadableAdminMenu> children = null;

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

	public ArrayList<ReadableAdminMenu> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<ReadableAdminMenu> children) {
		this.children = children;
	}

}
