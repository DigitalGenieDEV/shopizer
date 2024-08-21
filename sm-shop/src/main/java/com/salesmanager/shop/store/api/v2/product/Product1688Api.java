package com.salesmanager.shop.store.api.v2.product;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import com.salesmanager.core.model.catalog.product.PublishWayEnums;
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
	@ApiImplicitParams({
			@ApiImplicitParam(name = "store", dataType = "string", defaultValue = "DEFAULT"),
			@ApiImplicitParam(name = "lang", dataType = "string", defaultValue = "ko")
	})
	public Map<String, Object> searchProductByKeywords1(@ApiIgnore MerchantStore merchantStore,
														@ApiIgnore Language language, @Valid @RequestBody Product1688AddEntity paramData) throws ServiceException {

		long startTime = System.currentTimeMillis();
		System.out.println("开始时间: " + startTime);

		Map<String, Object> resultMap = new HashMap<>();

		int corePoolSize = 100;  // 核心线程数
		int maxPoolSize = 100;   // 最大线程数
		int queueCapacity = 200; // 有界队列大小

		AtomicInteger importCount = new AtomicInteger();
		AtomicInteger queryCount = new AtomicInteger();

		// 存储异常的itemId和对应的错误信息
		Map<Long, String> errorItems = new ConcurrentHashMap<>();

		// 创建一个有界队列，并自定义拒绝策略
		BlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(queueCapacity);
		RejectedExecutionHandler handler = (r, executor) -> {
			try {
				// 当队列满时，阻塞等待队列有空间
				executor.getQueue().put(r);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				throw new RejectedExecutionException("Task " + r.toString() + " rejected", e);
			}
		};

		ExecutorService executorService = new ThreadPoolExecutor(
				corePoolSize,
				maxPoolSize,
				60L,
				TimeUnit.SECONDS,
				queue,
				handler
		);

		List<Future<?>> futures = new ArrayList<>();

		try {
			if (paramData.getKeywordList().size() > 0) {
				for (Product1688ListEntity keyword : paramData.getKeywordList()) {
					AlibabaProductSearchKeywordQueryParam alibabaProductSearchKeywordQueryParam = new AlibabaProductSearchKeywordQueryParam();
					alibabaProductSearchKeywordQueryParam.setBeginPage(paramData.getBeginPage());
					alibabaProductSearchKeywordQueryParam.setPageSize(paramData.getPageSize());
					alibabaProductSearchKeywordQueryParam.setCountry(paramData.getCountry());
					alibabaProductSearchKeywordQueryParam.setCategoryId(keyword.getCategoryId1688());
					alibabaProductSearchKeywordQueryParam.setKeyword(keyword.getKeyword());

					ReadableProductPageInfo searchData = alibabaProductFacade
							.searchProductByKeywords(alibabaProductSearchKeywordQueryParam);
					int totalPage = (int) Math.ceil((double) searchData.getTotalRecords() / searchData.getPageSize());

					for (int i = 1; i <= totalPage; i++) {
						AlibabaProductSearchKeywordQueryParam pageParam = new AlibabaProductSearchKeywordQueryParam();
						pageParam.setBeginPage(i);
						pageParam.setPageSize(paramData.getPageSize());
						pageParam.setCountry(paramData.getCountry());
						pageParam.setKeyword(keyword.getKeyword());
						pageParam.setCategoryId(keyword.getCategoryId1688());

						ReadableProductPageInfo searchPageData = alibabaProductFacade.searchProductByKeywords(pageParam);

						queryCount.addAndGet(searchPageData.getPageSize());
						System.out.println("查询数量："+queryCount.get());

						if (searchPageData.getData() != null && !searchPageData.getData().isEmpty()) {
							for (AlibabaProductSearchQueryModelProduct data : searchPageData.getData()) {
								Future<?> future = executorService.submit(() -> {
									try {
										importCount.incrementAndGet();
										alibabaProductFacade.adminBatchImportProduct(
												data.getOfferId(), language.getCode(), merchantStore,
												keyword.getCategoryId() == null ? null : Lists.newArrayList(keyword.getCategoryId()), PublishWayEnums.BATCH_IMPORT_BY_1688);
									} catch (Exception e) {
										// 捕获异常并记录
										errorItems.put(data.getOfferId(), e.getMessage());
									}
								});
								futures.add(future);
							}
						}
					}
				}
			}

			// 等待所有任务完成
			for (Future<?> f : futures) {
				f.get();
			}

			resultMap.put("resultCode", 200);
			resultMap.put("errMsg", "");
		} catch (Exception e) {
			e.printStackTrace();
			resultMap.put("resultCode", 500);
			resultMap.put("errMsg", e.getMessage());
		} finally {
			executorService.shutdown();
			long endTime = System.currentTimeMillis();
			System.out.println("结束时间: " + endTime);
			System.out.println("总耗时: " + (endTime - startTime) + " 毫秒");
			System.out.println("查询总数："+queryCount.get());
			System.out.println("导入总数："+importCount.get());

			// 输出所有异常的itemId和错误信息
			if (!errorItems.isEmpty()) {
				System.out.println("以下商品导入时发生错误：");
				errorItems.forEach((itemId, errorMsg) -> {
					System.out.println("商品ID: " + itemId + ", 错误信息: " + errorMsg);
				});
			}
		}

		return resultMap;
	}






}
