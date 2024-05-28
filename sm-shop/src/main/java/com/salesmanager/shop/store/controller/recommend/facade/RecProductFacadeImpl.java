package com.salesmanager.shop.store.controller.recommend.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.feature.ProductFeatureService;
import com.salesmanager.core.business.services.search.RecProductService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.recommend.*;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductVariantMapper;
import com.salesmanager.shop.model.recommend.*;
import com.salesmanager.shop.populator.recommend.ReadableRecProductPopulator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.utils.ImageFilePath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("recProductFacade")
public class RecProductFacadeImpl implements RecProductFacade{

    @Inject
    private RecProductService recProductService;

    @Inject
    private PricingService pricingService;

    @Autowired
    private ReadableMerchantStorePopulator readableMerchantStorePopulator;

    @Autowired
    private ReadableProductVariantMapper readableProductVariantMapper;

    @Autowired
    private ProductFeatureService productFeatureService;

    @Inject
    @Qualifier("img")
    private ImageFilePath imageUtils;

    @Override
    public ReadableRecProductList getRecGuessULike(RecGuessULikeRequest request, Language language) throws Exception {
        GuessULikeRequest guessULikeRequest = new GuessULikeRequest();
        guessULikeRequest.setDeviceid(request.getDeviceId());
        guessULikeRequest.setUid(request.getUid());
        guessULikeRequest.setPageIdx(request.getNumber());
        guessULikeRequest.setSize(request.getSize());
        guessULikeRequest.setCacheid(request.getCacheid());

        GuessULikeResult guessULikeResult = recProductService.guessULike(guessULikeRequest);

        ReadableRecProductList readableRecProductList = convert2ReadableRecProduct(guessULikeResult.getProductList(), language);
        readableRecProductList.setNumber(request.getNumber());
        readableRecProductList.setCacheid(guessULikeResult.getCacheid());

        return readableRecProductList;
    }

    @Override
    public ReadableRecProductList getRecRelateItem(RecRelateItemRequest request, Language language) throws Exception {
        RelateItemRequest relateItemRequest = new RelateItemRequest();
        relateItemRequest.setDeviceid(request.getDeviceId());
        relateItemRequest.setUid(request.getUid());
        relateItemRequest.setProductId(request.getProductId());
        relateItemRequest.setPageIdx(request.getNumber());
        relateItemRequest.setSize(request.getSize());
        relateItemRequest.setCacheid(request.getCacheid());

        RelateItemResult relateItemResult = recProductService.relateItem(relateItemRequest);

        ReadableRecProductList readableRecProductList = convert2ReadableRecProduct(relateItemResult.getProductList(), language);
        readableRecProductList.setNumber(request.getNumber());
        readableRecProductList.setCacheid(relateItemResult.getCacheid());

        return readableRecProductList;
    }

    @Override
    public ReadableRecProductList getRecSelectionProduct(RecSelectionProductRequest request, Language language) throws Exception {
        SelectionItemRequest selectionItemRequest = new SelectionItemRequest();
        selectionItemRequest.setDeviceid(request.getDeviceId());
        selectionItemRequest.setCategoryId(request.getCategoryId());
        selectionItemRequest.setUid(request.getUid());
        selectionItemRequest.setPageIdx(request.getPageIdx());
        selectionItemRequest.setTag(request.getTag());
        selectionItemRequest.setUid(request.getSize());
        selectionItemRequest.setCacheid(request.getCacheid());

        SelectionItemResult selectionItemResult = recProductService.selectionItem(selectionItemRequest);

        ReadableRecProductList readableRecProductList = convert2ReadableRecProduct(selectionItemResult.getProductList(), language);
        readableRecProductList.setNumber(request.getPageIdx());
        readableRecProductList.setCacheid(selectionItemResult.getCacheid());

        return readableRecProductList;
    }

    @Override
    public ReadableRecProductList getFootPrint(RecFootPrintRequest request, Language language) throws Exception {
        FootPrintRequest footPrintRequest = new FootPrintRequest();
        footPrintRequest.setUid(request.getUid());
        footPrintRequest.setSize(request.getSize());
        footPrintRequest.setPageIdx(request.getNumber());
        footPrintRequest.setCacheid(request.getCacheid());

        FootPrintResult footPrintResult = recProductService.footPrint(footPrintRequest);

        ReadableRecProductList readableRecProductList = convert2ReadableRecProduct(footPrintResult.getProductList(), language);
        readableRecProductList.setProducts(readableRecProductList.getProducts());

        return readableRecProductList;
    }

    private ReadableRecProductList convert2ReadableRecProduct(List<Product> products, Language language) throws ConversionException {
        if (products == null) {
            return null;
        }

        ReadableRecProductPopulator populator = new ReadableRecProductPopulator();
        populator.setPricingService(pricingService);
        populator.setimageUtils(imageUtils);
        populator.setReadableMerchantStorePopulator(readableMerchantStorePopulator);
        populator.setReadableProductVariantMapper(readableProductVariantMapper);
        populator.setProductFeatureService(productFeatureService);

        ReadableRecProductList productList = new ReadableRecProductList();
        for(Product product : products) {
            //create new proxy product
            ReadableRecProduct readProduct = populator.populate(product, new ReadableRecProduct(), product.getMerchantStore(), language);
            productList.getProducts().add(readProduct);
        }

        return productList;
    }
}
