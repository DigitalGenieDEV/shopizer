package com.salesmanager.core.business.services.payments.combine;

import com.salesmanager.core.business.constants.Constants;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.order.OrderService;
import com.salesmanager.core.business.services.payments.TransactionService;
import com.salesmanager.core.business.services.reference.loader.ConfigurationModulesLoader;
import com.salesmanager.core.business.services.system.MerchantConfigurationService;
import com.salesmanager.core.business.services.system.ModuleConfigurationService;
import com.salesmanager.core.business.utils.CoreConfiguration;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.customer.order.CustomerOrder;
import com.salesmanager.core.model.customer.shoppingcart.CustomerShoppingCartItem;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.Order;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.payments.*;
import com.salesmanager.core.model.system.IntegrationConfiguration;
import com.salesmanager.core.model.system.IntegrationModule;
import com.salesmanager.core.model.system.MerchantConfiguration;
import com.salesmanager.core.modules.integration.IntegrationException;
import com.salesmanager.core.modules.integration.payment.model.CombinePaymentModule;
import com.salesmanager.core.modules.integration.payment.model.PaymentModule;
import com.salesmanager.core.modules.utils.Encryption;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.*;

@Service("combinePaymentService")
public class CombinePaymentServiceImpl implements CombinePaymentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CombinePaymentServiceImpl.class);

    @Inject
    private MerchantConfigurationService merchantConfigurationService;

    @Inject
    private ModuleConfigurationService moduleConfigurationService;

    @Inject
    private TransactionService transactionService;

    @Inject
    private CombineTransactionService combineTransactionService;

    @Inject
    private OrderService orderService;

    @Inject
    private CoreConfiguration coreConfiguration;

    @Inject
    @Resource(name="combinePaymentModules")
    private Map<String,CombinePaymentModule> combinePaymentModules;


    @Inject
    private Encryption encryption;


    @Override
    public List<IntegrationModule> getPaymentMethods(MerchantStore store) throws ServiceException {
        List<IntegrationModule> modules =  moduleConfigurationService.getIntegrationModules(Constants.COMBINE_PAYMENT_MODULES);
        List<IntegrationModule> returnModules = new ArrayList<IntegrationModule>();

        for(IntegrationModule module : modules) {
            if(module.getRegionsSet().contains(store.getCountry().getIsoCode())
                    || module.getRegionsSet().contains("*")) {

                returnModules.add(module);
            }
        }

        return returnModules;
    }

    @Override
    public Map<String, IntegrationConfiguration> getCombinePaymentModulesConfigured(MerchantStore store) throws ServiceException {
        try {

            Map<String,IntegrationConfiguration> modules = new HashMap<String,IntegrationConfiguration>();
            MerchantConfiguration merchantConfiguration = merchantConfigurationService.getMerchantConfiguration(Constants.COMBINE_PAYMENT_MODULES, store);
            if(merchantConfiguration!=null) {

                if(!StringUtils.isBlank(merchantConfiguration.getValue())) {

                    String decrypted = encryption.decrypt(merchantConfiguration.getValue());
                    modules = ConfigurationModulesLoader.loadIntegrationConfigurations(decrypted);


                }
            }
            return modules;

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CombineTransaction processPayment(MerchantStore store, Customer customer, List<CustomerShoppingCartItem> items, Payment payment, CustomerOrder customerOrder) throws ServiceException {
        Validate.notNull(customer);
        Validate.notNull(store);
        Validate.notNull(payment);
        Validate.notNull(customerOrder);
        Validate.notNull(customerOrder.getTotal());

        payment.setCurrency(store.getCurrency());

        BigDecimal amount = customerOrder.getTotal();

        //must have a shipping module configured
        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
        if(modules==null){
            throw new ServiceException("No payment module configured");
        }

        IntegrationConfiguration configuration = modules.get(payment.getModuleName());

        if(configuration==null) {
            throw new ServiceException("Payment module " + payment.getModuleName() + " is not configured");
        }

        if(!configuration.isActive()) {
            throw new ServiceException("Payment module " + payment.getModuleName() + " is not active");
        }

        String sTransactionType = configuration.getIntegrationKeys().get("transaction");
        if(sTransactionType==null) {
            sTransactionType = TransactionType.INIT.name();
        }

        try {
            TransactionType.valueOf(sTransactionType);
        } catch(IllegalArgumentException ie) {
            LOGGER.warn("Transaction type " + sTransactionType + " does noe exist, using default INIT");
            sTransactionType = "INIT";
        }

        if(sTransactionType.equals(TransactionType.AUTHORIZE.name())) {
            payment.setTransactionType(TransactionType.AUTHORIZE);
        } else {
            payment.setTransactionType(TransactionType.AUTHORIZECAPTURE);
        }

        CombinePaymentModule module = this.combinePaymentModules.get(payment.getModuleName());

        if (module == null) {
            throw new ServiceException("Payment module " + payment.getModuleName() + " does not exist");
        }

        IntegrationModule integrationModule = getPaymentMethodByCode(store,payment.getModuleName());
        TransactionType transactionType = TransactionType.valueOf(sTransactionType);
        if(transactionType==null) {
            transactionType = payment.getTransactionType();
            if(transactionType.equals(TransactionType.CAPTURE.name())) {
                throw new ServiceException("This method does not allow to process capture transaction. Use processCapturePayment");
            }
        }

        CombineTransaction transaction = null;
        if(transactionType == TransactionType.AUTHORIZE)  {
            transaction = module.authorize(store, customer, customerOrder, amount, payment, configuration, integrationModule);
        } else if(transactionType == TransactionType.AUTHORIZECAPTURE)  {
            transaction = module.authorizeAndCapture(store, customer, customerOrder, amount, payment, configuration, integrationModule);
        } else if(transactionType == TransactionType.INIT)  {
            transaction = module.initTransaction(store, customer, customerOrder, amount, payment, configuration, integrationModule);
        }

//        combineTransactionService.create(transaction);

        return transaction;
    }

    @Override
    public CombineTransaction processRefund(CustomerOrder customerOrder, Customer customer, MerchantStore store, BigDecimal amount) throws ServiceException {

        Validate.notNull(customer);
        Validate.notNull(store);
        Validate.notNull(amount);
        Validate.notNull(customerOrder);
        Validate.notNull(customerOrder.getTotal());

        BigDecimal orderTotal = customerOrder.getTotal();

        if(amount.doubleValue()>orderTotal.doubleValue()) {
            throw new ServiceException("Invalid amount, the refunded amount is greater than the total allowed");
        }

        String module = customerOrder.getPaymentModuleCode();
        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
        if(modules==null){
            throw new ServiceException("No payment module configured");
        }

        IntegrationConfiguration configuration = modules.get(module);

        if(configuration==null) {
            throw new ServiceException("Payment module " + module + " is not configured");
        }

        CombinePaymentModule paymentModule = this.combinePaymentModules.get(module);
        if(paymentModule==null) {
            throw new ServiceException("Payment module " + paymentModule + " does not exist");
        }

        boolean partial = false;
        if(amount.doubleValue()!=customerOrder.getTotal().doubleValue()) {
            partial = true;
        }

        IntegrationModule integrationModule = getPaymentMethodByCode(store,module);

        //get the associated transaction
        CombineTransaction refundable = combineTransactionService.getRefundableCombineTransaction(customerOrder);

        if(refundable==null) {
            throw new ServiceException("No refundable transaction for this order");
        }

        CombineTransaction transaction = paymentModule.refund(partial, store, refundable, customerOrder, amount, configuration, integrationModule);
        combineTransactionService.create(transaction);

        // TODO Customer Order Update

        return transaction;
    }

    @Override
    public IntegrationModule getPaymentMethodByCode(MerchantStore store, String code) throws ServiceException {
        List<IntegrationModule> modules =  getPaymentMethods(store);
        for(IntegrationModule module : modules) {
            if(module.getCode().equals(code)) {

                return module;
            }
        }

        return null;
    }

    @Override
    public void savePaymentModuleConfiguration(IntegrationConfiguration configuration, MerchantStore store) throws ServiceException {
        //validate entries
        try {

            String moduleCode = configuration.getModuleCode();
            CombinePaymentModule module = combinePaymentModules.get(moduleCode);
            if(module==null) {
                throw new ServiceException("Payment module " + moduleCode + " does not exist");
            }
            module.validateModuleConfiguration(configuration, store);

        } catch (IntegrationException ie) {
            throw ie;
        }

        try {
            Map<String,IntegrationConfiguration> modules = new HashMap<String,IntegrationConfiguration>();
            MerchantConfiguration merchantConfiguration = merchantConfigurationService.getMerchantConfiguration(Constants.COMBINE_PAYMENT_MODULES, store);
            if(merchantConfiguration!=null) {
                if(!StringUtils.isBlank(merchantConfiguration.getValue())) {

                    String decrypted = encryption.decrypt(merchantConfiguration.getValue());

                    modules = ConfigurationModulesLoader.loadIntegrationConfigurations(decrypted);
                }
            } else {
                merchantConfiguration = new MerchantConfiguration();
                merchantConfiguration.setMerchantStore(store);
                merchantConfiguration.setKey(Constants.COMBINE_PAYMENT_MODULES);
            }
            modules.put(configuration.getModuleCode(), configuration);

            String configs =  ConfigurationModulesLoader.toJSONString(modules);

            String encrypted = encryption.encrypt(configs);
            merchantConfiguration.setValue(encrypted);

            merchantConfigurationService.saveOrUpdate(merchantConfiguration);

        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public IntegrationConfiguration getPaymentConfiguration(String moduleCode, MerchantStore store) throws ServiceException {
        Validate.notNull(moduleCode,"Module code must not be null");
        Validate.notNull(store,"Store must not be null");
        String mod = moduleCode.toLowerCase();

        Map<String,IntegrationConfiguration> configuredModules = getCombinePaymentModulesConfigured(store);
        if(configuredModules!=null) {
            for(String key : configuredModules.keySet()) {
                if(key.equals(mod)) {
                    return configuredModules.get(key);
                }
            }
        }

        return null;
    }

    @Override
    public void removePaymentModuleConfiguration(String moduleCode, MerchantStore store) throws ServiceException {
        try {
            Map<String,IntegrationConfiguration> modules = new HashMap<String,IntegrationConfiguration>();
            MerchantConfiguration merchantConfiguration = merchantConfigurationService.getMerchantConfiguration(Constants.COMBINE_PAYMENT_MODULES, store);
            if(merchantConfiguration!=null) {

                if(!StringUtils.isBlank(merchantConfiguration.getValue())) {

                    String decrypted = encryption.decrypt(merchantConfiguration.getValue());
                    modules = ConfigurationModulesLoader.loadIntegrationConfigurations(decrypted);
                }

                modules.remove(moduleCode);
                String configs =  ConfigurationModulesLoader.toJSONString(modules);

                String encrypted = encryption.encrypt(configs);
                merchantConfiguration.setValue(encrypted);

                merchantConfigurationService.saveOrUpdate(merchantConfiguration);


            }

            MerchantConfiguration configuration = merchantConfigurationService.getMerchantConfiguration(moduleCode, store);

            if(configuration!=null) {//custom module

                merchantConfigurationService.delete(configuration);
            }


        } catch (Exception e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public CombineTransaction processCapturePayment(CustomerOrder customerOrder, Customer customer, MerchantStore store) throws ServiceException {

        Validate.notNull(customer);
        Validate.notNull(store);
        Validate.notNull(customerOrder);

        //must have a shipping module configured
        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
        if(modules==null){
            throw new ServiceException("No payment module configured");
        }

        IntegrationConfiguration configuration = modules.get(customerOrder.getPaymentModuleCode());

        if(configuration==null) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not configured");
        }

        if(!configuration.isActive()) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not active");
        }


        CombinePaymentModule module = this.combinePaymentModules.get(customerOrder.getPaymentModuleCode());

        if(module==null) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " does not exist");
        }


        IntegrationModule integrationModule = getPaymentMethodByCode(store,customerOrder.getPaymentModuleCode());

        CombineTransaction trx = combineTransactionService.getCapturableCombineTransaction(customerOrder);
        if(trx==null) {
            throw new ServiceException("No capturable transaction for order id " + customerOrder.getId());
        }

        CombineTransaction combineTransaction = module.capture(store, customer, customerOrder, trx, configuration, integrationModule);


        combineTransactionService.create(combineTransaction);


        return combineTransaction;
    }

    @Override
    public CombineTransaction processAuthorizeAndCapturePayment(CustomerOrder customerOrder, Customer customer, MerchantStore store) throws ServiceException {
        Validate.notNull(customer);
        Validate.notNull(store);
        Validate.notNull(customerOrder);

        //must have a shipping module configured
        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
        if(modules==null){
            throw new ServiceException("No payment module configured");
        }

        IntegrationConfiguration configuration = modules.get(customerOrder.getPaymentModuleCode());

        if(configuration==null) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not configured");
        }

        if(!configuration.isActive()) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not active");
        }


        CombinePaymentModule module = this.combinePaymentModules.get(customerOrder.getPaymentModuleCode());

        if(module==null) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " does not exist");
        }


        IntegrationModule integrationModule = getPaymentMethodByCode(store,customerOrder.getPaymentModuleCode());

