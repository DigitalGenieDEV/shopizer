package com.salesmanager.shop.model.catalog.product.product.variant;

import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.catalog.product.ReadableImage;
import com.salesmanager.shop.model.catalog.product.inventory.ReadableInventory;
import com.salesmanager.shop.model.catalog.product.variation.ReadableProductVariation;

public class ReadableProductVariant extends ProductVariant {

	private static final long serialVersionUID = 1L;
	
	private List<ReadableProductVariation> variations;

	private String code;
	private List<ReadableImage> images = new ArrayList<ReadableImage>();
	private List<ReadableInventory> inventory = new ArrayList<ReadableInventory>();

	public List<ReadableProductVariation> getVariation() {
		return variations;
	}

	public void setVariations(List<ReadableProductVariation> variations) {
		this.variations = variations;
	}

	public List<ReadableImage> getImages() {
		return images;
	}
	public void setImages(List<ReadableImage> images) {
		this.images = images;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<ReadableInventory> getInventory() {
		return inventory;
	}
	public void setInventory(List<ReadableInventory> inventory) {
		this.inventory = inventory;
	}

}
