package com.salesmanager.core.business.services.order.ordertotal;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.inject.Inject;

import com.salesmanager.core.business.repositories.order.OrderRepository;
import com.salesmanager.core.business.repositories.order.OrderTotalRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.business.services.order.OrderServiceImpl;
import com.salesmanager.core.model.order.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.services.catalog.product.ProductService;
import com.salesmanager.core.model.catalog.product.Product;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import com.salesmanager.core.modules.order.total.OrderTotalPostProcessorModule;
import org.springframework.transaction.annotation.Transactional;

@Service("OrderTotalService")
public class OrderTotalServiceImpl  extends SalesManagerEntityServiceImpl<Long, OrderTotal>  implements OrderTotalService {

	private static final Logger LOGGER = LoggerFactory.getLogger(OrderTotalServiceImpl.class);


	@Autowired
	@Resource(name="orderTotalsPostProcessors")
	List<OrderTotalPostProcessorModule> orderTotalPostProcessors;
	
	@Inject
	private ProductService productService;

	private OrderTotalRepository orderTotalRepository;


	@Inject
	public OrderTotalServiceImpl(OrderTotalRepository orderTotalRepository) {
		super(orderTotalRepository);
		this.orderTotalRepository = orderTotalRepository;
	}


	@Override
	@Transactional(readOnly = true)
	public OrderTotalVariation findOrderTotalVariation(OrderSummary summary, Customer customer, MerchantStore store, Language language)
			throws Exception {
	
		RebatesOrderTotalVariation variation = new RebatesOrderTotalVariation();
		
		List<OrderTotal> totals = null;
		
		if(orderTotalPostProcessors != null) {
			for(OrderTotalPostProcessorModule module : orderTotalPostProcessors) {
				//TODO check if the module is enabled from the Admin
				
				List<ShoppingCartItem> items = summary.getProducts();
				for(ShoppingCartItem item : items) {

//					Product product = productService.getBySku(item.getSku(), store, language);
					Product product = productService.getBySku(item.getSku());
					//Product product = productService.getProductForLocale(productId, language, languageService.toLocale(language, store));
					
					OrderTotal orderTotal = module.caculateProductPiceVariation(summary, item, product, customer, store);
					if(orderTotal==null) {
						continue;
					}
					if(totals==null) {
						totals = new ArrayList<OrderTotal>();
						variation.setVariations(totals);
					}
					
					//if product is null it will be catched when invoking the module
					orderTotal.setText(StringUtils.isNoneBlank(orderTotal.getText())?orderTotal.getText():product.getProductDescription().getName());
					variation.getVariations().add(orderTotal);	
				}
			}
		}
		
		
		return variation;
	}

	@Override
	public void updateValueByOrderIdAndModule(BigDecimal value, Long orderId, String module) {
		orderTotalRepository.updateValueByOrderIdAndModule(value, orderId, module);
	}

}
