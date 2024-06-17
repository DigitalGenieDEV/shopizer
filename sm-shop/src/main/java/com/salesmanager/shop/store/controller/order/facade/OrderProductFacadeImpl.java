package com.salesmanager.shop.store.controller.order.facade;

import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductList;
import com.salesmanager.shop.populator.order.ReadableOrderProductPopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@Service("orderProductFacade")
public class OrderProductFacadeImpl implements OrderProductFacade{

    @Inject
    private OrderProductService orderProductService;

    @Inject
    private ProductService productService;

    @Inject
    private PricingService pricingService;

    @Inject
    @Qualifier("img")
    private ImageFilePath imageUtils;


    @Override
    public ReadableOrderProductList getReadableOrderProductList(MerchantStore store, Customer customer, Integer page, Integer count, Language language) {
        try {

            return null;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error while getting order products", e);
        }
    }

    @Override
    public ReadableOrderProductList getReadableOrderProductList(OrderProductCriteria criteria, MerchantStore store, Language language) {
        try {
            criteria.setLegacyPagination(false);

            OrderProductList orderProductList = orderProductService.getOrderProducts(criteria, store);

            List<OrderProduct> orderProducts = orderProductList.getOrderProducts();
            ReadableOrderProductList returnList = new ReadableOrderProductList();

            if (CollectionUtils.isEmpty(orderProducts)) {
                returnList.setRecordsTotal(0);
                return returnList;
            }

            ReadableOrderProductPopulator readableOrderProductPopulator = new ReadableOrderProductPopulator();
            readableOrderProductPopulator.setProductService(productService);
            readableOrderProductPopulator.setimageUtils(imageUtils);
            readableOrderProductPopulator.setPricingService(pricingService);

            List<ReadableOrderProduct> readableOrderProducts = new ArrayList<>();
            for (OrderProduct orderProduct : orderProducts) {
                ReadableOrderProduct readableOrderProduct = new ReadableOrderProduct();
                readableOrderProductPopulator.populate(orderProduct, readableOrderProduct, store, language);
                readableOrderProducts.add(readableOrderProduct);
            }

            returnList.setOrderProducts(readableOrderProducts);
            returnList.setRecordsTotal(orderProductList.getTotalCount());
            returnList.setTotalPages(orderProductList.getTotalPages());
            returnList.setNumber(orderProductList.getOrderProducts().size());
            returnList.setRecordsFiltered(orderProductList.getOrderProducts().size());

            return returnList;
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error while getting order products", e);
        }
    }

    @Override
    public ReadableOrderProduct getReadableOrderProduct(Long id, MerchantStore merchantStore, Language language) {
        Validate.notNull(merchantStore, "MerchantStore cannot be null");
        OrderProduct modelOrderProduct = orderProductService.getOrderProduct(id, merchantStore);
        if (modelOrderProduct == null) {
            throw new ResourceNotFoundException("OrderProduct not found with id " + id);
        }

        ReadableOrderProductPopulator readableOrderProductPopulator = new ReadableOrderProductPopulator();
        readableOrderProductPopulator.setProductService(productService);
        readableOrderProductPopulator.setimageUtils(imageUtils);
        readableOrderProductPopulator.setPricingService(pricingService);

        ReadableOrderProduct readableOrderProduct = new ReadableOrderProduct();

        try {
            readableOrderProductPopulator.populate(modelOrderProduct, readableOrderProduct, merchantStore, language);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error while getting order product [" + id + "]");
        }

        return readableOrderProduct;
    }
}