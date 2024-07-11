package com.salesmanager.core.model.catalog.product;

import java.util.List;

import com.salesmanager.core.model.catalog.product.attribute.AttributeCriteria;
import com.salesmanager.core.model.common.Criteria;
import com.salesmanager.core.model.feature.ProductFeatureType;
import lombok.Data;

@Data
public class ProductCriteria extends Criteria {
	
	public static final String ORIGIN_SHOP = "shop";
	public static final String ORIGIN_ADMIN = "admin";

	private String tag;

	/**
	 * @see ProductFeatureType
	 */
	private String productFeatureType;


	private String companyName;


	private Integer sellerCountryCode;

	private String productName;
	private List<AttributeCriteria> attributeCriteria;
	private String origin = ORIGIN_SHOP;

	private String shippingType;
	private Long startTime;

	private Long endTime;


	private Long createStartTime;

	private Long createEndTime;


	private Long modifiedStartTime;

	private Long modifiedEndTime;


	private String auditStatus;

	private String publishWay;

	private List<Long> shippingTemplateIds;

	private Boolean available = null;

	private List<Long> categoryIds;
	private List<String> availabilities;
	private List<Long> productIds;
	private List<Long> optionValueIds;
	private String sku;

	//V2
	private List<String> optionValueCodes;
	private String option;

	private String status;

	private Long manufacturerId = null;

	private Long ownerId = null;

	/**
	 * @see ProductCriteriaSortField
	 */
	private String sortField;


	/**
	 * desc asc
	 */
	private String sortDirection;

 	@Override
	public String toString() {
		return "ProductCriteria{" +
				"sellerCountryCode=" + sellerCountryCode +
				", productName='" + productName + '\'' +
				", attributeCriteria=" + attributeCriteria +
				", origin='" + origin + '\'' +
				", shippingType='" + shippingType + '\'' +
				", startTime=" + startTime +
				", endTime=" + endTime +
				", auditStatus='" + auditStatus + '\'' +
				", shippingTemplateIds=" + shippingTemplateIds +
				", available=" + available +
				", categoryIds=" + categoryIds +
				", availabilities=" + availabilities +
				", productIds=" + productIds +
				", optionValueIds=" + optionValueIds +
				", sku='" + sku + '\'' +
				", optionValueCodes=" + optionValueCodes +
				", option='" + option + '\'' +
				", status='" + status + '\'' +
				", manufacturerId=" + manufacturerId +
				", ownerId=" + ownerId +
				'}';
	}

}
