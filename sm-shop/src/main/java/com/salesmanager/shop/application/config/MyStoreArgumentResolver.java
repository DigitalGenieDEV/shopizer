package com.salesmanager.shop.application.config;

import com.salesmanager.core.business.services.customer.CustomerService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.merchant.MyStore;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Component
public class MyStoreArgumentResolver implements HandlerMethodArgumentResolver {

	private static final Logger LOGGER = LoggerFactory.getLogger(MyStoreArgumentResolver.class);

	@Autowired
	private CustomerService customerService;

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.getParameterType().equals(MyStore.class);
	}

	@Override
	public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
			NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {

		HttpServletRequest httpServletRequest = webRequest.getNativeRequest(HttpServletRequest.class);
		Principal principal = httpServletRequest.getUserPrincipal();
		String userName = principal.getName();

		Customer customer = customerService.getByNick(userName);
		if(customer == null) {
			throw new UnauthorizedException("customer not authorized");
		}
		MerchantStore merchantStore = customer.getMerchantStore();
		return new MyStore(merchantStore);
	}
}
