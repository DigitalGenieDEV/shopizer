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
import com.salesmanager.shop.populator.recommend.ReadableDisplayProductPopulator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.store.controller.search.facade.SearchFacadeImpl;
import com.salesmanager.shop.utils.ImageFilePath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("recProductFacade")
public class RecProductFacadeImpl implements RecProductFacade{


    private static final Logger LOGGER = LoggerFactory.getLogger(RecProductFacadeImpl.class);

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

        readableRecProductList.setRecordsTotal(guessULikeResult.getHitNumber());
        readableRecProductList.setTotalPages((int) Math.ceil(guessULikeResult.getHitNumber() / request.getSize()));

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
        readableRecProductList.setNumber(request.getNumber());
        readableRecProductList.setRecordsTotal(relateItemResult.getHitNumber());
        readableRecProductList.setTotalPages((int) Math.ceil(relateItemResult.getHitNumber() / request.getSize()));

        return readableRecProductList;
    }

    @Override
    public ReadableRecProductList getRecSelectionProduct(RecSelectionProductRequest request, Language language) throws Exception {
        SelectionItemRequest selectionItemRequest = new SelectionItemRequest();
        selectionItemRequest.setDeviceid(request.getDeviceId());
        selectionItemRequest.setCategoryId(request.getCategoryId());
        selectionItemRequest.setUid(request.getUid());
        selectionItemRequest.setPageIdx(request.getNumber());
        selectionItemRequest.setTag(request.getTag());
        selectionItemRequest.setUid(request.getUid());
        selectionItemRequest.setCacheid(request.getCacheid());

        SelectionItemResult selectionItemResult = recProductService.selectionItem(selectionItemRequest);

        ReadableRecProductList readableRecProductList = convert2ReadableRecProduct(selectionItemResult.getProductList(), language);
        readableRecProductList.setNumber(request.getNumber());
        readableRecProductList.setCacheid(selectionItemResult.getCacheid());
        readableRecProductList.setRecordsTotal(selectionItemResult.getHitNumber());
        readableRecProductList.setTotalPages((int) Math.ceil(selectionItemResult.getHitNumber() / request.getSize()));

        return readableRecProductList;
    }

    @Override
    public ReadableRecProductList getRecentView(RecFootPrintRequest request, Language language) throws Exception {
        FootPrintRequest footPrintRequest = new FootPrintRequest();
        footPrintRequest.setUid(request.getUid());
        footPrintRequest.setSize(request.getSize());
        footPrintRequest.setPageIdx(request.getNumber());
        footPrintRequest.setCacheid(request.getCacheid());

        FootPrintResult footPrintResult = recProductService.footPrint(footPrintRequest);

        ReadableRecProductList readableRecProductList = convert2ReadableRecProduct(footPrintResult.getProductList(), language);
        readableRecProductList.setProducts(readableRecProductList.getProducts());
        readableRecProductList.setRecordsTotal(footPrintResult.getHitNumber());
        readableRecProductList.setTotalPages((int) Math.ceil(footPrintResult.getHitNumber() / request.getSize()));

        return readableRecProductList;
    }

    private ReadableRecProductList convert2ReadableRecProduct(List<Product> products, Language language) throws ConversionException {
        if (products == null) {
            return null;
        }

        ReadableDisplayProductPopulator populator = new ReadableDisplayProductPopulator();
        populator.setPricingService(pricingService);
        populator.setimageUtils(imageUtils);
        populator.setReadableMerchantStorePopulator(readableMerchantStorePopulator);
        populator.setReadableProductVariantMapper(readableProductVariantMapper);
        populator.setProductFeatureService(productFeatureService);

        ReadableRecProductList productList = new ReadableRecProductList();
        for(Product product : products) {
            //create new proxy product
            try {
                ReadableDisplayProduct readProduct = populator.populate(product, new ReadableDisplayProduct(), product.getMerchantStore(), language);
                productList.getProducts().add(readProduct);
            } catch (Exception e) {
                LOGGER.error("convert2ReadableRecProduct error: ", e);
            }

        }

        return productList;
    }
}
