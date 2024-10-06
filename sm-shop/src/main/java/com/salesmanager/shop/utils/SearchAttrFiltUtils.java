package com.salesmanager.shop.utils;

import com.salesmanager.core.business.services.catalog.category.CategoryService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductOptionValueService;
import com.salesmanager.core.model.catalog.category.Category;
import com.salesmanager.core.model.catalog.category.CategoryDescription;
import com.salesmanager.core.model.catalog.product.attribute.*;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.search.ReadableAttrFiltAttrKv;
import com.salesmanager.shop.model.search.ReadableAttrFiltKv;
import com.salesmanager.shop.model.search.ReadableAttrForFilt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.*;
import java.util.stream.Collectors;

@Component("searchAttrFiltUtils")
public class SearchAttrFiltUtils {

    protected final Logger LOG = LoggerFactory.getLogger(SearchAttrFiltUtils.class);

    @Inject
    private CategoryService categoryService;

//    @Inject
//    private ProductAttributeService productAttributeService;
    //tmpzk2
    @Inject
    private ProductOptionService productOptionService;

    @Inject
    private ProductOptionValueService productOptionValueService;

    private static final String FILT_KEY_CATEGORY = "category";

    private static final String FILT_KEY_PLACE = "product_origin";

    private static final String FILT_KEY_PRICE = "price";

    private static final String FILT_KEY_PROD_TYPE= "product_type";

    private static final String FILT_KEY_ATTR_PREFIX = "attr_";

    public ReadableAttrForFilt getAttrFilt(Map<String, List<String>> attrForFilt, MerchantStore merchantStore, Language language) {
        ReadableAttrForFilt readableAttrForFilt = new ReadableAttrForFilt();

        //tmpzk1
//        long startTime = System.currentTimeMillis();

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
        //tmpzk1
//        long endTime = System.currentTimeMillis();
//        System.out.println("getAttrFilt-1: " + (endTime - startTime) + " ms");

        //tmpzk1
//        startTime = System.currentTimeMillis();

        List<ReadableAttrFiltAttrKv> attrs = getAttrFiltAttrs(attrForFilt, merchantStore, language);
        if (attrs != null) {
            readableAttrForFilt.setAttrs(attrs);
        }

        //tmpzk1
//        endTime = System.currentTimeMillis();
//        System.out.println("getAttrFilt-2: " + (endTime - startTime) + " ms");

        return readableAttrForFilt;
    }

    // 查询过滤条件-分类
    private List<ReadableAttrFiltKv> getAttrFiltCategorys(Map<String, List<String>> attrForFilt, MerchantStore merchantStore, Language language) {
        List<String> categoryIds = attrForFilt.get(FILT_KEY_CATEGORY);

        if (categoryIds == null) {
            return null;
        }

        //tmpzk2
        List<Long> categoryIdsWithLong = categoryIds.stream().map(Long::parseLong).collect(Collectors.toList());
        List<Category> categoryList = categoryService.getByIds(language.getId(), categoryIdsWithLong);

        List<ReadableAttrFiltKv> rltList= new ArrayList<>();
        for(Category c : categoryList){
            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
            kv.setValue(c.getId());
            Optional<CategoryDescription> firstElement = c.getDescriptions().stream().findFirst();
            if(firstElement.isPresent()){
                kv.setLabel(firstElement.get().getName());
            }else {
                kv.setLabel("Unkown");
            }
            rltList.add(kv);
        }

        return rltList;
//        return categoryIds.stream().map(categoryId -> getCategoryAttrFiltKv(Long.valueOf(categoryId), merchantStore, language))
//                .filter(readableAttrFiltKv -> readableAttrFiltKv != null).collect(Collectors.toList());
    }

