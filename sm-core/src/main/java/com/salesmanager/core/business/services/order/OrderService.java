package com.salesmanager.core.business.services.order;

import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.customer.Customer;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.order.*;
import com.salesmanager.core.model.order.orderstatus.OrderStatus;
import com.salesmanager.core.model.order.orderstatus.OrderStatusHistory;
import com.salesmanager.core.model.payments.Payment;
import com.salesmanager.core.model.payments.Transaction;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.core.model.shoppingcart.ShoppingCart;
import com.salesmanager.core.model.shoppingcart.ShoppingCartItem;
import org.springframework.data.repository.query.Param;


public interface OrderService extends SalesManagerEntityService<Long, Order> {

	void addOrderStatusHistory(Order order, OrderStatusHistory history)
			throws ServiceException;

	void updateOrderStatus(Long orderId, OrderStatus status);


	void updateOrderTotalPriceByOrderId(Long orderId, BigDecimal totalPrice);

	/**
	 * Can be used to calculates the final prices of all items contained in checkout page
	 * @param orderSummary
	 * @param customer
	 * @param store
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	OrderTotalSummary caculateOrderTotal(OrderSummary orderSummary,
										 Customer customer, MerchantStore store, Language language)
			throws ServiceException;

	OrderTotalSummary caculateOrder(OrderSummary summary, Customer customer, final MerchantStore store, final Language language) throws Exception;

	/**
	 * Can be used to calculates the final prices of all items contained in a ShoppingCart
	 * @param orderSummary
	 * @param store
	 * @param language
	 * @return
	 * @throws ServiceException
	 */
	OrderTotalSummary caculateOrderTotal(OrderSummary orderSummary,
										 MerchantStore store, Language language) throws ServiceException;


	/**
	 * Can be used to calculates the final prices of all items contained in checkout page
	 * @param shoppingCart
	 * @param customer
	 * @param store
	 * @param language
	 * @return  @return {@link OrderTotalSummary}
	 * @throws ServiceException
	 */
	OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart,final Customer customer, final MerchantStore store, final Language language) throws ServiceException;

	/**
	 * Can be used to calculates the final prices of all items contained in a ShoppingCart
	 * @param shoppingCart
	 * @param store
	 * @param language
	 * @return {@link OrderTotalSummary}
	 * @throws ServiceException
	 */
	OrderTotalSummary calculateShoppingCartTotal(final ShoppingCart shoppingCart,final MerchantStore store, final Language language) throws ServiceException;

	ByteArrayOutputStream generateInvoice(MerchantStore store, Order order,
										  Language language) throws ServiceException;

	Order getOrder(Long id, MerchantStore store);

	Order getOrder(Long id, Customer customer);


	/**
	 * For finding orders. Mainly used in the administration tool
	 * @param store
	 * @param criteria
	 * @return
	 */
	OrderList listByStore(MerchantStore store, OrderCriteria criteria);

	/**
	 * For finding customer orders.
	 * @param store
	 * @param criteria
	 * @return
	 */
	OrderList listByCustomer(Customer customer, OrderCustomerCriteria criteria);

	Map<String, Integer> countCustomerOrderByStatus(Customer customer, OrderCustomerCriteria criteria);


	/**
	 * get all orders. Mainly used in the administration tool
	 * @param criteria
	 * @return
	 */
	OrderList getOrders(OrderCriteria criteria, MerchantStore store);

	void saveOrUpdate(Order order) throws ServiceException;

	Order processOrder(Order order, Customer customer,
					   List<ShoppingCartItem> items, OrderTotalSummary summary,
					   Payment payment, MerchantStore store) throws ServiceException;

	Order processOrder(Order order, Customer customer,
					   List<ShoppingCartItem> items, OrderTotalSummary summary,
					   Payment payment, Transaction transaction, MerchantStore store)
			throws ServiceException;



	Long findCustomerOrderIdByOrderId(Long orderId);


	/**
	 * Determines if an Order has download files
	 * @param order
	 * @return
	 * @throws ServiceException
	 */
	boolean hasDownloadFiles(Order order) throws ServiceException;

	/**
	 * List all orders that have been pre-authorized but not captured
	 * @param store
	 * @param startDate
	 * @param endDate
	 * @return
	 * @throws ServiceException
	 */
	List<Order> getCapturableOrders(MerchantStore store, Date startDate, Date endDate) throws ServiceException;

}
