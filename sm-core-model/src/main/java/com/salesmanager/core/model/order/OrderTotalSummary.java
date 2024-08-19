package com.salesmanager.core.model.order;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Output object after total calculation
 * @author Carl Samson
 *
 */
@Data
public class OrderTotalSummary implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	private BigDecimal subTotal = new BigDecimal(0);//one time price for items

	private BigDecimal total = new BigDecimal(0);//final price

	private BigDecimal taxTotal = new BigDecimal(0);//total of taxes


	//手续费
	private BigDecimal productHandlingFeePriceTotal = new BigDecimal(0);//total of taxes
	//运费
	private BigDecimal shippingPriceTotal = new BigDecimal(0);//total of taxes

	//增值服务费
	private BigDecimal additionalServicesPriceTotal = new BigDecimal(0);//total of taxes

	//erp费用
	private BigDecimal erpPriceTotal = new BigDecimal(0);//total of taxes


	private List<OrderTotal> totals;//all other fees (tax, shipping ....)


}
