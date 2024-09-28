package com.salesmanager.shop.store.api.v1.order;

import java.security.Principal;
import java.util.List;
import java.util.Locale;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.fulfillment.ShippingOrderProductQuery;
import com.salesmanager.shop.model.catalog.product.ReadableProduct;
import com.salesmanager.shop.model.catalog.product.product.PersistableProduct;
import com.salesmanager.shop.model.catalog.product.product.definition.PersistableProductDefinition;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.fulfillment.*;
import com.salesmanager.shop.model.fulfillment.facade.FulfillmentFacade;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import com.salesmanager.shop.utils.DateUtil;
import com.salesmanager.shop.utils.LocaleUtils;
import io.swagger.annotations.*;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.salesmanager.core.business.services.catalog.pricing.PricingService;
import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.business.services.reference.country.CountryService;
import com.salesmanager.core.model.common.Delivery;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.country.Country;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shipping.ShippingOption;
import com.salesmanager.core.model.shipping.ShippingQuote;
import com.salesmanager.core.model.shipping.ShippingSummary;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.shop.model.customer.address.AddressLocation;
import com.salesmanager.shop.model.order.shipping.ReadableShippingSummary;
import com.salesmanager.shop.populator.order.ReadableShippingSummaryPopulator;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.controller.shoppingCart.facade.ShoppingCartFacade;
import com.salesmanager.shop.utils.LabelUtils;

import springfox.documentation.annotations.ApiIgnore;


