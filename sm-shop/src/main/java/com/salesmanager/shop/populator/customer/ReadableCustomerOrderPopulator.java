package com.salesmanager.shop.populator.customer;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.ReadableBilling;
import com.salesmanager.shop.model.customer.ReadableDelivery;
import com.salesmanager.shop.model.customer.order.ReadableCustomerOrder;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.populator.order.ReadableOrderPopulator;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class ReadableCustomerOrderPopulator extends AbstractDataPopulator<CustomerOrder, ReadableCustomerOrder> {

    @Autowired
    private ReadableOrderPopulator readableOrderPopulator;

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
        target.setImportMain(source.getImportMain());
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
        if (!CollectionUtils.isEmpty(orders)) {
            for (Order order : orders) {
                ReadableOrder readableOrder = new ReadableOrder();
                readableOrderPopulator.populate(order, readableOrder, order.getMerchant(), language);
                readableOrders.add(readableOrder);
            }
        }

        target.setOrders(readableOrders);

        return target;
    }
}
