package com.salesmanager.shop.model.dept;

import java.util.ArrayList;

public class PersistableChangeOrdDept {
	private ArrayList<ChangeOrdDeptEntity> changeOrdList = null;

	public ArrayList<ChangeOrdDeptEntity> getChangeOrdList() {
		return changeOrdList;
	}

	public void setChangeOrdList(ArrayList<ChangeOrdDeptEntity> changeOrdList) {
		this.changeOrdList = changeOrdList;
	}

}