//        CombineTransaction trx = combineTransactionService.getCapturableCombineTransaction(customerOrder);
//        if(trx==null) {
//            throw new ServiceException("No capturable transaction for order id " + customerOrder.getId());
//        }

        CombineTransaction combineTransaction = module.authorizeAndCapture(store, customer, customerOrder, customerOrder.getTotal(), null, configuration, integrationModule);


        combineTransactionService.create(combineTransaction);

        return combineTransaction;
    }

    @Override
    public CombineTransaction processPostPayment(CustomerOrder customerOrder, Customer customer, MerchantStore store, Payment payment) throws ServiceException {
        Validate.notNull(customer);
        Validate.notNull(store);
        Validate.notNull(customerOrder);

        //must have a shipping module configured
        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
        if(modules==null){
            throw new ServiceException("No payment module configured");
        }

        IntegrationConfiguration configuration = modules.get(customerOrder.getPaymentModuleCode());

        if(configuration==null) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not configured");
        }

        if(!configuration.isActive()) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not active");
        }


        CombinePaymentModule module = this.combinePaymentModules.get(payment.getModuleName());

        if(module==null) {
            throw new ServiceException("Payment module " + payment.getModuleName() + " does not exist");
        }


        IntegrationModule integrationModule = getPaymentMethodByCode(store,payment.getModuleName());
        TransactionType transactionType = payment.getTransactionType();
        if(transactionType==null) {
            transactionType = payment.getTransactionType();
            if(transactionType.equals(TransactionType.CAPTURE.name())) {
                throw new ServiceException("This method does not allow to process capture transaction. Use processCapturePayment");
            }
        }

        CombineTransaction transaction = null;
        if(transactionType == TransactionType.AUTHORIZE)  {
            transaction = module.authorize(store, customer, customerOrder, customerOrder.getTotal(), payment, configuration, integrationModule);
        } else if(transactionType == TransactionType.AUTHORIZECAPTURE)  {
            transaction = module.authorizeAndCapture(store, customer, customerOrder, customerOrder.getTotal(), payment, configuration, integrationModule);
        } else if(transactionType == TransactionType.CAPTURE)  {
            CombineTransaction lastCombineTransaction = combineTransactionService.lastCombineTransaction(customerOrder);
            transaction = module.capture(store, customer, customerOrder, lastCombineTransaction, configuration, integrationModule);
        }

        combineTransactionService.create(transaction);

        return transaction;
    }

    @Override
    public CombineTransaction initTransaction(CustomerOrder customerOrder, Customer customer, Payment payment, MerchantStore store) throws ServiceException {

        Validate.notNull(store);
        Validate.notNull(payment);
        Validate.notNull(customerOrder);
        Validate.notNull(customerOrder.getTotal());

        payment.setCurrency(store.getCurrency());

        BigDecimal amount = customerOrder.getTotal();

        //must have a shipping module configured
        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
        if(modules==null){
            throw new ServiceException("No payment module configured");
        }

        IntegrationConfiguration configuration = modules.get(payment.getModuleName());

        if(configuration==null) {
            throw new ServiceException("Payment module " + payment.getModuleName() + " is not configured");
        }

        if(!configuration.isActive()) {
            throw new ServiceException("Payment module " + payment.getModuleName() + " is not active");
        }

        CombinePaymentModule module = this.combinePaymentModules.get(customerOrder.getPaymentModuleCode());

        if(module==null) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " does not exist");
        }

        IntegrationModule integrationModule = getPaymentMethodByCode(store,payment.getModuleName());


        return module.initTransaction(store, customer, customerOrder, amount, payment, configuration, integrationModule);
    }

