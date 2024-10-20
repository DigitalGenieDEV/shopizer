package com.salesmanager.shop.model.catalog.product.product.variant;

import com.salesmanager.shop.model.catalog.product.product.PersistableProductInventory;
import com.salesmanager.shop.model.catalog.product.product.variantGroup.PersistableProductVariantGroup;

import java.util.List;

public class PersistableProductVariant extends ProductVariant {

	private static final long serialVersionUID = 1L;

	private PersistableProductInventory inventory;

	private List<PersistableVariation> productVariations = null;


	public PersistableProductInventory getInventory() {
		return inventory;
	}

	public void setInventory(PersistableProductInventory inventory) {
		this.inventory = inventory;
	}


	public List<PersistableVariation> getProductVariations() {
		return productVariations;
	}

	public void setProductVariations(List<PersistableVariation> productVariations) {
		this.productVariations = productVariations;
	}
}
