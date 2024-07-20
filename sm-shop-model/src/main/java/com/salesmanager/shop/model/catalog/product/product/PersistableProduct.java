package com.salesmanager.shop.model.catalog.product.product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.salesmanager.core.model.catalog.product.SellerProductShippingTextInfo;
import com.salesmanager.shop.model.catalog.category.Category;
import com.salesmanager.shop.model.catalog.product.PersistableImage;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableAnnouncement;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableProductVariant;
import lombok.Data;


@Data
public class PersistableProduct extends ProductEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Long sellerTextInfoId;

	private String manufacturer;

	private String identifier;

	private List<String> productTag;

	private Long shippingTemplateId;

	private List<PersistableProductAttribute> properties = new ArrayList<PersistableProductAttribute>();

	private PersistableAnnouncement announcement = new PersistableAnnouncement();

	private List<ProductDescription> descriptions = new ArrayList<ProductDescription>();

	private List<PersistableImage> images;//persist images and save reference

	private List<Category> categories = new ArrayList<Category>();

	private PersistableProductInventory inventory;

	private List<PersistableProductVariant> variants = new ArrayList<PersistableProductVariant>();

	private String type;

	private Long outProductId;


}
