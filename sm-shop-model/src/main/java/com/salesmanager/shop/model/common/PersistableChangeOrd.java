package com.salesmanager.shop.model.common;

import java.util.ArrayList;

public class PersistableChangeOrd {
	private ArrayList<ChangeOrdEntity> changeOrdList = null;

	public ArrayList<ChangeOrdEntity> getChangeOrdList() {
		return changeOrdList;
	}

	public void setChangeOrdList(ArrayList<ChangeOrdEntity> changeOrdList) {
		this.changeOrdList = changeOrdList;
	}
}
