package com.salesmanager.shop.populator.customer;

import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.business.services.reference.currency.CurrencyService;
import com.salesmanager.core.business.utils.AbstractDataPopulator;
import com.salesmanager.core.model.common.Billing;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.OrderChannel;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.PaymentType;
import com.salesmanager.core.model.reference.currency.Currency;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.customer.order.PersistableCustomerOrder;
import org.apache.commons.lang3.Validate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Date;

@Component
public class PersistableCustomerOrderApiPopulator extends AbstractDataPopulator<PersistableCustomerOrder, CustomerOrder> {

    @Autowired
    private CurrencyService currencyService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private CountryService countryService;

    @Autowired
    private CustomerPopulator customerPopulator;

    @Override
    protected CustomerOrder createTarget() {
        return null;
    }

    @Override
    public CustomerOrder populate(PersistableCustomerOrder source, CustomerOrder target, MerchantStore store, Language language) throws ConversionException {

        Validate.notNull(source.getPayment(),"Payment cannot be null");

        try {
            if (target == null) {
                target = new CustomerOrder();
            }

//            target.setLocale(LocaleUtils.getLocale(store));

            Currency currency = null;
            try {
                currency = currencyService.getByCode(source.getCurrency());
            } catch (Exception e) {
                throw new ConversionException("Currency not found for code " + source.getCurrency());
            }

            if(currency==null) {
                throw new ConversionException("Currency not found for code " + source.getCurrency());
            }

            //Customer
            Customer customer = null;
            if(source.getCustomerId() != null && source.getCustomerId().longValue() >0) {
                Long customerId = source.getCustomerId();
                customer = customerService.getById(customerId);

                if(customer == null) {
                    throw new ConversionException("Curstomer with id " + source.getCustomerId() + " does not exist");
                }
                target.setCustomerId(customerId);

            } else {
//                if(source instanceof PersistableAnonymousOrder) {
//                    PersistableCustomer persistableCustomer = ((PersistableAnonymousOrder)source).getCustomer();
//                    customer = new Customer();
//                    customer = customerPopulator.populate(persistableCustomer, customer, store, language);
//                } else {
//                    throw new ConversionException("Curstomer details or id not set in request");
//                }
            }


            target.setCustomerEmailAddress(customer.getEmailAddress());

//            Delivery delivery = customer.getDelivery();
//            target.setDelivery(delivery);



            Delivery delivery = new Delivery();
            delivery.setAddress(source.getAddress().getAddress());
            delivery.setCity(source.getAddress().getCity());
            delivery.setCompany(source.getAddress().getCompany());
            delivery.setFirstName(source.getAddress().getFirstName());
            delivery.setLastName(source.getAddress().getLastName());
            delivery.setPostalCode(source.getAddress().getPostalCode());
            delivery.setTelephone(source.getAddress().getPhone());
            delivery.setCountry(countryService.getByCode(source.getAddress().getCountryCode()));
            target.setDelivery(delivery);

            Billing billing = customer.getBilling();
            target.setBilling(billing);


//            if(source.getAttributes() != null && source.getAttributes().size() > 0) {
//                Set<OrderAttribute> attrs = new HashSet<OrderAttribute>();
//                for(com.salesmanager.shop.model.order.OrderAttribute attribute : source.getAttributes()) {
//                    OrderAttribute attr = new OrderAttribute();
//                    attr.setKey(attribute.getKey());
//                    attr.setValue(attribute.getValue());
//                    attr.setOrder(target);
//                    attrs.add(attr);
//                }
//                target.setOrderAttributes(attrs);
//            }

            target.setDatePurchased(new Date());
            target.setCurrency(currency);
            target.setCurrencyValue(new BigDecimal(0));
            target.setMerchant(store);
            target.setChannel(OrderChannel.API);
            //need this
            target.setStatus(OrderStatus.ORDERED);
            target.setPaymentModuleCode(source.getPayment().getPaymentModule());
            target.setPaymentType(PaymentType.valueOf(source.getPayment().getPaymentType()));

            target.setCustomerAgreement(source.isCustomerAgreement());
            target.setConfirmedAddress(true);

            return target;
        } catch (Exception e) {
            throw new ConversionException(e);
        }
    }
}
