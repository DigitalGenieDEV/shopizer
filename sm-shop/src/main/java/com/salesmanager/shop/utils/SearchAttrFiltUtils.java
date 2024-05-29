package com.salesmanager.shop.utils;

import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.search.ReadableAttrFiltAttrKv;
import com.salesmanager.shop.model.search.ReadableAttrFiltKv;
import com.salesmanager.shop.model.search.ReadableAttrForFilt;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component("searchAttrFiltUtils")
public class SearchAttrFiltUtils {

    @Inject
    private CategoryService categoryService;

    @Inject
    private ProductAttributeService productAttributeService;

    @Inject
    private ProductOptionValueService productOptionValueService;

    private static final String FILT_KEY_CATEGORY = "category";

    private static final String FILT_KEY_PLACE = "product_origin";

    private static final String FILT_KEY_PRICE = "price";

    private static final String FILT_KEY_PROD_TYPE= "product_type";

    private static final String FILT_KEY_ATTR_PREFIX = "attr_";

    public ReadableAttrForFilt getAttrFilt(Map<String, List<String>> attrForFilt, MerchantStore merchantStore, Language language) {
        ReadableAttrForFilt readableAttrForFilt = new ReadableAttrForFilt();

        List<ReadableAttrFiltKv> categorys = getAttrFiltCategorys(attrForFilt, merchantStore, language);
        if (categorys != null) {
            readableAttrForFilt.setCategory(categorys);
        }

        List<ReadableAttrFiltKv> productOrigin = getAttrFiltOps(attrForFilt, merchantStore, language);
        if (productOrigin != null) {
            readableAttrForFilt.setProductOrigin(productOrigin);
        }

        List<ReadableAttrFiltKv> price = getAttrFiltPrice(attrForFilt);
        if (price != null) {
            readableAttrForFilt.setPrice(price);
        }

        List<ReadableAttrFiltKv> prodType = getAttrFiltProdType(attrForFilt);
        if (prodType != null) {
            readableAttrForFilt.setProductType(prodType);
        }

        List<ReadableAttrFiltAttrKv> attrs = getAttrFiltAttrs(attrForFilt, merchantStore, language);
        if (attrs != null) {
            readableAttrForFilt.setAttrs(attrs);
        }

        return readableAttrForFilt;
    }

    // 查询过滤条件-分类
    private List<ReadableAttrFiltKv> getAttrFiltCategorys(Map<String, List<String>> attrForFilt, MerchantStore merchantStore, Language language) {
        List<String> categoryIds = attrForFilt.get(FILT_KEY_CATEGORY);

        if (categoryIds == null) {
            return null;
        }

        return categoryIds.stream().map(categoryId -> getCategoryAttrFiltKv(Long.valueOf(categoryId), merchantStore, language))
                .filter(readableAttrFiltKv -> readableAttrFiltKv != null).collect(Collectors.toList());
    }

    // 查询过滤条件-产地
    private List<ReadableAttrFiltKv> getAttrFiltOps(Map<String, List<String>> attrForFilt, MerchantStore merchantStore, Language language) {
        List<String> ops = attrForFilt.get(FILT_KEY_PLACE);

        if (ops == null) {
            return null;
        }


        return ops.stream().map(optionValueId -> getOptionValueAttrFiltKv(Long.valueOf(optionValueId), merchantStore, language))
                .filter(readableAttrFiltKv -> readableAttrFiltKv != null)
                .collect(Collectors.toList());
    }

    // 查询过滤条件-价格
    private List<ReadableAttrFiltKv> getAttrFiltPrice(Map<String, List<String>> attrForFilt) {
        List<String> price = attrForFilt.get(FILT_KEY_PRICE);

        if (price == null) {
            return null;
        }


        return price.stream().map(p -> new ReadableAttrFiltKv(p, Double.valueOf(p)))
                .filter(readableAttrFiltKv -> readableAttrFiltKv != null)
                .collect(Collectors.toList());
    }