    // 查询过滤条件-产地
    private List<ReadableAttrFiltKv> getAttrFiltOps(Map<String, List<String>> attrForFilt, MerchantStore merchantStore, Language language) {
        List<String> ops = attrForFilt.get(FILT_KEY_PLACE);

        if (ops == null) {
            return null;
        }

        //tmpzk2
        List<Long> opsIds = ops.stream().map(Long::parseLong).collect(Collectors.toList());

        List<ProductOptionValue> opsList = productOptionValueService.getByIds(opsIds, language);

        List<ReadableAttrFiltKv> rltList= new ArrayList<>();
        for(ProductOptionValue p : opsList){
            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
            kv.setValue(p.getId());
            Optional<ProductOptionValueDescription> firstElement = p.getDescriptions().stream().findFirst();
            if(firstElement.isPresent()){
                kv.setLabel(firstElement.get().getName());
            }else {
                kv.setLabel("Unkown");
            }
            rltList.add(kv);
        }

        return rltList;
//        return ops.stream().map(optionValueId -> getOptionValueAttrFiltKv(Long.valueOf(optionValueId), merchantStore, language))
//                .filter(readableAttrFiltKv -> readableAttrFiltKv != null)
//                .collect(Collectors.toList());
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

        List<String> attrIdsWithPrefix = new ArrayList<>();
        for (Map.Entry<String, List<String>> entry : entrySets) {
            if (entry.getKey().startsWith(FILT_KEY_ATTR_PREFIX)) {
                attrIdsWithPrefix.add(entry.getKey());
            }
        }

        List<Long> attrIds = attrIdsWithPrefix.stream()
                .map(attrIdWithPrefix -> Long.valueOf(attrIdWithPrefix.replace(FILT_KEY_ATTR_PREFIX, ""))).collect(Collectors.toList());

//        List<ProductAttribute> productAttributes = productAttributeService.getByIdList(attrIds);
//        for (ProductAttribute productAttribute : productAttributes) {
//            try {
//                ReadableAttrFiltKv attrName = getAttibuteAttrFiltKv(productAttribute, language);
//
//                List<ReadableAttrFiltKv> attrValues = attrForFilt.get(FILT_KEY_ATTR_PREFIX + productAttribute.getId()).stream()
//                        .map(optionValueId -> getOptionValueAttrFiltKv(Long.valueOf(optionValueId), merchantStore, language))
//                        .filter(readableAttrFiltKv -> readableAttrFiltKv != null)
//                        .collect(Collectors.toList());
//                ReadableAttrFiltAttrKv attrKv = new ReadableAttrFiltAttrKv();
//
//                attrKv.setAttrName(attrName);
//                attrKv.setAttrValues(attrValues);
//
//                kvs.add(attrKv);
//            } catch (Exception e) {
//                LOG.error("attr kv exception, product attribute Id = [" + productAttribute.getId() + "]", e);
//            }
//        }

        //tmpzk2
        List<ProductOption> productOptions = productOptionService.getByIds(attrIds, language);
        for(ProductOption po : productOptions){
            List<Long> optionValueIdslist = attrForFilt.get(FILT_KEY_ATTR_PREFIX + po.getId()).stream().map(Long::parseLong).collect(Collectors.toList());
            List<ProductOptionValue> optionValueList = productOptionValueService.getByIds(optionValueIdslist, language);

            ReadableAttrFiltAttrKv attrKv = new ReadableAttrFiltAttrKv();

            ReadableAttrFiltKv kvOption = new ReadableAttrFiltKv();
            kvOption.setValue(po.getId());
            Optional<ProductOptionDescription> firstOptionElement = po.getDescriptions().stream().findFirst();
            if(firstOptionElement.isPresent()){
                kvOption.setLabel(firstOptionElement.get().getName());
            }else {
                kvOption.setLabel("Unkown");
            }
            attrKv.setAttrName(kvOption);

            List<ReadableAttrFiltKv> optionValues = new ArrayList<>();
            for(ProductOptionValue pov : optionValueList){
                ReadableAttrFiltKv kvValue = new ReadableAttrFiltKv();
                kvValue.setValue(pov.getId());
                Optional<ProductOptionValueDescription> firstValueElement = pov.getDescriptions().stream().findFirst();
                if(firstValueElement.isPresent()){
                    kvValue.setLabel(firstValueElement.get().getName());
                }else {
                    kvValue.setLabel("Unkown");
                }
                optionValues.add(kvValue);
            }
            attrKv.setAttrValues(optionValues);
            kvs.add(attrKv);
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

//            Category category = categoryService.getById(language.getId(), categoryId);
//            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
//            kv.setValue(category.getId());
//            Optional<CategoryDescription> firstElement = category.getDescriptions().stream().findFirst();
//            if(firstElement.isPresent()){
//                kv.setLabel(firstElement.get().getName());
//            }else {
//                kv.setLabel("Unkown");
//            }

            return kv;
        } catch (Exception e) {
            LOG.error("get category attr filt kv exception", e);
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

//            ProductOptionValue optionValue = productOptionValueService.getById(optionValueId, language);
//
//            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
//            kv.setValue(optionValue.getId());
//
//            Optional<ProductOptionValueDescription> firstElement = optionValue.getDescriptions().stream().findFirst();
//            if(firstElement.isPresent()){
//                kv.setLabel(firstElement.get().getName());
//            }else {
//                kv.setLabel("Unkown");
//            }

            return kv;
        } catch (Exception e) {
            LOG.error("get option value attr filt exception, option value id = [" + optionValueId +  "]", e);
        }

        return null;
    }

    public ReadableAttrFiltKv getAttibuteAttrFiltKv(ProductAttribute productAttribute, Language language) {
        try {
//            ProductAttribute productAttribute = productAttributeService.getById(Long.valueOf(attrIdWithPrefix.replace(FILT_KEY_ATTR_PREFIX, "")));
            ProductOption productOption = productAttribute.getProductOption();

            Set<ProductOptionDescription> productOptionDescriptions = productOption.getDescriptions();

            ReadableAttrFiltKv kv = new ReadableAttrFiltKv();
            kv.setValue(FILT_KEY_ATTR_PREFIX + productAttribute.getId());

            for (ProductOptionDescription productOptionDescription : productOptionDescriptions) {
                if (productOptionDescription.getLanguage().getId() == language.getId()) {
                    kv.setLabel(productOptionDescription.getName());
                }
            }

            return kv;
        } catch (Exception e) {
            LOG.error("get product  attr filt kv exception", e);
        }


        return null;
    }

}
