package com.salesmanager.core.model.order;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

/**
 * Output object after total calculation
 * @author Carl Samson
 *
 */
public class OrderTotalSummary implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;
	private BigDecimal subTotal = new BigDecimal(0);//one time price for items
	private BigDecimal total = new BigDecimal(0);//final price
	private BigDecimal taxTotal = new BigDecimal(0);//total of taxes

	private List<OrderTotal> totals;//all other fees (tax, shipping ....)

	public BigDecimal getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(BigDecimal subTotal) {
		this.subTotal = subTotal != null ? subTotal.setScale(2, RoundingMode.HALF_UP) : null;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total != null ? total.setScale(2, RoundingMode.HALF_UP) : null;
	}

	public List<OrderTotal> getTotals() {
		return totals;
	}

	public void setTotals(List<OrderTotal> totals) {
		this.totals = totals;
	}

	public BigDecimal getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(BigDecimal taxTotal) {
		this.taxTotal = taxTotal != null ? taxTotal.setScale(2, RoundingMode.HALF_UP) : null;
	}

}
