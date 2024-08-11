package com.salesmanager.shop.model.catalog.category;

import java.io.Serializable;

public class CategoryEntity extends Category implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	private String imageUrl;

	private int sortOrder;
	private boolean visible;
	private boolean featured;
	private String lineage;
	private int depth;
	private Category parent;


	private String handlingFee;


	private String handlingFeeFor1688;

	public String getHandlingFeeFor1688() {
		return handlingFeeFor1688;
	}

	public void setHandlingFeeFor1688(String handlingFeeFor1688) {
		this.handlingFeeFor1688 = handlingFeeFor1688;
	}

	public String getHandlingFee() {
		return handlingFee;
	}

	public void setHandlingFee(String handlingFee) {
		this.handlingFee = handlingFee;
	}
	

	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	public String getLineage() {
		return lineage;
	}
	public void setLineage(String lineage) {
		this.lineage = lineage;
	}
	public int getDepth() {
		return depth;
	}
	public void setDepth(int depth) {
		this.depth = depth;
	}
	public Category getParent() {
		return parent;
	}
	public void setParent(Category parent) {
		this.parent = parent;
	}
	public boolean isFeatured() {
		return featured;
	}
	public void setFeatured(boolean featured) {
		this.featured = featured;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
}