//    @Override
//    public CombineTransaction initTransaction(Customer customer, Payment payment, MerchantStore store) throws ServiceException {
//        Validate.notNull(store);
//        Validate.notNull(payment);
//        Validate.notNull(payment.getAmount());
//
//        payment.setCurrency(store.getCurrency());
//
//        BigDecimal amount = payment.getAmount();
//
//        //must have a shipping module configured
//        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
//        if(modules==null){
//            throw new ServiceException("No payment module configured");
//        }
//
//        IntegrationConfiguration configuration = modules.get(payment.getModuleName());
//
//        if(configuration==null) {
//            throw new ServiceException("Payment module " + payment.getModuleName() + " is not configured");
//        }
//
//        if(!configuration.isActive()) {
//            throw new ServiceException("Payment module " + payment.getModuleName() + " is not active");
//        }
//
//        CombinePaymentModule module = this.combinePaymentModules.get(payment.getModuleName());
//
//        if(module==null) {
//            throw new ServiceException("Payment module " + payment.getModuleName() + " does not exist");
//        }
//
//        IntegrationModule integrationModule = getPaymentMethodByCode(store,payment.getModuleName());
//
//        Transaction transaction = module.initTransaction(store, customer, amount, payment, configuration, integrationModule);
//
//        transactionService.save(transaction);
//
//        return transaction;
//    }

    @Override
    public List<PaymentMethod> getAcceptedPaymentMethods(MerchantStore store) throws ServiceException {
        Map<String,IntegrationConfiguration> modules =  this.getCombinePaymentModulesConfigured(store);

        List<PaymentMethod> returnModules = new ArrayList<PaymentMethod>();

        for(String module : modules.keySet()) {
            IntegrationConfiguration config = modules.get(module);
            if(config.isActive()) {

                IntegrationModule md = this.getPaymentMethodByCode(store, config.getModuleCode());
                if(md==null) {
                    continue;
                }
                PaymentMethod paymentMethod = new PaymentMethod();

                paymentMethod.setDefaultSelected(config.isDefaultSelected());
                paymentMethod.setPaymentMethodCode(config.getModuleCode());
                paymentMethod.setModule(md);
                paymentMethod.setInformations(config);

                PaymentType type = PaymentType.fromString(md.getType());

                paymentMethod.setPaymentType(type);
                returnModules.add(paymentMethod);
            }
        }

        return returnModules;
    }

    @Override
    public CombineTransaction processPaymentNextTransaction(CustomerOrder customerOrder, Customer customer, MerchantStore store, Payment payment) throws ServiceException {
        Validate.notNull(customer);
        Validate.notNull(store);
        Validate.notNull(customerOrder);

        //must have a shipping module configured
        Map<String, IntegrationConfiguration> modules = this.getCombinePaymentModulesConfigured(store);
        if(modules==null){
            throw new ServiceException("No payment module configured");
        }

        IntegrationConfiguration configuration = modules.get(customerOrder.getPaymentModuleCode());

        if(configuration==null) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not configured");
        }

        if(!configuration.isActive()) {
            throw new ServiceException("Payment module " + customerOrder.getPaymentModuleCode() + " is not active");
        }


        CombinePaymentModule module = this.combinePaymentModules.get(payment.getModuleName());

        if(module==null) {
            throw new ServiceException("Payment module " + payment.getModuleName() + " does not exist");
        }


        IntegrationModule integrationModule = getPaymentMethodByCode(store,payment.getModuleName());
        TransactionType transactionType = payment.getTransactionType();
        if(transactionType==null) {
            transactionType = payment.getTransactionType();
            if(transactionType.equals(TransactionType.CAPTURE.name())) {
                throw new ServiceException("This method does not allow to process capture transaction. Use processCapturePayment");
            }
        }

        CombineTransaction transaction = null;
        if(transactionType == TransactionType.AUTHORIZE)  {
            transaction = module.authorize(store, customer, customerOrder, customerOrder.getTotal(), payment, configuration, integrationModule);
        } else if(transactionType == TransactionType.AUTHORIZECAPTURE)  {
            transaction = module.authorizeAndCapture(store, customer, customerOrder, customerOrder.getTotal(), payment, configuration, integrationModule);
        } else if(transactionType == TransactionType.CAPTURE)  {
            CombineTransaction lastCombineTransaction = combineTransactionService.lastCombineTransaction(customerOrder);
            transaction = module.capture(store, customer, customerOrder, lastCombineTransaction, configuration, integrationModule);
        }

        combineTransactionService.create(transaction);

        return transaction;
    }

    @Override
    public CombinePaymentModule getPaymentModule(String paymentModuleCode) throws ServiceException {
        return combinePaymentModules.get(paymentModuleCode);
    }
//
//    @Override
//    public CombineTransaction processPayment(Customer customer, Payment payment, CustomerOrder customerOrder, MerchantStore store) throws ServiceException {
//        CombineTransaction combineTransaction = new CombineTransaction();
//        combineTransaction.setAmount(customerOrder.getTotal());
//        combineTransaction.setTransactionDate(new Date());
//        combineTransaction.setTransactionType(TransactionType.AUTHORIZECAPTURE);
//        combineTransaction.setPaymentType(PaymentType.FREE);
//
//        return combineTransaction;
//    }
}
