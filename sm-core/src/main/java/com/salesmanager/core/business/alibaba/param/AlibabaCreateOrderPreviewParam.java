package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaCreateOrderPreviewParam extends AbstractAPIRequest<AlibabaCreateOrderPreviewResult> {

    public AlibabaCreateOrderPreviewParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.createOrder.preview", 1);
    }

    private AlibabaTradeFastAddress addressParam;

    /**
     * @return 收货地址信息
     */
    public AlibabaTradeFastAddress getAddressParam() {
        return addressParam;
    }

    /**
     *     收货地址信息     *
     * 参数示例：<pre>{"address":"网商路699号","phone": "0517-88990077","mobile": "15251667788","fullName": "张三","postCode": "000000","areaText": "滨江区","townText": "","cityText": "杭州市","provinceText": "浙江省"}</pre>     
     *
     */
    public void setAddressParam(AlibabaTradeFastAddress addressParam) {
        this.addressParam = addressParam;
    }

    private AlibabaTradeFastCargo[] cargoParamList;

    /**
     * @return 商品信息
     */
    public AlibabaTradeFastCargo[] getCargoParamList() {
        return cargoParamList;
    }

    /**
     *     商品信息     *
     * 参数示例：<pre>[{"specId": "b266e0726506185beaf205cbae88530d","quantity": 5,"offerId": 554456348334},{"specId": "2ba3d63866a71fbae83909d9b4814f01","quantity": 6,"offerId": 554456348334}]</pre>     
     *
     */
    public void setCargoParamList(AlibabaTradeFastCargo[] cargoParamList) {
        this.cargoParamList = cargoParamList;
    }

    private AlibabaTradeFastInvoice invoiceParam;

    /**
     * @return 发票信息
     */
    public AlibabaTradeFastInvoice getInvoiceParam() {
        return invoiceParam;
    }

    /**
     *     发票信息     *
     * 参数示例：<pre>{"invoiceType":0,"cityText": "杭州市","provinceText": "浙江省","address": "网商路699号","phone": "0517-88990077","mobile": "15251667788","fullName": "张五","postCode": "000000","areaText": "滨江区","companyName": "测试公司","taxpayerIdentifier": "123455"}</pre>     
     *
     */
    public void setInvoiceParam(AlibabaTradeFastInvoice invoiceParam) {
        this.invoiceParam = invoiceParam;
    }

    private String flow;

    /**
     * @return general（创建大市场订单），fenxiao（创建分销订单）,saleproxy流程将校验分销关系,paired(火拼下单),boutiquefenxiao(精选货源分销价下单，采购量1个使用包邮)， boutiquepifa(精选货源批发价下单，采购量大于2使用). flow如果为空的情况，会比价择优预览，并返回最优下单方式flow
     */
    public String getFlow() {
        return flow;
    }

    /**
     *     general（创建大市场订单），fenxiao（创建分销订单）,saleproxy流程将校验分销关系,paired(火拼下单),boutiquefenxiao(精选货源分销价下单，采购量1个使用包邮)， boutiquepifa(精选货源批发价下单，采购量大于2使用). flow如果为空的情况，会比价择优预览，并返回最优下单方式flow     *
     * 参数示例：<pre>general</pre>     
     *
     */
    public void setFlow(String flow) {
        this.flow = flow;
    }

    private String instanceId;

    /**
     * @return 批发团instanceId,从alibaba.pifatuan.product.list获取
     */
    public String getInstanceId() {
        return instanceId;
    }

    /**
     *     批发团instanceId,从alibaba.pifatuan.product.list获取     *
     * 参数示例：<pre>4063139_1662080400000</pre>     
     *
     */
    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    private AlibabaTradeFastCreateOrderEncryptOutOrderInfo encryptOutOrderInfo;

    /**
     * @return 下游加密订单信息，用于下游打单使用
     */
    public AlibabaTradeFastCreateOrderEncryptOutOrderInfo getEncryptOutOrderInfo() {
        return encryptOutOrderInfo;
    }

    /**
     *     下游加密订单信息，用于下游打单使用     *
     * 参数示例：<pre>{}</pre>     
     *
     */
    public void setEncryptOutOrderInfo(AlibabaTradeFastCreateOrderEncryptOutOrderInfo encryptOutOrderInfo) {
        this.encryptOutOrderInfo = encryptOutOrderInfo;
    }

    private String proxySettleRecordId;

    /**
     * @return 分账普通下单采购单id，交易flow为“proxy”
     */
    public String getProxySettleRecordId() {
        return proxySettleRecordId;
    }

    /**
     *     分账普通下单采购单id，交易flow为“proxy”     *
     * 参数示例：<pre>4051300002</pre>     
     *
     */
    public void setProxySettleRecordId(String proxySettleRecordId) {
        this.proxySettleRecordId = proxySettleRecordId;
    }

    private String inventoryMode;

    /**
     * @return 库存模式，jit（jit模式）或 cang（仓发模式）,目前只提供给AE使用	
     */
    public String getInventoryMode() {
        return inventoryMode;
    }

    /**
     *     库存模式，jit（jit模式）或 cang（仓发模式）,目前只提供给AE使用	     *
     * 参数示例：<pre>jit</pre>     
     *
     */
    public void setInventoryMode(String inventoryMode) {
        this.inventoryMode = inventoryMode;
    }

    private String outOrderId;

    /**
     * @return 外部订单号	
     */
    public String getOutOrderId() {
        return outOrderId;
    }

    /**
     *     外部订单号	     *
     * 参数示例：<pre>988129883123</pre>     
     *
     */
    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    private String pickupService;

    /**
     * @return 上门揽收,目前AE供货可用，其他场景暂不开通
     */
    public String getPickupService() {
        return pickupService;
    }

    /**
     *     上门揽收,目前AE供货可用，其他场景暂不开通     *
     * 参数示例：<pre>y或n,默认为n</pre>     
     *
     */
    public void setPickupService(String pickupService) {
        this.pickupService = pickupService;
    }

}
