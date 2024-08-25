package com.salesmanager.core.model.catalog.product.price;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class ProductPriceDO {

	public final static String DEFAULT_PRICE_CODE = "base";

	private Long id;

	private BigDecimal productPriceAmount = new BigDecimal(0);

	private String code = DEFAULT_PRICE_CODE;

	private boolean defaultPrice = false;

	private Date productPriceSpecialStartDate;

	private Date productPriceSpecialEndDate;

	private BigDecimal productPriceSpecialAmount;

	private Integer discountPercent;

	private String currency;

	private Long productIdentifierId;

	private String priceRangeList;


	private String priceSupplyRangeList;


	private String consignPrice;


	private String supplyPrice;


	private String consignSupplyPrice;

	private ProductPriceType productPriceType = ProductPriceType.ONE_TIME;

}