    // 查询过滤条件-商品类型
    private List<ReadableAttrFiltKv> getAttrFiltProdType(Map<String, List<String>> attrForFilt) {
        List<String> prodType = attrForFilt.get(FILT_KEY_PROD_TYPE);

        if (prodType == null) {
            return null;
        }

        return prodType.stream().map(pt -> new ReadableAttrFiltKv(pt, pt))
                .filter(readableAttrFiltKv -> readableAttrFiltKv != null)
                .collect(Collectors.toList());
    }

    // 查询过滤条件-属性
    private List<ReadableAttrFiltAttrKv> getAttrFiltAttrs(Map<String, List<String>> attrForFilt, MerchantStore merchantStore, Language language) {

        List<ReadableAttrFiltAttrKv> kvs = new ArrayList<>();
        Set<Map.Entry<String, List<String>>> entrySets = attrForFilt.entrySet();

        for (Map.Entry<String, List<String>> entry : entrySets) {
            if (entry.getKey().startsWith(FILT_KEY_ATTR_PREFIX)) {
                ReadableAttrFiltAttrKv attrKv = new ReadableAttrFiltAttrKv();

                ReadableAttrFiltKv attrName = getAttibuteAttrFiltKv(entry.getKey(), merchantStore, language);

                List<ReadableAttrFiltKv> attrValues = entry.getValue().stream()
                        .map(optionValueId -> getOptionValueAttrFiltKv(Long.valueOf(optionValueId), merchantStore, language))
                        .filter(readableAttrFiltKv -> readableAttrFiltKv != null)
                        .collect(Collectors.toList());

                attrKv.setAttrName(attrName);
                attrKv.setAttrValues(attrValues);

                kvs.add(attrKv);
            }
        }

        return kvs;
    }

    public ReadableAttrFiltKv getCategoryAttrFiltKv(long categoryId, MerchantStore merchantStore, Language language) {
        try {
            Category category = categoryService.getById(merchantStore, categoryId);

            Set<CategoryDescription> categoryDescriptions = category.getDescriptions();

            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
            kv.setValue(category.getId());

            // 查询多语言
            for (CategoryDescription categoryDescription : categoryDescriptions) {
                if (categoryDescription.getLanguage().getId() == language.getId()) {
                    kv.setLabel(categoryDescription.getName());
                    break;
                }
            }

            return kv;
        } catch (Exception e) {
        }

        return null;
    }

    private ReadableAttrFiltKv getOptionValueAttrFiltKv(long optionValueId, MerchantStore merchantStore, Language language) {
        try {
            ProductOptionValue optionValue = productOptionValueService.getById(merchantStore, optionValueId);

            Set<ProductOptionValueDescription> optionValueDescriptions = optionValue.getDescriptions();

            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
            kv.setValue(optionValue.getId());

            for (ProductOptionValueDescription optionValueDescription : optionValueDescriptions) {
                if (optionValueDescription.getLanguage().getId() == language.getId()) {
                    kv.setLabel(optionValueDescription.getName());
                    break;
                }
            }

            return kv;
        } catch (Exception e) {
        }


        return null;
    }

    private ReadableAttrFiltKv getAttibuteAttrFiltKv(String attrIdWithPrefix, MerchantStore merchantStore, Language language) {
        try {
            ProductAttribute productAttribute = productAttributeService.getById(Long.valueOf(attrIdWithPrefix.replace(FILT_KEY_ATTR_PREFIX, "")));
            ProductOption productOption = productAttribute.getProductOption();

            Set<ProductOptionDescription> productOptionDescriptions = productOption.getDescriptions();

            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
            kv.setValue(attrIdWithPrefix);

            for (ProductOptionDescription productOptionDescription : productOptionDescriptions) {
                if (productOptionDescription.getLanguage().getId() == language.getId()) {
                    kv.setLabel(productOptionDescription.getName());
                }
            }

            return kv;
        } catch (Exception e) {
        }


        return null;
    }

}
