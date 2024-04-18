package com.salesmanager.core.business.services.alibaba.category;

import com.salesmanager.core.business.alibaba.fenxiao.crossborder.param.*;

public interface AlibabaCategoryService {

    CategoryTranslationGetByIdCategory getCategoryById(Long categoryId, String language);
}
