package com.salesmanager.shop.store.api.v1.product;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.constants.QuestionType;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.PersistableProductQna;
import com.salesmanager.shop.model.catalog.product.PersistableProductQnaReply;
import com.salesmanager.shop.model.catalog.product.PersistableProductReview;
import com.salesmanager.shop.model.catalog.product.ReadableProductQna;
import com.salesmanager.shop.model.catalog.product.ReadableProductQnaList;
import com.salesmanager.shop.store.controller.product.facade.ProductCommonFacade;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v1")
public class ProductQnaApi {
	
	@Inject 
	private ProductCommonFacade productCommonFacade;
	
	@GetMapping(value = {"/auth/products/{id}/qnas", "/private/products/{id}/qnas"})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "questingType", dataType = "String"),
		@ApiImplicitParam(name = "checkSecret", dataType = "boolean", defaultValue = "false"),
		@ApiImplicitParam(name = "checkSelf", dataType = "boolean", defaultValue = "false"),
		@ApiImplicitParam(name = "customerId", dataType = "Integer"),
		@ApiImplicitParam(name = "category", dataType = "String"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ReadableProductQnaList getAll(
			@PathVariable final Long id,
			@ApiIgnore MerchantStore merchantStore,
			@RequestParam(value = "page", required = false) Integer page,
			@RequestParam(value = "count", required = false) Integer count,
			@RequestParam(value = "checkSecret", required = false) boolean checkSecret,
			@RequestParam(value = "checkSelf", required = false) boolean checkSelf,
			@RequestParam(value = "customerId", required = false) Integer customerId,
			@RequestParam(value = "category", required = false) String category,
			@ApiIgnore Language language,
			HttpServletResponse response,
			HttpServletRequest request
			) {
		
		try {
			// product exist
//			Product product = productService.getById(id);
//			
//			if (product == null) {
//				response.sendError(404, "Product id " + id + " does not exists");
//				return null;
//			}
			
			if(page == null) {
				page = 0;
			}
			if(count == null) {
				count = Integer.MAX_VALUE;
			}
			
			Pageable pageRequest = PageRequest.of(page, count, Sort.by(Sort.Order.desc("QNA_DATE")));
			ReadableProductQnaList readableQnaList =  productCommonFacade.getProductQnaList(id, checkSecret, checkSelf, customerId, category, pageRequest, merchantStore, language);
			return readableQnaList;
						
		} catch (Exception e) {
			// TODO: handle exception
			return null;
		}
		
	}
	
	@GetMapping(value = {"/auth/products/{id}/qna/{qnaId}", "/private/products/{id}/qna/{qnaId}"})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ReadableProductQna getQna(
			@PathVariable final Long id,
			@PathVariable final Long qnaId,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletResponse response,
			HttpServletRequest request
			) throws ConversionException {
		try {
			ReadableProductQna readableQna = productCommonFacade.getProductQna(qnaId, merchantStore, language); 
			return readableQna;
		} catch (Exception e) {
			// TODO: handle exception
			throw e; 
		}
		
	}
	
	@PostMapping(value = {"/auth/products/{id}/qna", "/private/products/{id}/qna"})
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ResponseEntity<?> create(
			@PathVariable final Long id,
			@Valid @RequestBody PersistableProductQna qna,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		try {
			qna.setProductId(id);
			productCommonFacade.saveOrUpdateQna(qna, merchantStore, language);
			
			return ResponseEntity.ok(Void.class);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving library files "+e.getMessage());
		}
		
	}
	
	@PostMapping(value = {"/auth/products/{id}/qna/{qnaId}/reply", "/private/products/{id}/qna/{qnaId}/reply"})
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ResponseEntity<?> createReply(
			@PathVariable final Long id,
			@PathVariable final Long qnaId,
			@Valid @RequestBody PersistableProductQnaReply reply,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		
		try {
			productCommonFacade.saveReply(qnaId, reply, merchantStore, language);
			return ResponseEntity.ok(Void.class);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving library files "+e.getMessage());
		}
	}
	
	@PutMapping(value = {"/auth/products/{id}/qna/{qnaId}", "/private/products/{id}/qna/{qnaId}"})
	@ResponseStatus(HttpStatus.CREATED)
	@ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ResponseEntity<?> createReply(
			@PathVariable final Long id,
			@PathVariable final Long qnaId,
			@Valid @RequestBody PersistableProductQna qna,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletResponse response,
			HttpServletRequest request) throws Exception {
		
		try {
			qna.setProductId(id);
			qna.setId(qnaId);
			productCommonFacade.saveOrUpdateQna(qna, merchantStore, language);
			return ResponseEntity.ok(Void.class);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving library files "+e.getMessage());
		}
		
		
	}
	
	@DeleteMapping(value = {"/auth/products/{id}/qna/{qnaId}", "/private/products/{id}/qna/{qnaId}"})
	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@ApiImplicitParams({
		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
	})
	public ResponseEntity<?> delete(
			@PathVariable final Long id,
			@PathVariable final Long qnaId,
			@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language,
			HttpServletResponse response,
			HttpServletRequest request
			) {
		try {
			productCommonFacade.deleteQna(qnaId);
			return ResponseEntity.ok(Void.class);
		} catch (Exception e) {
			// TODO: handle exception
			return ResponseEntity.badRequest().body("Exception when saving library files "+e.getMessage());
		}
		
	}
}
