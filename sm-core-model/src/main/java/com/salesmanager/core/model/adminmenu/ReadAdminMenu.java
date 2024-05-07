package com.salesmanager.core.model.adminmenu;

public interface ReadAdminMenu {
	 Integer getId();
	 Integer getParent_Id();
	 String getMenu_Name();
	 String getMenu_Desc();
	 String getMenu_Url();
	 int getVisible();
	 int getOrd();
	 int getDepth();
	 String getMenu_name_path();
}
