package com.salesmanager.shop.populator.customer;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.fulfillment.service.FulfillmentHistoryService;
import com.salesmanager.core.business.fulfillment.service.InvoicePackingFormService;
import com.salesmanager.core.business.fulfillment.service.ShippingOrderService;
import com.salesmanager.core.business.repositories.fulfillment.ShippingDocumentOrderRepository;
import com.salesmanager.core.business.repositories.order.orderproduct.OrderProductRepository;
import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.business.services.catalog.product.attribute.ProductAttributeService;
import com.salesmanager.core.business.services.catalog.product.file.DigitalProductService;
import com.salesmanager.core.business.services.catalog.product.variant.ProductVariantService;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.business.services.order.orderproduct.OrderProductService;
import com.salesmanager.core.business.services.payments.PaymentService;
import com.salesmanager.core.business.services.payments.TransactionService;
import com.salesmanager.core.business.services.payments.combine.CombineTransactionService;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.zone.ZoneService;
import com.salesmanager.core.business.services.shipping.ShippingQuoteService;
import com.salesmanager.core.business.services.shipping.ShippingService;
import com.salesmanager.core.business.services.shoppingcart.ShoppingCartService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.business.utils.ProductPriceUtils;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.orderproduct.OrderProduct;
import com.salesmanager.core.model.payments.ImportMainEnums;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.mapper.catalog.ReadableCategoryMapper;
import com.salesmanager.shop.mapper.catalog.product.ReadableProductVariantMapper;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrder;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.populator.order.PersistableOrderApiPopulator;
import com.salesmanager.shop.populator.order.ReadableOrderPopulator;
import com.salesmanager.shop.populator.order.ReadableOrderProductPopulator;
import com.salesmanager.shop.populator.store.ReadableMerchantStorePopulator;
import com.salesmanager.shop.store.controller.customer.facade.CustomerFacade;
import com.salesmanager.shop.store.controller.customer.facade.CustomerOrderFacadeImpl;
import com.salesmanager.shop.store.controller.fulfillment.faced.convert.AdditionalServicesConvert;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacade;
import com.salesmanager.shop.utils.EmailTemplatesUtils;
import com.salesmanager.shop.utils.ImageFilePath;
import com.salesmanager.shop.utils.LabelUtils;
import com.salesmanager.shop.utils.LocaleUtils;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Component
public class ReadableCustomerOrderPopulator extends AbstractDataPopulator<CustomerOrder, ReadableCustomerOrder> {

    private static final Logger LOGGER = LoggerFactory.getLogger(ReadableCustomerOrderPopulator.class);

    @Autowired
    private ReadableOrderPopulator readableOrderPopulator;

    @Inject
    private ProductService productService;

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

    @Override
    protected ReadableCustomerOrder createTarget() {
        return null;
    }

    @Override
    public ReadableCustomerOrder populate(CustomerOrder source, ReadableCustomerOrder target, MerchantStore store, Language language) throws ConversionException {

        target.setId(source.getId());
        target.setDatePurchased(source.getDatePurchased());
        target.setCurrency(source.getCurrency().getCode());
        //target.setCurrencyModel(source.getCurrency());

        target.setPaymentType(source.getPaymentType());
        target.setOrderType(source.getOrderType() == null? null : source.getOrderType().name());
        target.setImportMain(source.getImportMain() == null? null : source.getImportMain().name());
        target.setCustomsClearanceNumber(source.getCustomsClearanceNumber());

        target.setPaymentModule(source.getPaymentModuleCode());
        if(source.getCustomerAgreement()!=null) {
            target.setCustomerAgreed(source.getCustomerAgreement());
        }
        if(source.getConfirmedAddress()!=null) {
            target.setConfirmedAddress(source.getConfirmedAddress());
        }

        if(source.getBilling()!=null) {
            ReadableBilling address = new ReadableBilling();
            address.setEmail(source.getCustomerEmailAddress());
            address.setCity(source.getBilling().getCity());
            address.setAddress(source.getBilling().getAddress());
            address.setCompany(source.getBilling().getCompany());
            address.setFirstName(source.getBilling().getFirstName());
            address.setLastName(source.getBilling().getLastName());
            address.setPostalCode(source.getBilling().getPostalCode());
            address.setPhone(source.getBilling().getTelephone());
            if(source.getBilling().getCountry()!=null) {
                address.setCountry(source.getBilling().getCountry().getIsoCode());
            }
            if(source.getBilling().getZone()!=null) {
                address.setZone(source.getBilling().getZone().getCode());
            }

            target.setBilling(address);
        }


        if(source.getDelivery()!=null) {
            ReadableDelivery address = new ReadableDelivery();
            address.setCity(source.getDelivery().getCity());
            address.setAddress(source.getDelivery().getAddress());
            address.setCompany(source.getDelivery().getCompany());
            address.setFirstName(source.getDelivery().getFirstName());
            address.setLastName(source.getDelivery().getLastName());
            address.setPostalCode(source.getDelivery().getPostalCode());
            address.setPhone(source.getDelivery().getTelephone());
            if(source.getDelivery().getCountry()!=null) {
                address.setCountry(source.getDelivery().getCountry().getIsoCode());
            }
            if(source.getDelivery().getZone()!=null) {
                address.setZone(source.getDelivery().getZone().getCode());
            }

            target.setDelivery(address);
        }

        List<Order> orders = source.getOrders();
        List<ReadableOrder> readableOrders = new ArrayList<>();
        Locale locale = LocaleUtils.getLocale(language);
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                ReadableOrder readableOrder = new ReadableOrder();
                readableOrderPopulator.populate(order, readableOrder, order.getMerchant(), language);
                try {
                    setOrderProductList(order, locale, store, language, readableOrder);
                }catch (Exception e){
                    LOGGER.error("setOrderProductList error",e);
                }
                readableOrders.add(readableOrder);
            }
        }

        target.setOrders(readableOrders);

        return target;
    }


    private void setOrderProductList(final Order order, final Locale locale, final MerchantStore store,
                                     final Language language, final com.salesmanager.shop.model.order.v0.ReadableOrder readableOrder)
            throws ConversionException {
        List<ReadableOrderProduct> orderProducts = new ArrayList<ReadableOrderProduct>();
        for (OrderProduct p : order.getOrderProducts()) {
            ReadableOrderProductPopulator orderProductPopulator = new ReadableOrderProductPopulator();
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

            orderProductPopulator.setReadableProductVariantMapper(readableProductVariantMapper);
            ReadableOrderProduct orderProduct = new ReadableOrderProduct();
            orderProductPopulator.populate(p, orderProduct, store, language);

            // image

            // attributes

            orderProducts.add(orderProduct);
        }

        readableOrder.setProducts(orderProducts);
    }

}
