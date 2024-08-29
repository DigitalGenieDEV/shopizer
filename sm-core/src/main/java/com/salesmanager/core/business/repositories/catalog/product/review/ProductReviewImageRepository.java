package com.salesmanager.core.business.repositories.catalog.product.review;

import org.springframework.data.jpa.repository.JpaRepository;

import com.salesmanager.core.model.catalog.product.review.ProductReviewImage;

public interface ProductReviewImageRepository extends JpaRepository<ProductReviewImage, Long> {

}
