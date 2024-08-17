package com.salesmanager.shop.model.order;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.salesmanager.shop.model.order.total.ReadableOrderTotal;
import lombok.Data;

@Data
public class ReadableOrderTotalSummary implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String subTotal;//one time price for items
	private String total;//final price
	private String taxTotal;//total of taxes


	//手续费
	private String productHandlingFeePriceTotal ;
	//运费
	private String shippingPriceTotal ;

	//增值服务费
	private String additionalServicesPriceTotal ;

	//erp费用
	private String erpPriceTotal ;
	
	private List<ReadableOrderTotal> totals = new ArrayList<ReadableOrderTotal>();//all other fees (tax, shipping ....)

	public String getSubTotal() {
		return subTotal;
	}

	public void setSubTotal(String subTotal) {
		this.subTotal = subTotal;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getTaxTotal() {
		return taxTotal;
	}

	public void setTaxTotal(String taxTotal) {
		this.taxTotal = taxTotal;
	}

	public List<ReadableOrderTotal> getTotals() {
		return totals;
	}

	public void setTotals(List<ReadableOrderTotal> totals) {
		this.totals = totals;
	}

}
