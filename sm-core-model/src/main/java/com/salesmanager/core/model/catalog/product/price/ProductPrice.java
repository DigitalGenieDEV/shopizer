package com.salesmanager.core.model.catalog.product.price;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.catalog.product.availability.ProductAvailability;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.utils.CloneUtils;

@Entity
@Table(name = "PRODUCT_PRICE")
public class ProductPrice extends SalesManagerEntity<Long, ProductPrice> {
	private static final long serialVersionUID = 1L;

	public final static String DEFAULT_PRICE_CODE = "base";

	@Id
	@Column(name = "PRODUCT_PRICE_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_PRICE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "productPrice", cascade = CascadeType.ALL)
	private Set<ProductPriceDescription> descriptions = new HashSet<ProductPriceDescription>();

	@NotEmpty
	@Pattern(regexp = "^[a-zA-Z0-9_]*$")
	@Column(name = "PRODUCT_PRICE_CODE", nullable = false)
	private String code = DEFAULT_PRICE_CODE;

	@Column(name = "PRODUCT_PRICE_AMOUNT", nullable = true)
	private BigDecimal productPriceAmount = new BigDecimal(0);

	@Column(name = "PRODUCT_PRICE_TYPE", length = 20)
	@Enumerated(value = EnumType.STRING)
	private ProductPriceType productPriceType = ProductPriceType.ONE_TIME;

	@Column(name = "DEFAULT_PRICE")
	private boolean defaultPrice = false;

	@Temporal(TemporalType.DATE)
	@Column(name = "PRODUCT_PRICE_SPECIAL_ST_DATE")
	private Date productPriceSpecialStartDate;

	@Temporal(TemporalType.DATE)
	@Column(name = "PRODUCT_PRICE_SPECIAL_END_DATE")
	private Date productPriceSpecialEndDate;

	@Column(name = "PRODUCT_PRICE_SPECIAL_AMOUNT")
	private BigDecimal productPriceSpecialAmount;

	@JsonIgnore
	@ManyToOne(targetEntity = ProductAvailability.class)
	@JoinColumn(name = "PRODUCT_AVAIL_ID", nullable = false)
	private ProductAvailability productAvailability;

	@Column(name = "PRODUCT_IDENTIFIER_ID", nullable = true)
	private Long productIdentifierId;

	/**
	 * Ladder prices are stored using json, and the data structure is list . PriceRange.class
	 */
	@Column(name = "PRODUCT_PRICE_RANGE", nullable = true)
	private String priceRangeList;


	/**
	 * Ladder prices are stored using json, and the data structure is list . PriceRange.class
	 */
	@Column(name = "PRODUCT_SUPPLY_PRICE_RANGE", nullable = true)
	private String priceSupplyRangeList;

	/**
	 * 0-无sku按商品数量报价，1-按sku规格报价 2-有sku按商品数量报价
	 *
	 * 0-No sku. Quote based on product quantity.
	 * 1-Quotation based on SKU specifications
	 *  2- If there is a SKU, the quotation is based on the quantity of the product.
	 */
	@Column(name = "QUOTE_TYPE", nullable = true)
	private Integer quoteType;

	/**
	 * 分销价，一件代发价格
	 * 一件代发价，此时需要判断商品是否开通一件代发，否在在不为null的情况下为大市场批发价=price
	 * Distribution price, dropshipping price
	 */
	@Column(name = "CONSIGN_PRICE", nullable = true)
	private String consignPrice;


	/**
	 * 供货价
	 */
	@Column(name = "SUPPLY_PRICE", nullable = true)
	private String supplyPrice;


	/**
	 * 分销供货价，一件代发价格
	 * 一件代发价，此时需要判断商品是否开通一件代发，否在在不为null的情况下为大市场批发价=price
	 * Distribution price, dropshipping price
	 */
	@Column(name = "CONSIGN_SUPPLY_PRICE", nullable = true)
	private String consignSupplyPrice;

	public String getPriceSupplyRangeList() {
		return priceSupplyRangeList;
	}

	public void setPriceSupplyRangeList(String priceSupplyRangeList) {
		this.priceSupplyRangeList = priceSupplyRangeList;
	}

	public String getConsignSupplyPrice() {
		return consignSupplyPrice;
	}

	public void setConsignSupplyPrice(String consignSupplyPrice) {
		this.consignSupplyPrice = consignSupplyPrice;
	}

	public String getSupplyPrice() {
		return supplyPrice;
	}

	public void setSupplyPrice(String supplyPrice) {
		this.supplyPrice = supplyPrice;
	}

	public Long getProductIdentifierId() {
		return productIdentifierId;
	}

	public void setProductIdentifierId(Long productIdentifierId) {
		this.productIdentifierId = productIdentifierId;
	}

	public String getPriceRangeList() {
		return priceRangeList;
	}

	public void setPriceRangeList(String priceRangeList) {
		this.priceRangeList = priceRangeList;
	}

	public Integer getQuoteType() {
		return quoteType;
	}

	public void setQuoteType(Integer quoteType) {
		this.quoteType = quoteType;
	}

	public String getConsignPrice() {
		return consignPrice;
	}

	public void setConsignPrice(String consignPrice) {
		this.consignPrice = consignPrice;
	}
	public ProductPrice() {
	}

	@Override
	public Long getId() {
		return this.id;
	}

	@Override
	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getProductPriceAmount() {
		return productPriceAmount;
	}

	public void setProductPriceAmount(BigDecimal productPriceAmount) {
		this.productPriceAmount = productPriceAmount;
	}

	public Date getProductPriceSpecialStartDate() {
		return CloneUtils.clone(productPriceSpecialStartDate);
	}

	public void setProductPriceSpecialStartDate(Date productPriceSpecialStartDate) {
		this.productPriceSpecialStartDate = CloneUtils.clone(productPriceSpecialStartDate);
	}

	public Date getProductPriceSpecialEndDate() {
		return CloneUtils.clone(productPriceSpecialEndDate);
	}

	public void setProductPriceSpecialEndDate(Date productPriceSpecialEndDate) {
		this.productPriceSpecialEndDate = CloneUtils.clone(productPriceSpecialEndDate);
	}

	public BigDecimal getProductPriceSpecialAmount() {
		return productPriceSpecialAmount;
	}

	public void setProductPriceSpecialAmount(BigDecimal productPriceSpecialAmount) {
		this.productPriceSpecialAmount = productPriceSpecialAmount;
	}

	public Set<ProductPriceDescription> getDescriptions() {
		return descriptions;
	}

	public void setDescriptions(Set<ProductPriceDescription> descriptions) {
		this.descriptions = descriptions;
	}

	public boolean isDefaultPrice() {
		return defaultPrice;
	}

	public void setDefaultPrice(boolean defaultPrice) {
		this.defaultPrice = defaultPrice;
	}

	public void setProductAvailability(ProductAvailability productAvailability) {
		this.productAvailability = productAvailability;
	}

	public ProductAvailability getProductAvailability() {
		return productAvailability;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

	public void setProductPriceType(ProductPriceType productPriceType) {
		this.productPriceType = productPriceType;
	}

	public ProductPriceType getProductPriceType() {
		return productPriceType;
	}


}