@Controller
@RequestMapping("/api/v1")
@Api(tags = {"Shipping Quotes and Calculation resource (Shipping Api)"})
@SwaggerDefinition(tags = {
        @Tag(name = "Shipping Quotes and Calculation resource", description = "Get shipping quotes for public api and loged in customers")
})
public class OrderShippingApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderShippingApi.class);

    private static final String DEFAULT_ORDER_LIST_COUNT = "10";

    @Inject private CustomerService customerService;

    @Inject private OrderFacade orderFacade;

    @Inject private ShoppingCartFacade shoppingCartFacade;

    @Inject private LabelUtils messages;

    @Inject private PricingService pricingService;

    @Inject private CountryService countryService;

    @Autowired
    private FulfillmentFacade fulfillmentFacade;


    /**
     * Get shipping quote for a given shopping cart
     *
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = {"/auth/cart/{code}/shipping"},
            method = RequestMethod.GET)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
    })
    public ReadableShippingSummary shipping(
            @PathVariable final String code,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response) {

        try {
            Locale locale = request.getLocale();
            Principal principal = request.getUserPrincipal();
            String userName = principal.getName();

            // get customer id
            Customer customer = customerService.getByNick(userName);

            if (customer == null) {
                response.sendError(503, "Error while getting user details to calculate shipping quote");
            }

            ShoppingCart cart = shoppingCartFacade.getShoppingCartModel(code, merchantStore);

            if (cart == null) {
                response.sendError(404, "Cart code " + code + " does not exist");
            }

            if (cart.getCustomerId() == null) {
                response.sendError(404, "Cart code " + code + " does not exist for exist for user " + userName);
            }

            if (cart.getCustomerId().longValue() != customer.getId().longValue()) {
                response.sendError(404, "Cart code " + code + " does not exist for exist for user " + userName);
            }

            ShippingQuote quote = orderFacade.getShippingQuote(customer, cart, merchantStore, language);

            ShippingSummary summary = orderFacade.getShippingSummary(quote, merchantStore, language);

            ReadableShippingSummary shippingSummary = new ReadableShippingSummary();
            ReadableShippingSummaryPopulator populator = new ReadableShippingSummaryPopulator();
            populator.setPricingService(pricingService);
            populator.populate(summary, shippingSummary, merchantStore, language);

            List<ShippingOption> options = quote.getShippingOptions();

            if (!CollectionUtils.isEmpty(options)) {

                for (ShippingOption shipOption : options) {

                    StringBuilder moduleName = new StringBuilder();
                    moduleName.append("module.shipping.").append(shipOption.getShippingModuleCode());

                    String carrier =
                            messages.getMessage(
                                    moduleName.toString(), new String[] {merchantStore.getStorename()}, locale);

                    String note = messages.getMessage(moduleName.append(".note").toString(), locale, "");

                    shipOption.setDescription(carrier);
                    shipOption.setNote(note);

                    // option name
                    if (!StringUtils.isBlank(shipOption.getOptionCode())) {
                        // try to get the translate
                        StringBuilder optionCodeBuilder = new StringBuilder();
                        try {

                            optionCodeBuilder
                                    .append("module.shipping.")
                                    .append(shipOption.getShippingModuleCode());
                            String optionName = messages.getMessage(optionCodeBuilder.toString(), locale);
                            shipOption.setOptionName(optionName);
                        } catch (Exception e) { // label not found
                            LOGGER.warn("No shipping code found for " + optionCodeBuilder.toString());
                        }
                    }
                }

                shippingSummary.setShippingOptions(options);
            }

            return shippingSummary;

        } catch (Exception e) {
            LOGGER.error("Error while getting shipping quote", e);
            try {
                response.sendError(503, "Error while getting shipping quote" + e.getMessage());
            } catch (Exception ignore) {
            }
            return null;
        }
    }

    /**
     * Get shipping quote based on postal code
     * @param code
     * @param address
     * @param merchantStore
     * @param language
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(
            value = {"/cart/{code}/shipping"},
            method = RequestMethod.POST)
    @ResponseBody
    @ApiImplicitParams({
            @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
            @ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")
    })
    public ReadableShippingSummary shipping(
            @PathVariable final String code,
            @RequestBody AddressLocation address,
            @ApiIgnore MerchantStore merchantStore,
            @ApiIgnore Language language,
            HttpServletRequest request,
            HttpServletResponse response)
            throws Exception {

        try {
            Locale locale = request.getLocale();

            ShoppingCart cart = shoppingCartFacade.getShoppingCartModel(code, merchantStore);

            if (cart == null) {
                response.sendError(404, "Cart id " + code + " does not exist");
            }


            Delivery addr = new Delivery();
            addr.setPostalCode(address.getPostalCode());

            Country c = countryService.getByCode(address.getCountryCode());

            if(c==null) {
                c = merchantStore.getCountry();
            }
            addr.setCountry(c);


            Customer temp = new Customer();
            temp.setAnonymous(true);
            temp.setDelivery(addr);

            ShippingQuote quote = orderFacade.getShippingQuote(temp, cart, merchantStore, language);

            ShippingSummary summary = orderFacade.getShippingSummary(quote, merchantStore, language);

            ReadableShippingSummary shippingSummary = new ReadableShippingSummary();
            ReadableShippingSummaryPopulator populator = new ReadableShippingSummaryPopulator();
            populator.setPricingService(pricingService);
            populator.populate(summary, shippingSummary, merchantStore, language);

            List<ShippingOption> options = quote.getShippingOptions();

            if (!CollectionUtils.isEmpty(options)) {

                for (ShippingOption shipOption : options) {

                    StringBuilder moduleName = new StringBuilder();
                    moduleName.append("module.shipping.").append(shipOption.getShippingModuleCode());

                    String carrier =
                            messages.getMessage(
                                    moduleName.toString(), new String[] {merchantStore.getStorename()}, locale);

                    String note = messages.getMessage(moduleName.append(".note").toString(), locale, "");

                    shipOption.setDescription(carrier);
                    shipOption.setNote(note);

                    // option name
                    if (!StringUtils.isBlank(shipOption.getOptionCode())) {
                        // try to get the translate
                        StringBuilder optionCodeBuilder = new StringBuilder();
                        try {

                            optionCodeBuilder
                                    .append("module.shipping.")
                                    .append(shipOption.getShippingModuleCode());
                            String optionName = messages.getMessage(optionCodeBuilder.toString(), new String[]{merchantStore.getStorename()},locale);
                            shipOption.setOptionName(optionName);
                        } catch (Exception e) { // label not found
                            LOGGER.warn("No shipping code found for " + optionCodeBuilder.toString());
                        }
                    }
                }

                shippingSummary.setShippingOptions(options);
            }

            return shippingSummary;

        } catch (Exception e) {
            LOGGER.error("Error while getting shipping quote", e);
            try {
                response.sendError(503, "Error while getting shipping quote" + e.getMessage());
            } catch (Exception ignore) {
            }
            return null;
        }
    }



    @RequestMapping(value = { "/private/shipping/list/products" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")})
    public CommonResultDTO<List<ReadableOrderProduct>> queryShippingOrderListProducts(
            @RequestParam(value = "shippingOrderId", required = false) Long shippingOrderId,
            @ApiIgnore Language language) {

        try {
            List<ReadableOrderProduct> result  = orderFacade.queryShippingOrderListProducts(LocaleUtils.getLocale(language), language, shippingOrderId);
            return CommonResultDTO.ofSuccess(result);
        } catch (Exception e) {
            LOGGER.error("admin queryShippingOrderListProducts error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = { "/private/shipping/order_product/list" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")})
    public CommonResultDTO<ReadableOrderProductShippingList> queryShippingOrderProductList(
            @RequestParam(value = "count", required = false, defaultValue = DEFAULT_ORDER_LIST_COUNT) Integer count,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "orderId", required = false) Long orderId,
            @RequestParam(value = "productName", required = false) String productName,
            @ApiIgnore Language language) {


        ShippingOrderProductQuery query = new ShippingOrderProductQuery();
        query.setLegacyPagination(false);
        query.setOrderId(orderId);
        query.setProductName(productName);
        if (page != null) {
            query.setStartPage(page);
        }

        if (count != null) {
            query.setPageSize(count);
        }

        try {
            ReadableOrderProductShippingList result  = orderFacade.queryShippingOrderProductsList(LocaleUtils.getLocale(language), language, query);
            return CommonResultDTO.ofSuccess(result);
        } catch (Exception e) {
            LOGGER.error("admin query order shipping list error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @RequestMapping(value = { "/private/shipping/list" }, method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @ApiImplicitParams({@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko")})
    public CommonResultDTO<ReadableShippingDocumentOrderList> queryShippingList(
            @RequestParam(value = "count", required = false, defaultValue = DEFAULT_ORDER_LIST_COUNT) Integer count,
            @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
            @RequestParam(value = "orderId", required = false) Long orderId,
            @RequestParam(value = "createStartTime", required = false) Long createStartTime,
            @RequestParam(value = "createEndTime", required = false) Long createEndTime,
            @RequestParam(value = "productName", required = false) String productName,
            @ApiIgnore Language language) {


        ShippingOrderProductQuery query = new ShippingOrderProductQuery();
        query.setLegacyPagination(false);
        query.setOrderId(orderId);
        query.setProductName(productName);
        if (page != null) {
            query.setStartPage(page);
        }

        if (count != null) {
            query.setPageSize(count);
        }
        query.setCreateStartTime(createStartTime);
        query.setCreateEndTime(createEndTime);

        try {
            ReadableShippingDocumentOrderList result  = orderFacade.queryShippingDocumentOrderList(LocaleUtils.getLocale(language), language, query);
            return CommonResultDTO.ofSuccess(result);
        } catch (Exception e) {
            LOGGER.error("admin query order shipping list error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }



    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(value = { "/private/shipping/create"})
    @ResponseBody
    public CommonResultDTO<Void> createShipping(
            @Valid @RequestParam(name = "createTime") Long createTime) {
        try {
            if (!DateUtil.isTimestampGreaterThanOrEqualToday(createTime)){
                return CommonResultDTO.ofSuccess(null);
            }
            orderFacade.createShippingDocumentOrder(createTime);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("updateSellerOrderStatus error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping(value = { "/private/shipping/delete/{id}" })
    public CommonResultDTO<Void> delete(@PathVariable Long id) {
        try {
            orderFacade.deleteShippingDocumentOrder(id);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("delete error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @PostMapping(value = { "/private/shipping/{id}/add/order_product/{orderProductId}" })
    public CommonResultDTO<Void> addShippingProductByOrderProductId(@PathVariable Long orderProductId, @PathVariable Long id) {
        try {
            orderFacade.addShippingProductByOrderProductId(id, orderProductId);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("addShippingProductByOrderProductId error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    @PostMapping(value = { "/private/shipping/{id}/remove/order_product/{orderProductId}" })
    public CommonResultDTO<Void> removeShippingProductByOrderProductId(@PathVariable Long orderProductId, @PathVariable Long id) {
        try {
            orderFacade.removeShippingProductByOrderProductId(id, orderProductId);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("removeShippingProductByOrderProductId error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }




    @PostMapping(value = { "/private/shipping/generalDocument"})
    public @ResponseBody CommonResultDTO<Void> saveGeneralDocument(
            @Valid @RequestBody PersistableGeneralDocument persistableGeneralDocument) {
        try {
            fulfillmentFacade.saveGeneralDocument(persistableGeneralDocument);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("save general document by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }



    @PostMapping(value = {"/private/shipping/invoice_packing"})
    public @ResponseBody CommonResultDTO<Void> saveInvoicePackingForm(
            @Valid @RequestBody PersistableInvoicePackingForm persistableInvoicePackingForm) {
        try {
            fulfillmentFacade.saveInvoicePackingForm(persistableInvoicePackingForm);
            return CommonResultDTO.ofSuccess();
        }catch (Exception e){
            LOGGER.error("save invoice packing form by order id error", e);
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), ErrorCodeEnums.SYSTEM_ERROR.getErrorMessage(), e.getMessage());
        }
    }

}
