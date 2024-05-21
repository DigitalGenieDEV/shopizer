package com.salesmanager.shop.store.controller.search.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.search.RecProductService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.catalog.product.recommend.GuessULikeRequest;
import com.salesmanager.core.model.catalog.product.recommend.RelateItemRequest;
import com.salesmanager.core.model.catalog.product.recommend.SelectionItemRequest;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.catalog.SearchRecGuessULikeRequest;
import com.salesmanager.shop.model.catalog.SearchRecRelateItemRequest;
import com.salesmanager.shop.model.catalog.SearchRecSelectionRequest;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.ReadableProductList;
import com.salesmanager.shop.populator.catalog.ReadableProductPopulator;
import com.salesmanager.shop.utils.ImageFilePath;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

@Service("searchRecFacade")
public class SearchRecFacadeImpl implements SearchRecFacade{

    @Inject
    private RecProductService recProductService;

    @Inject
    private PricingService pricingService;

    @Inject
    @Qualifier("img")
    private ImageFilePath imageUtils;

    @Override
    public List<ReadableProduct> searchRecGuessULike(SearchRecGuessULikeRequest request, Language language) throws ConversionException {
        GuessULikeRequest guessULikeRequest = new GuessULikeRequest();
        guessULikeRequest.setDeviceid(request.getDeviceid());
        guessULikeRequest.setUid(request.getUid());
        guessULikeRequest.setPageIdx(request.getPageIdx());
        guessULikeRequest.setSize(request.getSize());

        return convert2ReadableProduct(recProductService.guessULike(guessULikeRequest).getProductList(), language);
    }

    @Override
    public List<ReadableProduct> searchRecRelateItem(SearchRecRelateItemRequest request, Language language) throws ConversionException {
        RelateItemRequest relateItemRequest = new RelateItemRequest();
        relateItemRequest.setDeviceid(request.getDeviceid());
        relateItemRequest.setUid(request.getUid());
        relateItemRequest.setProductId(request.getProductId());
        relateItemRequest.setPageIdx(request.getPageIdx());
        relateItemRequest.setSize(request.getSize());

        return convert2ReadableProduct(recProductService.relateItem(relateItemRequest).getProductList(), language);
    }

    @Override
    public List<ReadableProduct> searchRecSelectionItem(SearchRecSelectionRequest request, Language language) throws ConversionException {
        SelectionItemRequest selectionItemRequest = new SelectionItemRequest();
        selectionItemRequest.setDeviceid(request.getDeviceid());
        selectionItemRequest.setUid(request.getUid());
        selectionItemRequest.setPageIdx(request.getPageIdx());
        selectionItemRequest.setTag(request.getTag());
        selectionItemRequest.setUid(request.getSize());

        return convert2ReadableProduct(recProductService.selectionItem(selectionItemRequest).getProductList(), language);
    }

    private List<ReadableProduct> convert2ReadableProduct(List<Product> products, Language language) throws ConversionException {
        if (products == null) {
            return null;
        }

        ReadableProductPopulator populator = new ReadableProductPopulator();
        populator.setPricingService(pricingService);
        populator.setimageUtils(imageUtils);

        ReadableProductList productList = new ReadableProductList();
        for(Product product : products) {
            //create new proxy product
            ReadableProduct readProduct = populator.populate(product, new ReadableProduct(), product.getMerchantStore(), language);
            productList.getProducts().add(readProduct);
        }

        return productList.getProducts();

    }
}
