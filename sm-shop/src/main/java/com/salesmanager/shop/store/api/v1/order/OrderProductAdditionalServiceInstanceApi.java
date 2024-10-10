package com.salesmanager.shop.store.api.v1.order;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.enmus.AdditionalServiceInstanceStatusEnums;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.order.ReadableOrderProduct;
import com.salesmanager.shop.model.order.v0.ReadableOrder;
import com.salesmanager.shop.model.order.v1.PersistableOrderProductAdditionalServiceInstance;
import com.salesmanager.shop.model.order.v1.ReadableOrderProductAdditionalServiceInstance;
import com.salesmanager.shop.model.shop.CommonResultDTO;
import com.salesmanager.shop.store.controller.order.facade.OrderFacade;
import com.salesmanager.shop.store.controller.order.facade.OrderProductAdditionalServiceInstanceFacade;
import com.salesmanager.shop.store.controller.order.facade.OrderProductFacade;
import com.salesmanager.shop.store.error.ErrorCodeEnums;
import io.swagger.annotations.Api;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Api(tags = {"Ordering Product Additional Service Api"})
@SwaggerDefinition(tags = {@Tag(name = "Order Product addition service flow resource", description = "Manage order product addition service (create, list get)")})
public class OrderProductAdditionalServiceInstanceApi {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderProductAdditionalServiceInstanceApi.class);

    @Inject
    private OrderProductFacade orderProductFacade;

    @Inject
    private OrderProductAdditionalServiceInstanceFacade orderProductAdditionalServiceInstanceFacade;

    @Inject
    private OrderFacade orderFacade;

    @Inject
    private CustomerService customerService;

    /**
     * This method returns list of order product additional service. Contains additional service status and history
     *
     * @param id
     * @param merchantStore
     * @param language
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = {"/auth/order_products/{id}/additional_service/history"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<List<ReadableOrderProductAdditionalServiceInstance>> getAdditionalServiceForCustomer(@PathVariable Long id,
                                                                                                                @ApiIgnore MerchantStore merchantStore,
                                                                                                                @ApiIgnore Language language,
                                                                                                                HttpServletRequest request,
                                                                                                                HttpServletResponse response) {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        // get customer id
        Customer customer = customerService.getByNick(userName);
        if (customer == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), "Customer [" + userName + "] not found");
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(id, merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + id);
        }

        try {
            // check data access permission
            ReadableOrder customerReadableOrder = orderFacade.getCustomerReadableOrder(readableOrderProduct.getOrderId(), customer, language);
            if (customerReadableOrder == null) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "No permission to access this order");
            }
        } catch (Exception e) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }

        List<ReadableOrderProductAdditionalServiceInstance> readableOrderProductAdditionalService = orderProductAdditionalServiceInstanceFacade.listReadableOrderProductAdditionalServiceInstance(readableOrderProduct, merchantStore, language);

        return CommonResultDTO.ofSuccess(readableOrderProductAdditionalService);
    }

    /**
     * This method returns list of order product additional service. Contains additional service status and history
     *
     * @param id
     * @param merchantStore
     * @param language
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = {"/private/order_products/{id}/additional_service/history"})
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<List<ReadableOrderProductAdditionalServiceInstance>> getAdditionalServiceForAdmin(@PathVariable Long id,
                                                                                                             @ApiIgnore MerchantStore merchantStore,
                                                                                                             @ApiIgnore Language language,
                                                                                                             HttpServletRequest request,
                                                                                                             HttpServletResponse response) {
        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(id, merchantStore, language);

        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + id);
        }

        List<ReadableOrderProductAdditionalServiceInstance> readableOrderProductAdditionalService = orderProductAdditionalServiceInstanceFacade.listReadableOrderProductAdditionalServiceInstance(readableOrderProduct, merchantStore, language);

        return CommonResultDTO.ofSuccess(readableOrderProductAdditionalService);
    }

    @PostMapping(value = "/private/order_products/additional_service/history")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Boolean> save(@Valid @RequestBody PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance,
                                         @ApiIgnore MerchantStore merchantStore,
                                         @ApiIgnore Language language,
                                         HttpServletRequest request,
                                         HttpServletResponse response) {
        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(persistableOrderProductAdditionalServiceInstance.getOrderProductId(), merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + persistableOrderProductAdditionalServiceInstance.getOrderProductId());
        }

        Boolean success = null;
        try {
            success = orderProductAdditionalServiceInstanceFacade.saveAdditionalServiceInstance(readableOrderProduct, persistableOrderProductAdditionalServiceInstance, merchantStore, language);

            // TODO: send notice
            if (persistableOrderProductAdditionalServiceInstance.getSendNotice()) {
                // do nothing
            }
        } catch (Exception e) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }
        return CommonResultDTO.ofSuccess(success);
    }

    @PostMapping(value = "/auth/order_products/additional_service/history/reply")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Boolean> reply(@Valid @RequestBody PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance,
                                          @ApiIgnore MerchantStore merchantStore,
                                          @ApiIgnore Language language,
                                          HttpServletRequest request,
                                          HttpServletResponse response) {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        // get customer id
        Customer customer = customerService.getByNick(userName);
        if (customer == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), "Customer [" + userName + "] not found");
        }
        persistableOrderProductAdditionalServiceInstance.setReplyFrom(userName);


        ReadableOrderProductAdditionalServiceInstance readableOrderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceFacade.getById(persistableOrderProductAdditionalServiceInstance.getId(), merchantStore, language);
        if (readableOrderProductAdditionalServiceInstance == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProductAdditionalServiceInstanceById is null for id " + persistableOrderProductAdditionalServiceInstance.getId());
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(readableOrderProductAdditionalServiceInstance.getOrderProductId(), merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + readableOrderProductAdditionalServiceInstance.getOrderProductId());
        }

        try {
            // check data access permission
            ReadableOrder customerReadableOrder = orderFacade.getCustomerReadableOrder(readableOrderProduct.getOrderId(), customer, language);
            if (customerReadableOrder == null) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "No permission to access this order");
            }
        } catch (Exception e) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }

        persistableOrderProductAdditionalServiceInstance.setMessageContent(readableOrderProductAdditionalServiceInstance.getMessageContent());
        Boolean success = orderProductAdditionalServiceInstanceFacade.replyAdditionalServiceInstance(readableOrderProduct, persistableOrderProductAdditionalServiceInstance, merchantStore, language);
        return CommonResultDTO.ofSuccess(success);
    }

    @DeleteMapping(value = "/auth/order_products/additional_service/history/reply")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Boolean> delReply(@Valid @RequestBody PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance,
                                             @ApiIgnore MerchantStore merchantStore,
                                             @ApiIgnore Language language,
                                             HttpServletRequest request,
                                             HttpServletResponse response) {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        // get customer id
        Customer customer = customerService.getByNick(userName);
        if (customer == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), "Customer [" + userName + "] not found");
        }
        persistableOrderProductAdditionalServiceInstance.setReplyFrom(userName);

        Long id = persistableOrderProductAdditionalServiceInstance.getId();
        ReadableOrderProductAdditionalServiceInstance readableOrderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceFacade.getById(id, merchantStore, language);
        if (readableOrderProductAdditionalServiceInstance == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProductAdditionalServiceInstanceById is null for id " + id);
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(readableOrderProductAdditionalServiceInstance.getOrderProductId(), merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + readableOrderProductAdditionalServiceInstance.getOrderProductId());
        }

        try {
            // check data access permission
            ReadableOrder customerReadableOrder = orderFacade.getCustomerReadableOrder(readableOrderProduct.getOrderId(), customer, language);
            if (customerReadableOrder == null) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "No permission to access this order");
            }
        } catch (Exception e) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }

        persistableOrderProductAdditionalServiceInstance.setMessageContent(readableOrderProductAdditionalServiceInstance.getMessageContent());
        Boolean success = orderProductAdditionalServiceInstanceFacade.delAdditionalServiceInstanceReply(readableOrderProduct, persistableOrderProductAdditionalServiceInstance, merchantStore, language);
        return CommonResultDTO.ofSuccess(success);
    }

    @PutMapping(value = "/auth/order_products/additional_service/history/reply")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Boolean> updateReply(@Valid @RequestBody PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalServiceInstance,
                                                @ApiIgnore MerchantStore merchantStore,
                                                @ApiIgnore Language language,
                                                HttpServletRequest request,
                                                HttpServletResponse response) {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        // get customer id
        Customer customer = customerService.getByNick(userName);
        if (customer == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), "Customer [" + userName + "] not found");
        }
        persistableOrderProductAdditionalServiceInstance.setReplyFrom(userName);

        Long id = persistableOrderProductAdditionalServiceInstance.getId();
        ReadableOrderProductAdditionalServiceInstance readableOrderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceFacade.getById(id, merchantStore, language);
        if (readableOrderProductAdditionalServiceInstance == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProductAdditionalServiceInstanceById is null for id " + id);
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(readableOrderProductAdditionalServiceInstance.getOrderProductId(), merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + readableOrderProductAdditionalServiceInstance.getOrderProductId());
        }

        try {
            // check data access permission
            ReadableOrder customerReadableOrder = orderFacade.getCustomerReadableOrder(readableOrderProduct.getOrderId(), customer, language);
            if (customerReadableOrder == null) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "No permission to access this order");
            }
        } catch (Exception e) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }

        persistableOrderProductAdditionalServiceInstance.setMessageContent(readableOrderProductAdditionalServiceInstance.getMessageContent());
        Boolean success = orderProductAdditionalServiceInstanceFacade.updateAdditionalServiceInstanceReply(readableOrderProduct, persistableOrderProductAdditionalServiceInstance, merchantStore, language);
        return CommonResultDTO.ofSuccess(success);
    }

    @PutMapping(value = "/auth/order_products/additional_service/history/confirm")
    @ResponseStatus(HttpStatus.OK)
    @ResponseBody
    public CommonResultDTO<Boolean> updateStatus(@Valid @RequestBody PersistableOrderProductAdditionalServiceInstance persistableOrderProductAdditionalService,
                                                 @ApiIgnore MerchantStore merchantStore,
                                                 @ApiIgnore Language language,
                                                 HttpServletRequest request,
                                                 HttpServletResponse response) {
        Principal principal = request.getUserPrincipal();
        String userName = principal.getName();

        // get customer id
        Customer customer = customerService.getByNick(userName);
        if (customer == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.USER_ERROR.getErrorCode(), "Customer [" + userName + "] not found");
        }

        Long id = persistableOrderProductAdditionalService.getId();
        ReadableOrderProductAdditionalServiceInstance readableOrderProductAdditionalServiceInstance = orderProductAdditionalServiceInstanceFacade.getById(id, merchantStore, language);
        if (readableOrderProductAdditionalServiceInstance == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProductAdditionalServiceInstanceById is null for id " + id);
        }

        ReadableOrderProduct readableOrderProduct = orderProductFacade.getReadableOrderProduct(readableOrderProductAdditionalServiceInstance.getOrderProductId(), merchantStore, language);
        if (readableOrderProduct == null) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "OrderProduct is null for id " + readableOrderProductAdditionalServiceInstance.getOrderProductId());
        }

        try {
            // check data access permission
            ReadableOrder customerReadableOrder = orderFacade.getCustomerReadableOrder(readableOrderProduct.getOrderId(), customer, language);
            if (customerReadableOrder == null) {
                return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), "No permission to access this order");
            }
        } catch (Exception e) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.SYSTEM_ERROR.getErrorCode(), e.getMessage());
        }

        String status = persistableOrderProductAdditionalService.getStatus();
        AdditionalServiceInstanceStatusEnums additionalServiceInstanceStatusEnums;
        try {
            additionalServiceInstanceStatusEnums = AdditionalServiceInstanceStatusEnums.valueOf(status);
        } catch (IllegalArgumentException e) {
            return CommonResultDTO.ofFailed(ErrorCodeEnums.PARAM_ERROR.getErrorCode(), "Unknown status: " + status);
        }

        Boolean success = orderProductAdditionalServiceInstanceFacade.confirmAdditionalServiceInstance(id, additionalServiceInstanceStatusEnums, merchantStore, language);
        return CommonResultDTO.ofSuccess(success);
    }


}
