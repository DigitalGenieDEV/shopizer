package com.salesmanager.shop.store.api.v2.product;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.google.common.collect.Lists;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchKeywordQueryParam;
import com.salesmanager.shop.model.catalog.product.product.alibaba.AlibabaProductSearchQueryModelProduct;
import com.salesmanager.shop.model.catalog.product.product.alibaba.ReadableProductPageInfo;
import com.salesmanager.shop.model.common.Product1688AddEntity;
import com.salesmanager.shop.model.common.Product1688ListEntity;
import com.salesmanager.shop.store.controller.product.facade.AlibabaProductFacade;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@RequestMapping("/api/v2")
@SwaggerDefinition(tags = { @Tag(name = "Product 1688 Add", description = "Product 1688 Add") })
public class Product1688Api {

	@Autowired
	private AlibabaProductFacade alibabaProductFacade;

	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/product/add1688", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "search product by keywords", notes = "search product by keywords", produces = "application/json", response = ReadableProductPageInfo.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public Map<String, Object> searchProductByKeywords(@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, @Valid @RequestBody Product1688AddEntity paramData) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();
	
		int totalPage = 0;
		try {
			if (paramData.getKeywordList().size() > 0) {
				for (Product1688ListEntity keyword : paramData.getKeywordList()) {
					List<Long> offersId = new ArrayList<Long>();
					AlibabaProductSearchKeywordQueryParam alibabaProductSearchKeywordQueryParam = new AlibabaProductSearchKeywordQueryParam();
					alibabaProductSearchKeywordQueryParam.setBeginPage(paramData.getBeginPage());
					alibabaProductSearchKeywordQueryParam.setPageSize(paramData.getPageSize());
					alibabaProductSearchKeywordQueryParam.setCountry(paramData.getCountry());
					alibabaProductSearchKeywordQueryParam.setKeyword(keyword.getKeyword());
					ReadableProductPageInfo searchData = alibabaProductFacade
							.searchProductByKeywords(alibabaProductSearchKeywordQueryParam);
					totalPage = (int) Math.ceil((double) searchData.getTotalRecords() / searchData.getPageSize());
					System.out.println("totalPage:" + totalPage);

					if (searchData.getData().size() > 0) {
						for (AlibabaProductSearchQueryModelProduct data : searchData.getData()) {
							offersId.add(data.getOfferId());
						}
					}
					for (int i = 2; i <= totalPage; i++) {
						System.out.println("page:" + i);
						AlibabaProductSearchKeywordQueryParam insertAddParam = new AlibabaProductSearchKeywordQueryParam();
						insertAddParam.setBeginPage(i);
						insertAddParam.setPageSize(paramData.getPageSize());
						insertAddParam.setCountry(paramData.getCountry());
						insertAddParam.setKeyword(keyword.getKeyword());
						ReadableProductPageInfo searchOtherData = alibabaProductFacade
								.searchProductByKeywords(insertAddParam);
						if (searchOtherData.getData() != null && searchOtherData.getData().size() > 0) {
							for (AlibabaProductSearchQueryModelProduct data2 : searchOtherData.getData()) {
								offersId.add(data2.getOfferId());
							}
						}
					}
					System.out.println("offersIdCount"+offersId.size());
					List<Long> longs = alibabaProductFacade.importProduct(offersId, language.getCode(), merchantStore,
							keyword.getCategoryId() == null ? null : Lists.newArrayList(keyword.getCategoryId()));
				}
			}

			resultMap.put("resultCode", 200);
			resultMap.put("errMsg", "");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", 200);
			resultMap.put("errMsg", e.getMessage());
		}

		return resultMap;

	}

}
