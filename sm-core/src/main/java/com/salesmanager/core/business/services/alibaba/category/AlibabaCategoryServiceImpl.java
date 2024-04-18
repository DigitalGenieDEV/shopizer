package com.salesmanager.core.business.services.alibaba.category;

import com.alibaba.fastjson.JSON;
import com.salesmanager.core.business.alibaba.ApiExecutorSingleton;
import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;
import com.salesmanager.core.business.alibaba.rawsdk.ApiExecutor;
import com.salesmanager.core.business.services.alibaba.product.AlibabaProductServiceImpl;
import com.salesmanager.core.constants.ApiFor1688Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AlibabaCategoryServiceImpl implements AlibabaCategoryService{

    private static final Logger LOGGER = LoggerFactory.getLogger(AlibabaProductServiceImpl.class);

    @Override
    public CategoryTranslationGetByIdCategory getCategoryById(Long categoryId, String language) {
        ApiExecutor apiExecutor = ApiExecutorSingleton.getInstance();
        CategoryTranslationGetByIdParam param = new  CategoryTranslationGetByIdParam();
        param.setCategoryId(categoryId);
        param.setLanguage(language);
        CategoryTranslationGetByIdResult result = apiExecutor.execute(param, ApiFor1688Constants.ACCESS_TOKEN).getResult();
        LOGGER.info("AlibabaProductServiceImpl searchKeyword result" + JSON.toJSON(result));
        if (!result.getResult().getSuccess()){
            throw new RuntimeException("AlibabaProductServiceImpl searchKeyword error" + JSON.toJSON(param));
        }
        return result.getResult().getResult();
    }}
