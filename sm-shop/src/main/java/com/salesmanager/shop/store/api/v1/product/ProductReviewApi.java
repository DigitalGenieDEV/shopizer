package com.salesmanager.shop.store.api.v1.product;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.collect.ImmutableMap;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.review.ProductReviewService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.review.ProductReview;
import com.salesmanager.core.model.catalog.product.review.ProductReviewCriteria;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.library.StoreLibraryCriteria;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.catalog.product.PersistableProductReview;
import com.salesmanager.shop.model.catalog.product.PersistableProductReviewRecommend;
import com.salesmanager.shop.model.catalog.product.ReadableProductReview;
import com.salesmanager.shop.model.catalog.product.ReadableProductReviews;
import com.salesmanager.shop.store.api.exception.RestApiException;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;
import com.salesmanager.shop.utils.ServiceRequestCriteriaBuilderUtils;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
public class ProductReviewApi {

	@Inject 
	private ProductCommonFacade productCommonFacade;
	
	@Inject 
	private ProductService productService;
	
	@Inject 
	private ProductReviewService productReviewService;
	
	private static final Map<String, String> MAPPING_FIELDS = ImmutableMap.<String, String>builder()
			.put("name", "name")
			.put("readableAudit.user", "auditSection.modifiedBy").build();

  private static final Logger LOGGER = LoggerFactory.getLogger(ProductReviewApi.class);

