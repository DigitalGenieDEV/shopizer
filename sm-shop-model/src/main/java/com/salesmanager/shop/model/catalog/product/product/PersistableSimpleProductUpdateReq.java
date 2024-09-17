package com.salesmanager.shop.model.catalog.product.product;

import com.salesmanager.shop.model.catalog.category.Category;
import com.salesmanager.shop.model.catalog.product.PersistableImage;
import com.salesmanager.shop.model.catalog.product.ProductDescription;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableAnnouncement;
import com.salesmanager.shop.model.catalog.product.attribute.PersistableProductAttribute;
import com.salesmanager.shop.model.catalog.product.product.variant.PersistableProductVariant;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


@Data
public class PersistableSimpleProductUpdateReq  implements Serializable {

	private Long productId;


	private List<String> productTag;


	private Long leftCategory;


	private String type;

	/**
	 * 认证文件
	 */
	private String certificationDocument;

	/**
	 * 知识产权文件
	 */
	private String intellectualPropertyDocuments;
}
