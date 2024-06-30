package com.salesmanager.core.business.repositories.catalog.product.attribute;

import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.product.attribute.ProductAnnouncementAttribute;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PageableProductAnnouncementAttributeRepository extends PagingAndSortingRepository<Category, Long> {



}
