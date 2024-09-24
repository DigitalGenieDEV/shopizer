package com.salesmanager.shop.model.shoppingcart;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

import com.salesmanager.core.enmus.PlayThroughOptionsEnums;
import com.salesmanager.core.enmus.TruckModelEnums;
import com.salesmanager.core.enmus.TruckTransportationCompanyEnums;
import com.salesmanager.core.enmus.TruckTypeEnums;
import com.salesmanager.core.model.shipping.ShippingTransportationType;
import com.salesmanager.core.model.shipping.ShippingType;
import com.salesmanager.core.model.shipping.TransportationMethod;
import com.salesmanager.shop.model.entity.ShopEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;


public class ShoppingCartItem extends ShopEntity implements Serializable {
	
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String price;
	private String image;
	private BigDecimal productPrice;
	private int quantity;
	private String sku;//sku
	private String code;//shopping cart code


	/**
	 * 增值服务Id list列表用逗号分隔
	 * The value-added service ID list is separated by commas.
	 */
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

	private boolean productVirtual;
	
	private String subTotal;
	
	private List<ShoppingCartAttribute> shoppingCartAttributes;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getQuantity() {
		if(quantity <= 0) {
			quantity = 1;
		}
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}


	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public List<ShoppingCartAttribute> getShoppingCartAttributes() {
		return shoppingCartAttributes;
	}
	public void setShoppingCartAttributes(List<ShoppingCartAttribute> shoppingCartAttributes) {
		this.shoppingCartAttributes = shoppingCartAttributes;
	}
	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}
	public BigDecimal getProductPrice() {
		return productPrice;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getImage() {
		return image;
	}
	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}
	public String getSubTotal() {
		return subTotal;
	}
	public boolean isProductVirtual() {
		return productVirtual;
	}
	public void setProductVirtual(boolean productVirtual) {
		this.productVirtual = productVirtual;
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
