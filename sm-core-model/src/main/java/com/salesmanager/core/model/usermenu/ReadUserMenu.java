package com.salesmanager.core.model.usermenu;

public interface ReadUserMenu {
	 Integer getId();
	 Integer getParent_Id();
	 String getMenu_Name();
	 String getMenu_Desc();
	 String getUrl();
	 String getTop();
	 String getSide();
	 String getNavi();
	 String getTab();
	 String getMember_Target();
	 String getLink_Target();
	 int getVisible();
	 int getOrd();
	 int getDepth();
	 String getMenu_name_path();
}
