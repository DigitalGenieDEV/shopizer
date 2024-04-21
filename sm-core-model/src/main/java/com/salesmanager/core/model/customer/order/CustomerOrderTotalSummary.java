package com.salesmanager.core.model.customer.order;

import java.io.Serializable;
import java.math.BigDecimal;

public class CustomerOrderTotalSummary implements Serializable  {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    private BigDecimal subTotal = new BigDecimal(0);//one time price for items
    private BigDecimal total = new BigDecimal(0);//final price
    private BigDecimal taxTotal = new BigDecimal(0);//total of taxes

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTaxTotal() {
        return taxTotal;
    }

    public void setTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = taxTotal;
    }

    public void addSubTotal(BigDecimal subTotal) {
        this.subTotal = this.subTotal.add(subTotal);
    }

    public void addTotal(BigDecimal total) {
        this.total = this.total.add(total);
    }

    public void addTaxTotal(BigDecimal taxTotal) {
        this.taxTotal = this.taxTotal.add(taxTotal);
    }
}
