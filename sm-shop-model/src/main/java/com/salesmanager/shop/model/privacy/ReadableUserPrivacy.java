package com.salesmanager.shop.model.privacy;

import java.util.ArrayList;
import java.util.List;

public class ReadableUserPrivacy {
	private int id = 0;
	private String title;
	private String content;

	private List<PrivacyUserEntity> data = new ArrayList<PrivacyUserEntity>();

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<PrivacyUserEntity> getData() {
		return data;
	}

	public void setData(List<PrivacyUserEntity> data) {
		this.data = data;
	}

}
