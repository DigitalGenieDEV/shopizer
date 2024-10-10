package com.salesmanager.shop.store.controller.order.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductSnapshotService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.OrderProductSnapshot;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.ReadableCategoryMapper;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductVariantMapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsTrace;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import com.salesmanager.shop.model.order.PersistableOrderProductDesign;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.ReadableOrderProductDesign;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductList;
import com.salesmanager.shop.populator.order.ReadableOrderProductPopulator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import com.salesmanager.shop.utils.ImageFilePath;
import com.salesmanager.shop.utils.LocaleUtils;
import org.checkerframework.checker.units.qual.C;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.Locale;


import java.util.List;
@Component
public class OrderProductPopulatorUtil {
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProductPopulatorUtil.class);

    @Autowired
    private AdditionalServicesConvert additionalServicesConvert;
    @Autowired
    private ProductVariantService productVariantService;
    @Autowired
    private ReadableProductVariantMapper readableProductVariantMapper;
    @Autowired
    private FulfillmentFacade fulfillmentFacade;

    @Autowired
    private ProductService productService;
    @Autowired
    private PricingService pricingService;

    @Autowired
    @Qualifier("img")
    private ImageFilePath imageUtils;

    @Autowired
    private ReadableMerchantStorePopulator readableMerchantStorePopulator;

    @Autowired
    private ReadableCategoryMapper readableCategoryMapper;

    @Autowired
    private InvoicePackingFormService invoicePackingFormService;

    @Autowired
    private OrderProductSnapshotService orderProductSnapshotService;


    public void buildReadableOrderProduct(OrderProduct orderProduct,
                 ReadableOrderProduct target, MerchantStore store, Language language) throws ConversionException {
        ReadableOrderProductPopulator orderProductPopulator = new ReadableOrderProductPopulator();
        Locale locale = LocaleUtils.getLocale(language);
        orderProductPopulator.setLocale(locale);
        orderProductPopulator.setProductService(productService);
        orderProductPopulator.setPricingService(pricingService);
        orderProductPopulator.setimageUtils(imageUtils);
        orderProductPopulator.setAdditionalServicesConvert(additionalServicesConvert);
        orderProductPopulator.setReadableCategoryMapper(readableCategoryMapper);
        orderProductPopulator.setReadableMerchantStorePopulator(readableMerchantStorePopulator);
        orderProductPopulator.setInvoicePackingFormService(invoicePackingFormService);
        orderProductPopulator.setProductVariantService(productVariantService);
        orderProductPopulator.setFulfillmentFacade(fulfillmentFacade);
        orderProductPopulator.setReadableCategoryMapper(readableCategoryMapper);
        orderProductPopulator.setOrderProductSnapshotService(orderProductSnapshotService);
        orderProductPopulator.setReadableProductVariantMapper(readableProductVariantMapper);
        orderProductPopulator.populate(orderProduct, target, store, language);
    }
}
