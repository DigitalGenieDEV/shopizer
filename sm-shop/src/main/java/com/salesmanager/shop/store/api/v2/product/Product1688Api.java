package com.salesmanager.shop.store.api.v2.product;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

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

public Map<String, Object> oldSearchProductByKeywords(@ApiIgnore MerchantStore merchantStore,
			@ApiIgnore Language language, @Valid @RequestBody Product1688AddEntity paramData) throws ServiceException {
		Map<String, Object> resultMap = new HashMap<String, Object>();

		// 记录开始时间
		long startTime = System.currentTimeMillis();
		System.out.println("开始时间: " + startTime);


		int totalPage = 0;
		try {
			if (paramData.getKeywordList().size() > 0) {
				for (Product1688ListEntity keyword : paramData.getKeywordList()) {
					List<Long> offersId = new ArrayList<Long>();
					AlibabaProductSearchKeywordQueryParam alibabaProductSearchKeywordQueryParam = new AlibabaProductSearchKeywordQueryParam();
					alibabaProductSearchKeywordQueryParam.setBeginPage(paramData.getBeginPage());
					alibabaProductSearchKeywordQueryParam.setPageSize(paramData.getPageSize());
					alibabaProductSearchKeywordQueryParam.setCountry(paramData.getCountry());
					alibabaProductSearchKeywordQueryParam.setCategoryId(keyword.getCategoryId1688());
					alibabaProductSearchKeywordQueryParam.setKeyword(keyword.getKeyword());
					ReadableProductPageInfo searchData = alibabaProductFacade
							.searchProductByKeywords(alibabaProductSearchKeywordQueryParam);
					totalPage = (int) Math.ceil((double) searchData.getTotalRecords() / searchData.getPageSize());
					System.out.println("totalPage:" + totalPage);

					if (searchData.getData().size() > 0) {
						for (AlibabaProductSearchQueryModelProduct data : searchData.getData()) {
							alibabaProductFacade.importProduct(Collections.singletonList(data.getOfferId()), language.getCode(), merchantStore,
									keyword.getCategoryId() == null ? null : Lists.newArrayList(keyword.getCategoryId()));
						}
					}
					for (int i = 2; i <= totalPage; i++) {
						System.out.println("page:" + i);
						AlibabaProductSearchKeywordQueryParam insertAddParam = new AlibabaProductSearchKeywordQueryParam();
						insertAddParam.setBeginPage(i);
						insertAddParam.setPageSize(paramData.getPageSize());
						insertAddParam.setCountry(paramData.getCountry());
						insertAddParam.setCategoryId(keyword.getCategoryId1688());
						insertAddParam.setKeyword(keyword.getKeyword());
						ReadableProductPageInfo searchOtherData = alibabaProductFacade
								.searchProductByKeywords(insertAddParam);
						if (searchOtherData.getData() != null && searchOtherData.getData().size() > 0) {
							for (AlibabaProductSearchQueryModelProduct data2 : searchOtherData.getData()) {
								alibabaProductFacade.importProduct(Collections.singletonList(data2.getOfferId()), language.getCode(), merchantStore,
										keyword.getCategoryId() == null ? null : Lists.newArrayList(keyword.getCategoryId()));
							}
						}
					}
					System.out.println("offersIdCount"+offersId.size());

				}
			}
			// 记录结束时间
			long endTime = System.currentTimeMillis();
			System.out.println("结束时间: " + endTime);
			System.out.println("总耗时: " + (endTime - startTime) + " 毫秒");

			resultMap.put("resultCode", 200);
			resultMap.put("errMsg", "");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", 200);
			resultMap.put("errMsg", e.getMessage());
		}

		return resultMap;

	}


	@ResponseStatus(HttpStatus.OK)
	@ResponseBody
	@RequestMapping(value = "/product/add1688", method = RequestMethod.POST)
	@ApiOperation(httpMethod = "POST", value = "search product by keywords", notes = "search product by keywords", produces = "application/json", response = ReadableProductPageInfo.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko") })
	public Map<String, Object> searchProductByKeywords1(@ApiIgnore MerchantStore merchantStore,
													   @ApiIgnore Language language, @Valid @RequestBody Product1688AddEntity paramData) throws ServiceException {

		// 记录开始时间
		long startTime = System.currentTimeMillis();
		System.out.println("开始时间: " + startTime);

		Map<String, Object> resultMap = new HashMap<>();
		ExecutorService executorService = Executors.newFixedThreadPool(10); // 创建一个固定大小的线程池
		List<Future<?>> futures = new ArrayList<>();

		int totalPage = 0;

		try {
			if (paramData.getKeywordList().size() > 0) {
				for (Product1688ListEntity keyword : paramData.getKeywordList()) {
					AlibabaProductSearchKeywordQueryParam alibabaProductSearchKeywordQueryParam = new AlibabaProductSearchKeywordQueryParam();
					alibabaProductSearchKeywordQueryParam.setBeginPage(paramData.getBeginPage());
					alibabaProductSearchKeywordQueryParam.setPageSize(paramData.getPageSize());
					alibabaProductSearchKeywordQueryParam.setCountry(paramData.getCountry());
					alibabaProductSearchKeywordQueryParam.setCategoryId(keyword.getCategoryId1688());
					alibabaProductSearchKeywordQueryParam.setKeyword(keyword.getKeyword());
					System.out.println("keyword.getCategoryId1688()"+keyword.getCategoryId1688());
					System.out.println("alibabaProductSearchKeywordQueryParam.setCategoryId()"+alibabaProductSearchKeywordQueryParam.getCategoryId());
					ReadableProductPageInfo searchData = alibabaProductFacade
							.searchProductByKeywords(alibabaProductSearchKeywordQueryParam);
					totalPage = (int) Math.ceil((double) searchData.getTotalRecords() / searchData.getPageSize());
					System.out.println("totalPage:" + totalPage);

					if (searchData.getData().size() > 0) {
						for (AlibabaProductSearchQueryModelProduct data : searchData.getData()) {
							Future<?> future = executorService.submit(() -> {
								try {
									alibabaProductFacade.importProduct(
											Collections.singletonList(data.getOfferId()), language.getCode(), merchantStore,
											keyword.getCategoryId() == null ? null : Lists.newArrayList(keyword.getCategoryId()));
								} catch (ServiceException e) {
									throw new RuntimeException(e);
								}
							});
							futures.add(future);

							// 每10个任务一批处理
							if (futures.size() == 10) {
								for (Future<?> f : futures) {
									f.get();  // 等待当前这批任务完成
								}
								futures.clear();  // 清空列表以便继续下一批任务
							}
						}
					}

					for (int i = 2; i <= totalPage; i++) {
						System.out.println("page:" + i);
						AlibabaProductSearchKeywordQueryParam insertAddParam = new AlibabaProductSearchKeywordQueryParam();
						insertAddParam.setBeginPage(i);
						insertAddParam.setPageSize(paramData.getPageSize());
						insertAddParam.setCountry(paramData.getCountry());
						insertAddParam.setKeyword(keyword.getKeyword());
						insertAddParam.setCategoryId(keyword.getCategoryId1688());
						ReadableProductPageInfo searchOtherData = alibabaProductFacade
								.searchProductByKeywords(insertAddParam);
						if (searchOtherData.getData() != null && searchOtherData.getData().size() > 0) {
							for (AlibabaProductSearchQueryModelProduct data2 : searchOtherData.getData()) {
								Future<?> future = executorService.submit(() -> {
									try {
										alibabaProductFacade.importProduct(
												Collections.singletonList(data2.getOfferId()), language.getCode(), merchantStore,
												keyword.getCategoryId() == null ? null : Lists.newArrayList(keyword.getCategoryId()));
									} catch (ServiceException e) {
										throw new RuntimeException(e);
									}
								});
								futures.add(future);

								// 每10个任务一批处理
								if (futures.size() == 10) {
									for (Future<?> f : futures) {
										f.get();  // 等待当前这批任务完成
									}
									futures.clear();  // 清空列表以便继续下一批任务
								}
							}
						}
					}
				}
			}

			// 处理最后剩下不足10个任务的批次
			if (!futures.isEmpty()) {
				for (Future<?> f : futures) {
					f.get();
				}
			}

			resultMap.put("resultCode", 200);
			resultMap.put("errMsg", "");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", 500);
			resultMap.put("errMsg", e.getMessage());
		} finally {
			executorService.shutdown();
			// 记录结束时间
			long endTime = System.currentTimeMillis();
			System.out.println("结束时间: " + endTime);
			System.out.println("总耗时: " + (endTime - startTime) + " 毫秒");
		}

		return resultMap;
	}






}
