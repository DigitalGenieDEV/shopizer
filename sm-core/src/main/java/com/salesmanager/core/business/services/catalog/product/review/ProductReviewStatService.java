package com.salesmanager.core.business.services.catalog.product.review;

import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.review.ProductReviewStat;

public interface ProductReviewStatService extends SalesManagerEntityService<Long, ProductReviewStat>  {

	ProductReviewStat getByProduct(Product product);

}
