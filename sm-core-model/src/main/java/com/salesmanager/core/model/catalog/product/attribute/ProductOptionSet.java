package com.salesmanager.core.model.catalog.product.attribute;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.type.ProductType;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;

/**
 * Create a list of option and option value in order to accelerate and 
 * prepare product attribute creation
 * @author carlsamson
 *
 */
@Entity
@Table(name="PRODUCT_OPTION_SET",
uniqueConstraints={
		@UniqueConstraint(columnNames={
				"MERCHANT_ID",
				"PRODUCT_OPTION_SET_CODE"
			})
	}
)
public class ProductOptionSet extends SalesManagerEntity<Long, ProductOptionSet> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="PRODUCT_OPTION_SET_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "PRODUCT_OPT_SET_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@NotEmpty
	@Column(name="PRODUCT_OPTION_SET_CODE")
	private String code;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="PRODUCT_OPTION_ID", nullable=false)
	private ProductOption option;
	
	@ManyToMany(fetch = FetchType.LAZY, targetEntity=ProductOptionValue.class)
	@JoinTable(name = "PRODUCT_OPT_SET_OPT_VALUE")
	private List<ProductOptionValue> values = new ArrayList<ProductOptionValue>();
	
	@ManyToMany(fetch = FetchType.LAZY, targetEntity=ProductType.class)
	@JoinTable(name = "PRODUCT_OPT_SET_PRD_TYPE")
	private Set<ProductType> productTypes = new HashSet<ProductType>();
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="MERCHANT_ID", nullable=false)
	private MerchantStore store;
	
	@Column(name="PRODUCT_OPTION_SET_DISP")
	private boolean optionDisplayOnly = false;

	@Column(name="REQUIRED")
	private boolean required = false;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="CATEGORY_ID", nullable=true)
	private Category category;

	@Column(name="OPTION_SET_FOR_SALE_TYPE", length=20)
	private String optionSetForSaleType;


	public String getOptionSetForSaleType() {
		return optionSetForSaleType;
	}

	public void setOptionSetForSaleType(String optionSetForSaleType) {
		this.optionSetForSaleType = optionSetForSaleType;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}


	public ProductOption getOption() {
		return option;
	}
	public void setOption(ProductOption option) {
		this.option = option;
	}
	public List<ProductOptionValue> getValues() {
		return values;
	}
	public void setValues(List<ProductOptionValue> values) {
		this.values = values;
	}
	public MerchantStore getStore() {
		return store;
	}
	public void setStore(MerchantStore store) {
		this.store = store;
	}
	@Override
	public Long getId() {
		return this.id;
	}
	@Override
	public void setId(Long id) {
		this.id = id;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public boolean isOptionDisplayOnly() {
		return optionDisplayOnly;
	}
	public void setOptionDisplayOnly(boolean optionDisplayOnly) {
		this.optionDisplayOnly = optionDisplayOnly;
	}
	
	public Set<ProductType> getProductTypes() {
		return productTypes;
	}
	public void setProductTypes(Set<ProductType> productTypes) {
		this.productTypes = productTypes;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}
}
