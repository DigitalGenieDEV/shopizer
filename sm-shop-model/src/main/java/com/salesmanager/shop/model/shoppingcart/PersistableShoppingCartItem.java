package com.salesmanager.shop.model.shoppingcart;

import java.io.Serializable;
import java.util.List;

import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTransportationCompanyEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.shipping.CartItemType;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import com.salesmanager.shop.model.catalog.product.attribute.ProductAttribute;
import lombok.Getter;
import lombok.Setter;

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

	private String additionalServicesIdMap;

	/**
	 * 国内运输还是国外运输
	 */
	private ShippingType shippingType;

	/**
	 * 国际运输方式
	 * @see TransportationMethod
	 */
	private TransportationMethod internationalTransportationMethod;

	/**
	 * 国内运输方式
	 * @see TransportationMethod
	 */
	private TransportationMethod nationalTransportationMethod;


	/**
	 * 委托配送还是自提
	 */
	private ShippingTransportationType shippingTransportationType;


	/**
	 * 货车型号
	 * @see TruckModelEnums
	 */
	private String truckModel;

	/**
	 * 货车运输公司
	 * @see TruckModelEnums
	 */
	@Getter
	@Setter
	private TruckTransportationCompanyEnums truckTransportationCompany;

	/**
	 * 通关选项
	 * @see PlayThroughOptionsEnums
	 */
	private String playThroughOption;



	/**
	 * 货车类型
	 * @see TruckTypeEnums
	 */
	private String truckType;

	/**
	 * @see CartItemType
	 */
	@Getter
	@Setter
	private String itemType;


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


	public String getAdditionalServicesIdMap() {
		return additionalServicesIdMap;
	}

	public void setAdditionalServicesIdMap(String additionalServicesIdMap) {
		this.additionalServicesIdMap = additionalServicesIdMap;
	}

	public ShippingType getShippingType() {
		return shippingType;
	}

	public void setShippingType(ShippingType shippingType) {
		this.shippingType = shippingType;
	}

	public TransportationMethod getInternationalTransportationMethod() {
		return internationalTransportationMethod;
	}

	public void setInternationalTransportationMethod(TransportationMethod internationalTransportationMethod) {
		this.internationalTransportationMethod = internationalTransportationMethod;
	}

	public TransportationMethod getNationalTransportationMethod() {
		return nationalTransportationMethod;
	}

	public void setNationalTransportationMethod(TransportationMethod nationalTransportationMethod) {
		this.nationalTransportationMethod = nationalTransportationMethod;
	}

	public ShippingTransportationType getShippingTransportationType() {
		return shippingTransportationType;
	}

	public void setShippingTransportationType(ShippingTransportationType shippingTransportationType) {
		this.shippingTransportationType = shippingTransportationType;
	}

	public String getTruckModel() {
		return truckModel;
	}

	public void setTruckModel(String truckModel) {
		this.truckModel = truckModel;
	}

	public String getPlayThroughOption() {
		return playThroughOption;
	}

	public void setPlayThroughOption(String playThroughOption) {
		this.playThroughOption = playThroughOption;
	}

	public String getTruckType() {
		return truckType;
	}

	public void setTruckType(String truckType) {
		this.truckType = truckType;
	}
}
