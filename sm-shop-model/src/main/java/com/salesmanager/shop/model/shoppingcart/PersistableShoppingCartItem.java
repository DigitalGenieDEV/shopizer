package com.salesmanager.shop.model.shoppingcart;

import java.io.Serializable;
import java.util.List;

import com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute;
import com.salesmanager.shop.model.fulfillment.PersistableProductAdditionalService;

/**
 * Compatible with v1
 * @author c.samson
 *
 */
public class PersistableShoppingCartItem implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String product;// or product sku (instance or product)
	private int quantity;
	private String promoCode;
	private List<ProductAttribute> attributes;

	private PersistableProductAdditionalService additionalServices;


	public String getPromoCode() {
		return promoCode;
	}
	public void setPromoCode(String promoCode) {
		this.promoCode = promoCode;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public List<ProductAttribute> getAttributes() {
		return attributes;
	}
	public void setAttributes(List<ProductAttribute> attributes) {
		this.attributes = attributes;
	}
	public String getProduct() {
		return product;
	}
	public void setProduct(String product) {
		this.product = product;
	}


	public PersistableProductAdditionalService getAdditionalServices() {
		return additionalServices;
	}

	public void setAdditionalServices(PersistableProductAdditionalService additionalServices) {
		this.additionalServices = additionalServices;
	}
}