  @RequestMapping(
      value = {
        "/private/products/{id}/reviews",
        "/auth/products/{id}/reviews",
        "/auth/products/{id}/reviews",
        "/auth/products/{id}/reviews"
      },
      method = RequestMethod.POST)
  @ResponseStatus(HttpStatus.CREATED)
  @ResponseBody
  @ApiImplicitParams({
      @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
      @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public PersistableProductReview create(
      @PathVariable final Long id,
      @Valid @RequestPart(name = "param") PersistableProductReview review,
      @RequestPart(name = "reviewImages", required = false) List<MultipartFile> reviewImages,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {

	try {
		// rating already exist
		ProductReview prodReview = productReviewService.getByProductAndCustomer(review.getProductId(), review.getCustomerId());
		if (prodReview != null) {
			response.sendError(500, "A review already exist for this customer and product");
			return null;
		}
		// rating maximum 5
		if (review.getRating() > Constants.MAX_REVIEW_RATING_SCORE) {
			response.sendError(503, "Maximum rating score is " + Constants.MAX_REVIEW_RATING_SCORE);
			return null;
		}
		
		review.setProductId(id);
		productCommonFacade.saveOrUpdateReview(review, merchantStore, language, reviewImages);

      return review;

    } catch (Exception e) {
      LOGGER.error("Error while saving product review", e);
      try {
        response.sendError(503, "Error while saving product review" + e.getMessage());
      } catch (Exception ignore) {
      }

      return null;
    }
  }

	@RequestMapping(value = "/product/{id}/reviews", method = RequestMethod.GET)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "keyword", dataType = "String"),
		@ApiImplicitParam(name = "sortType", dataType = "String"),
		@ApiImplicitParam(name = "checkMedia", dataType = "boolean", defaultValue = "false"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ReadableProductReviews getAll(
			@PathVariable final Long id,
			@ApiIgnore MerchantStore merchantStore,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "count", required = false) Integer count,
			@RequestParam(value = "checkMedia", required = false) boolean checkMedia,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "sortType", required = false) String sortType,
			@ApiIgnore Language language,
			HttpServletResponse response,
			HttpServletRequest request) {
		
		try {
			// product exist
			Product product = productService.getById(id);
			
			if (product == null) {
				response.sendError(404, "Product id " + id + " does not exists");
				return null;
			}
			
			if(page == null) {
				page = 0;
			}
			if(count == null) {
				count = Integer.MAX_VALUE;
			}
			
			Pageable pageRequest = null;
			if(checkMedia) {
				pageRequest = PageRequest.of(page, count, Sort.by(Sort.Order.desc("MEDIA_FLAG"), Sort.Order.desc(sortType))); 
			} else {
				pageRequest = PageRequest.of(page, count, Sort.by(Sort.Order.desc(sortType)));
			}
			
			ReadableProductReviews reviews = productCommonFacade.getProductReviews(product, merchantStore, language, keyword, pageRequest);
			return reviews;
		} catch (Exception e) {
			LOGGER.error("Error while getting product reviews", e);
			try {
				response.sendError(503, "Error while getting product reviews" + e.getMessage());
			} catch (Exception ignore) {
				
			}
			return null;
		}
	}
  
	@RequestMapping(value = {"/auth/products/{id}/reviews/{reviewId}/recommend"}, method = RequestMethod.PUT)
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody @ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "checkMedia", dataType = "boolean", defaultValue = "false"),
		@ApiImplicitParam(name = "keyword", dataType = "String"),
		@ApiImplicitParam(name = "sortType", dataType = "String"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ReadableProductReviews recommend(
			@PathVariable final Long id,
			@PathVariable final Long reviewId,
			@Valid @RequestBody PersistableProductReviewRecommend recommend,
			@ApiIgnore MerchantStore merchantStore,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "count", required = false) Integer count,
			@RequestParam(value = "checkMedia", required = false) boolean checkMedia,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "sortType", required = false) String sortType,
			@ApiIgnore Language language,
			HttpServletRequest request,
			HttpServletResponse response) {
		
		// recommend update
		try {
			productCommonFacade.updateReviewRecommend(reviewId, recommend);
		} catch (Exception e) {
			// TODO: handle exception
			try {
				response.sendError(503, e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		
		try {
			// product exist
			Product product = productService.getById(id);
			
			if (product == null) {
				response.sendError(404, "Product id " + id + " does not exists");
				return null;
			}
			
			if (product == null) {
				response.sendError(404, "Product id " + id + " does not exists");
				return null;
			}
			
			if(page == null) {
				page = 0;
			}
			if(count == null) {
				count = Integer.MAX_VALUE;
			}
			
			Pageable pageRequest = null;
			if(checkMedia) {
				pageRequest = PageRequest.of(page, count, Sort.by(Sort.Order.desc("MEDIA_FLAG"), Sort.Order.desc(sortType))); 
			} else {
				pageRequest = PageRequest.of(page, count, Sort.by(Sort.Order.desc(sortType)));
			}
			
			ReadableProductReviews reviews = productCommonFacade.getProductReviews(product, merchantStore, language, keyword, pageRequest);
			return reviews;
		} catch (Exception e) {
			LOGGER.error("Error while getting product reviews", e);
			try {
				response.sendError(503, "Error while getting product reviews" + e.getMessage());
			} catch (Exception ignore) {
				
			}
			
			return null;
		}
	}
  
  @RequestMapping(
      value = {
        "/private/products/{id}/reviews/{reviewid}",
        "/auth/products/{id}/reviews/{reviewid}"
      },
      method = RequestMethod.PUT)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @ApiImplicitParams({
      @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
      @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public PersistableProductReview update(
      @PathVariable final Long id,
      @PathVariable final Long reviewId,
      @Valid @RequestBody PersistableProductReview review,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletRequest request,
      HttpServletResponse response) {

    try {
      ProductReview prodReview = productReviewService.getById(reviewId);
      if (prodReview == null) {
        response.sendError(404, "Product review with id " + reviewId + " does not exist");
        return null;
      }

      if (prodReview.getCustomer().getId().longValue() != review.getCustomerId().longValue()) {
        response.sendError(404, "Product review with id " + reviewId + " does not exist");
        return null;
      }

      // rating maximum 5
      if (review.getRating() > Constants.MAX_REVIEW_RATING_SCORE) {
        response.sendError(503, "Maximum rating score is " + Constants.MAX_REVIEW_RATING_SCORE);
        return null;
      }

      review.setProductId(id);
      productCommonFacade.saveOrUpdateReview(review, merchantStore, language, null);

      return review;

    } catch (Exception e) {
      LOGGER.error("Error while saving product review", e);
      try {
        response.sendError(503, "Error while saving product review" + e.getMessage());
      } catch (Exception ignore) {
      }

      return null;
    }
  }

  @RequestMapping(
      value = {
        "/private/products/{id}/reviews/{reviewid}",
        "/auth/products/{id}/reviews/{reviewid}"
      },
      method = RequestMethod.DELETE)
  @ResponseStatus(HttpStatus.OK)
  @ResponseBody
  @ApiImplicitParams({
      @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
      @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
  })
  public void delete(
      @PathVariable final Long id,
      @PathVariable final Long reviewId,
      @ApiIgnore MerchantStore merchantStore,
      @ApiIgnore Language language,
      HttpServletResponse response) {

    try {
      ProductReview prodReview = productReviewService.getById(reviewId);
      if (prodReview == null) {
        response.sendError(404, "Product review with id " + reviewId + " does not exist");
        return;
      }

      if (prodReview.getProduct().getId().longValue() != id.longValue()) {
        response.sendError(404, "Product review with id " + reviewId + " does not exist");
        return;
      }

      productCommonFacade.deleteReview(prodReview, merchantStore, language);

    } catch (Exception e) {
      LOGGER.error("Error while deleting product review", e);
      try {
        response.sendError(503, "Error while deleting product review" + e.getMessage());
      } catch (Exception ignore) {
      }

      return;
    }
  }
}
