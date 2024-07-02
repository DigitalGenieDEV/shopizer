package com.salesmanager.shop.model.catalog.product;

import java.io.Serializable;

import com.salesmanager.core.model.catalog.product.ProductAuditStatus;
import com.salesmanager.shop.model.entity.Entity;


public class Product extends Entity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String publishWay;
	private boolean productShipeable = false;

	private boolean available;
	private boolean visible = true;

	private int sortOrder;
	private String dateAvailable;
	private String creationDate;

	private String productAuditStatus;

	private String modificationDate;

	public String getModificationDate() {
		return modificationDate;
	}

	public String getPublishWay() {
		return publishWay;
	}

	public void setPublishWay(String publishWay) {
		this.publishWay = publishWay;
	}

	public void setModificationDate(String modificationDate) {
		this.modificationDate = modificationDate;
	}

	public boolean isProductShipeable() {
		return productShipeable;
	}
	public void setProductShipeable(boolean productShipeable) {
		this.productShipeable = productShipeable;
	}
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
		this.available = available;
	}
	public int getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(int sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getDateAvailable() {
		return dateAvailable;
	}
	public void setDateAvailable(String dateAvailable) {
		this.dateAvailable = dateAvailable;
	}
	public String getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(String creationDate) {
		this.creationDate = creationDate;
	}
	public boolean isVisible() {
		return visible;
	}
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public String getProductAuditStatus() {
		return productAuditStatus;
	}

	public void setProductAuditStatus(String productAuditStatus) {
		this.productAuditStatus = productAuditStatus;
	}
}
