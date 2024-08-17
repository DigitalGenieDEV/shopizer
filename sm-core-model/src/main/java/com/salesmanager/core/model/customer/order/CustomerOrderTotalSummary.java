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

    //手续费
    private BigDecimal productHandlingFeePriceTotal = new BigDecimal(0);//total of taxes
    //运费
    private BigDecimal shippingPriceTotal = new BigDecimal(0);//total of taxes

    //增值服务费
    private BigDecimal additionalServicesPriceTotal = new BigDecimal(0);//total of taxes

    //erp费用
    private BigDecimal erpPriceTotal = new BigDecimal(0);//total of taxes


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

    public BigDecimal getProductHandlingFeePriceTotal() {
        return productHandlingFeePriceTotal;
    }

    public void setProductHandlingFeePriceTotal(BigDecimal productHandlingFeePriceTotal) {
        this.productHandlingFeePriceTotal = productHandlingFeePriceTotal;
    }

    public BigDecimal getShippingPriceTotal() {
        return shippingPriceTotal;
    }

    public void setShippingPriceTotal(BigDecimal shippingPriceTotal) {
        this.shippingPriceTotal = shippingPriceTotal;
    }

    public BigDecimal getAdditionalServicesPriceTotal() {
        return additionalServicesPriceTotal;
    }

    public void setAdditionalServicesPriceTotal(BigDecimal additionalServicesPriceTotal) {
        this.additionalServicesPriceTotal = additionalServicesPriceTotal;
    }

    public BigDecimal getErpPriceTotal() {
        return erpPriceTotal;
    }

    public void setErpPriceTotal(BigDecimal erpPriceTotal) {
        this.erpPriceTotal = erpPriceTotal;
    }
}
