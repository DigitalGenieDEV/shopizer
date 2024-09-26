package com.salesmanager.shop.store.controller.order.facade;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.crossorder.SupplierCrossOrderLogisticsService;
import com.salesmanager.core.business.services.crossorder.SupplierCrossOrderLogisticsTraceService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductDesignService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogistics;
import com.salesmanager.core.model.crossorder.logistics.SupplierCrossOrderLogisticsTrace;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderProductCriteria;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.order.orderproduct.OrderProductDesign;
import com.salesmanager.core.model.order.orderproduct.OrderProductList;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.ReadableCategoryMapper;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductVariantMapper;
import com.salesmanager.shop.mapper.crossorder.ReadableSupplierCrossOrderLogisticsMapper;
import com.salesmanager.shop.mapper.crossorder.ReadableSupplierCrossOrderLogisticsTraceMapper;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogistics;
import com.salesmanager.shop.model.crossorder.ReadableSupplierCrossOrderLogisticsTrace;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import com.salesmanager.shop.model.order.PersistableOrderProductDesign;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.ReadableOrderProductDesign;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductList;
import com.salesmanager.shop.populator.order.PersistableOrderProductDesignPopulator;
import com.salesmanager.shop.populator.order.ReadableOrderProductDesignPopulator;
import com.salesmanager.shop.populator.order.ReadableOrderProductPopulator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.store.api.exception.ResourceNotFoundException;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import com.salesmanager.shop.utils.ImageFilePath;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service("orderProductFacade")
public class OrderProductFacadeImpl implements OrderProductFacade {

    @Inject
    private OrderProductService orderProductService;

    @Inject
    private OrderProductDesignService orderProductDesignService;

    @Inject
    private ProductService productService;

    @Inject
    private SupplierCrossOrderLogisticsTraceService supplierCrossOrderLogisticsTraceService;

    @Inject
    private SupplierCrossOrderLogisticsService supplierCrossOrderLogisticsService;

    @Inject
    private ReadableSupplierCrossOrderLogisticsTraceMapper readableSupplierCrossOrderLogisticsTraceMapper;

    @Inject
    private ReadableSupplierCrossOrderLogisticsMapper readableSupplierCrossOrderLogisticsMapper;

    @Inject
    private PricingService pricingService;

    @Inject
    @Qualifier("img")
    private ImageFilePath imageUtils;

    @Autowired
    private ProductVariantService productVariantService;

    @Autowired
    private ReadableProductVariantMapper readableProductVariantMapper;

    @Inject
    private InvoicePackingFormService invoicePackingFormService;

    @Autowired
    private FulfillmentFacade fulfillmentFacade;
    @Autowired
    private AdditionalServicesConvert additionalServicesConvert;
    @Autowired
    private ReadableMerchantStorePopulator readableMerchantStorePopulator;
    @Autowired
    private ReadableCategoryMapper readableCategoryMapper;

    @Inject
    private PersistableOrderProductDesignPopulator persistableOrderProductDesignPopulator;

    @Inject
    private ReadableOrderProductDesignPopulator readableOrderProductDesignPopulator;

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
            readableOrderProductPopulator.setInvoicePackingFormService(invoicePackingFormService);
            readableOrderProductPopulator.setProductVariantService(productVariantService);
            readableOrderProductPopulator.setFulfillmentFacade(fulfillmentFacade);
            readableOrderProductPopulator.setAdditionalServicesConvert(additionalServicesConvert);
            readableOrderProductPopulator.setReadableMerchantStorePopulator(readableMerchantStorePopulator);
            readableOrderProductPopulator.setReadableCategoryMapper(readableCategoryMapper);

            readableOrderProductPopulator.setReadableProductVariantMapper(readableProductVariantMapper);
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
        readableOrderProductPopulator.setInvoicePackingFormService(invoicePackingFormService);
        readableOrderProductPopulator.setProductVariantService(productVariantService);
        readableOrderProductPopulator.setFulfillmentFacade(fulfillmentFacade);
        readableOrderProductPopulator.setAdditionalServicesConvert(additionalServicesConvert);
        readableOrderProductPopulator.setReadableProductVariantMapper(readableProductVariantMapper);
        readableOrderProductPopulator.setReadableMerchantStorePopulator(readableMerchantStorePopulator);
        readableOrderProductPopulator.setReadableCategoryMapper(readableCategoryMapper);

        ReadableOrderProduct readableOrderProduct = new ReadableOrderProduct();

        try {
            readableOrderProductPopulator.populate(modelOrderProduct, readableOrderProduct, merchantStore, language);
        } catch (Exception e) {
            throw new ServiceRuntimeException("Error while getting order product [" + id + "]");
        }

        return readableOrderProduct;
    }

    @Override
    public Boolean patchDesign(ReadableOrderProduct readableOrderProduct, PersistableOrderProductDesign persistableOrderProductDesign) {
        OrderProductDesign orderProductDesign;
        try {
            OrderProductDesign existedOrderProductDesign = orderProductDesignService.getByOrderProductId(readableOrderProduct.getId());
            orderProductDesign = persistableOrderProductDesignPopulator.populate(persistableOrderProductDesign, existedOrderProductDesign, null, null);
        } catch (ConversionException e) {
            throw new ServiceRuntimeException("Error while saveing order product design, order product id:" + readableOrderProduct.getId(), e);
        }
        return orderProductDesignService.save(orderProductDesign);
    }

    @Override
    public ReadableOrderProductDesign getReadableOrderProductDesignById(Long id, MerchantStore merchantStore, Language language) {
        OrderProductDesign orderProductDesign = orderProductDesignService.getByOrderProductId(id);

        ReadableOrderProductDesign readableOrderProductDesign;
        try {
            readableOrderProductDesign = readableOrderProductDesignPopulator.populate(orderProductDesign, null, null);
        } catch (ConversionException e) {
            throw new ServiceRuntimeException("Error while getting order product design ", e);
        }

        return readableOrderProductDesign;
    }

    @Override
    public List<ReadableSupplierCrossOrderLogistics> getLogisticsInfo(Long id) {
        return supplierCrossOrderLogisticsService.getLogisticsByOrderProductId(id).stream()
                .map(logistics -> readableSupplierCrossOrderLogisticsMapper.convert(logistics, null, null))
                .collect(Collectors.toList());
    }

    @Override
    public List<ReadableSupplierCrossOrderLogisticsTrace> getLogisticsTrace(Long id) {
        return supplierCrossOrderLogisticsTraceService
                .getLogisticsTraceByOrderProductId(id)
                .stream()
                .map(trace -> readableSupplierCrossOrderLogisticsTraceMapper.convert(trace, null, null))
                .collect(Collectors.toList());
    }
}
