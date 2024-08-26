package com.salesmanager.shop.store.api.v2.product;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salesmanager.core.model.catalog.product.PublishWayEnums;
import com.salesmanager.shop.init.data.InitializationLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private static final Logger LOGGER = LoggerFactory.getLogger(Product1688Api.class);


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

		Set<Long> processedProductIds = ConcurrentHashMap.newKeySet();

		Map<String, Object> resultMap = new HashMap<>();

		// 存储异常的itemId和对应的错误信息
		Queue<Long> errorQueue = new ConcurrentLinkedQueue<>();

		int corePoolSize = 100;  // 核心线程数
		int maxPoolSize = 100;   // 最大线程数
		int queueCapacity = 400; // 有界队列大小

		AtomicInteger importCount = new AtomicInteger();
		AtomicInteger queryCount = new AtomicInteger();


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
					alibabaProductSearchKeywordQueryParam.setSort("{\"price\":\"asc\"}");
					alibabaProductSearchKeywordQueryParam.setCategoryId(keyword.getCategoryId1688());
					alibabaProductSearchKeywordQueryParam.setKeyword(keyword.getKeyword());

					ReadableProductPageInfo searchData = retrySearch(alibabaProductSearchKeywordQueryParam);
					int totalPage = (int) Math.ceil((double) searchData.getTotalRecords() / searchData.getPageSize());

					// 创建一个 CompletionService，用于并行执行查询任务并获取结果
					CompletionService<ReadableProductPageInfo> completionService = new ExecutorCompletionService<>(executorService);

					// 提交查询任务
					for (int i = 1; i <= totalPage; i++) {
						final int pageIndex = i;
						completionService.submit(() -> {
							AlibabaProductSearchKeywordQueryParam pageParam = new AlibabaProductSearchKeywordQueryParam();
							pageParam.setBeginPage(pageIndex);
							pageParam.setPageSize(paramData.getPageSize());
							pageParam.setCountry(paramData.getCountry());
							pageParam.setKeyword(keyword.getKeyword());
							pageParam.setCategoryId(keyword.getCategoryId1688());
							pageParam.setSort("{\"price\":\"asc\"}");
							return retrySearch(pageParam);
						});
					}

					// 收集所有查询结果
					List<ReadableProductPageInfo> allSearchResults = new ArrayList<>();
					try {
						for (int i = 1; i <= totalPage; i++) {
							Future<ReadableProductPageInfo> future = completionService.take(); // 阻塞等待任务完成
							ReadableProductPageInfo searchPageData = future.get();
							queryCount.addAndGet(searchPageData.getPageSize());
							allSearchResults.add(searchPageData);
						}
					} catch (InterruptedException | ExecutionException e) {
						e.printStackTrace();
						// 错误处理
					}

					// 多线程执行导入任务
					for (ReadableProductPageInfo searchPageData : allSearchResults) {
						if (searchPageData.getData() != null && !searchPageData.getData().isEmpty()) {
							for (AlibabaProductSearchQueryModelProduct data : searchPageData.getData()) {
								Future<?> future = executorService.submit(() -> {
									try {
										importCount.incrementAndGet();
										if (processedProductIds.contains(data.getOfferId())) {
											System.out.println("++++++++++++++++++++++++++++重复处理的商品ID: " + data.getOfferId());
										} else {
											processedProductIds.add(data.getOfferId());
//											//处理商品导入
											alibabaProductFacade.adminBatchImportProduct(
													data.getOfferId(), language.getCode(), merchantStore,
													keyword.getCategoryId() == null ? null : Lists.newArrayList(keyword.getCategoryId()), PublishWayEnums.IMPORT_BY_1688);
										}

									} catch (Exception e) {
										// 捕获异常并记录
										LOGGER.error(data.getOfferId() + "error", e);
										errorQueue.add(data.getOfferId());
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

			try {
				System.out.println("全部异常数据："+ JSON.toJSONString(errorQueue));
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return resultMap;
	}




	// 重试方法
	private ReadableProductPageInfo retrySearch(AlibabaProductSearchKeywordQueryParam param) throws ServiceException {
		int retryCount = 0;
		while (retryCount < 999999999) { // 最大重试次数
			try {
				System.out.println("search param:"+JSON.toJSON(param));
				return alibabaProductFacade.searchProductByKeywords(param);
			} catch (Exception e) {
//				if ("403".equals(e.getMessage())) {
				//现在所有异常都重试
					try {
						Thread.sleep(10000); // 等待10秒
						System.out.println("等待中。。。。。。。");
					} catch (InterruptedException ex) {
						Thread.currentThread().interrupt();
						throw new RuntimeException(ex);
					}
					retryCount++;
//				} else {
//					throw e; // 其他异常不重试
//				}
			}
		}
		throw new RuntimeException("Failed to retrieve data after retries");
	}


}
