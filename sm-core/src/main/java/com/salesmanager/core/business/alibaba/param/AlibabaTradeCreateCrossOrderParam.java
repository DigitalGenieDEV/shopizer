package com.salesmanager.core.business.alibaba.param;

import com.salesmanager.core.business.alibaba.rawsdk.client.APIId;
import com.salesmanager.core.business.alibaba.rawsdk.common.AbstractAPIRequest;


public class AlibabaTradeCreateCrossOrderParam extends AbstractAPIRequest<AlibabaTradeCreateCrossOrderResult> {

    public AlibabaTradeCreateCrossOrderParam() {
        super();
        oceanApiId = new APIId("com.alibaba.trade", "alibaba.trade.createCrossOrder", 1);
    }

    private String flow;

    /**
     * @return general（创建大市场订单），fenxiao（创建分销订单）,saleproxy流程将校验分销关系,paired(火拼下单),boutiquefenxiao(精选货源分销价下单，采购量1个使用包邮)， boutiquepifa(精选货源批发价下单，采购量大于2使用).
     */
    public String getFlow() {
        return flow;
    }

    /**
     *     general（创建大市场订单），fenxiao（创建分销订单）,saleproxy流程将校验分销关系,paired(火拼下单),boutiquefenxiao(精选货源分销价下单，采购量1个使用包邮)， boutiquepifa(精选货源批发价下单，采购量大于2使用).     *
     * 参数示例：<pre>general</pre>     
     *
     */
    public void setFlow(String flow) {
        this.flow = flow;
    }

    private String message;

    /**
     * @return 买家留言
     */
    public String getMessage() {
        return message;
    }

    /**
     *     买家留言     *
     * 参数示例：<pre>留言</pre>     
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private String isvBizType;

    /**
     * @return 开放平台业务码,默认为cross。cross(跨境业务),cross_daigou（跨境代购业务）
     */
    public String getIsvBizType() {
        return isvBizType;
    }

    /**
     *     开放平台业务码,默认为cross。cross(跨境业务),cross_daigou（跨境代购业务）     *
     * 参数示例：<pre>cross</pre>     
     *
     */
    public void setIsvBizType(String isvBizType) {
        this.isvBizType = isvBizType;
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

    private String tradeType;

    /**
     * @return 由于不同的商品支持的交易方式不同，没有一种交易方式是全局通用的，所以当前下单可使用的交易方式必须通过下单预览接口的tradeModeNameList获取。交易方式类型说明：assureTrade（交易4.0通用担保交易），alipay（大市场通用的支付宝担保交易（目前在做切流，后续会下掉）），period（普通账期交易）, assure（大买家企业采购询报价下单时需要使用的担保交易流程）, creditBuy（诚E赊），bank（银行转账），631staged（631分阶段付款），37staged（37分阶段）；此字段不传则系统默认会选取一个可用的交易方式下单，如果开通了诚E赊默认是creditBuy（诚E赊），未开通诚E赊默认使用的方式是支付宝担宝交易。	
     */
    public String getTradeType() {
        return tradeType;
    }

    /**
     *     由于不同的商品支持的交易方式不同，没有一种交易方式是全局通用的，所以当前下单可使用的交易方式必须通过下单预览接口的tradeModeNameList获取。交易方式类型说明：assureTrade（交易4.0通用担保交易），alipay（大市场通用的支付宝担保交易（目前在做切流，后续会下掉）），period（普通账期交易）, assure（大买家企业采购询报价下单时需要使用的担保交易流程）, creditBuy（诚E赊），bank（银行转账），631staged（631分阶段付款），37staged（37分阶段）；此字段不传则系统默认会选取一个可用的交易方式下单，如果开通了诚E赊默认是creditBuy（诚E赊），未开通诚E赊默认使用的方式是支付宝担宝交易。	     *
     * 参数示例：<pre>assureTrade</pre>     
     *
     */
    public void setTradeType(String tradeType) {
        this.tradeType = tradeType;
    }

    private String shopPromotionId;

    /**
     * @return 店铺优惠ID，通过“创建订单前预览数据接口”获得。为空默认使用默认优惠
     */
    public String getShopPromotionId() {
        return shopPromotionId;
    }

    /**
     *     店铺优惠ID，通过“创建订单前预览数据接口”获得。为空默认使用默认优惠     *
     * 参数示例：<pre>itemCoupon-5600812521_31032085284-398517001570</pre>     
     *
     */
    public void setShopPromotionId(String shopPromotionId) {
        this.shopPromotionId = shopPromotionId;
    }

    private Boolean anonymousBuyer;

    /**
     * @return 是否匿名下单
     */
    public Boolean getAnonymousBuyer() {
        return anonymousBuyer;
    }

    /**
     *     是否匿名下单     *
     *    
     *
     */
    public void setAnonymousBuyer(Boolean anonymousBuyer) {
        this.anonymousBuyer = anonymousBuyer;
    }

    private String fenxiaoChannel;

    /**
     * @return 回流订单下游平台 淘宝-thyny，天猫-tm，淘特-taote，阿里巴巴C2M-c2m，京东-jingdong，拼多多-pinduoduo，微信-weixin，跨境-kuajing，快手-kuaishou，有赞-youzan，抖音-douyin，寺库-siku，美团团好货-meituan，小红书-xiaohongshu，当当-dangdang，苏宁-suning，大V店-davdian，行云-xingyun，蜜芽-miya，菠萝派商城-boluo，快团团-kuaituantuan，其他-other
     */
    public String getFenxiaoChannel() {
        return fenxiaoChannel;
    }

    /**
     *     回流订单下游平台 淘宝-thyny，天猫-tm，淘特-taote，阿里巴巴C2M-c2m，京东-jingdong，拼多多-pinduoduo，微信-weixin，跨境-kuajing，快手-kuaishou，有赞-youzan，抖音-douyin，寺库-siku，美团团好货-meituan，小红书-xiaohongshu，当当-dangdang，苏宁-suning，大V店-davdian，行云-xingyun，蜜芽-miya，菠萝派商城-boluo，快团团-kuaituantuan，其他-other     *
     * 参数示例：<pre>douyin</pre>     
     *
     */
    public void setFenxiaoChannel(String fenxiaoChannel) {
        this.fenxiaoChannel = fenxiaoChannel;
    }

    private String inventoryMode;

    /**
     * @return 库存模式，JIT（jit模式）或 NORMAL（仓发模式）,目前只提供给AE使用
     */
    public String getInventoryMode() {
        return inventoryMode;
    }

    /**
     *     库存模式，JIT（jit模式）或 NORMAL（仓发模式）,目前只提供给AE使用     *
     * 参数示例：<pre>JIT</pre>     
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
     *     外部订单号     *
     * 参数示例：<pre>988129883123</pre>     
     *
     */
    public void setOutOrderId(String outOrderId) {
        this.outOrderId = outOrderId;
    }

    private String pickupService;

    /**
     * @return 上门揽收,目前AE供货可用，其他场景暂不开通.y或n,默认为n
     */
    public String getPickupService() {
        return pickupService;
    }

    /**
     *     上门揽收,目前AE供货可用，其他场景暂不开通.y或n,默认为n     *
     * 参数示例：<pre>n</pre>     
     *
     */
    public void setPickupService(String pickupService) {
        this.pickupService = pickupService;
    }

    private String warehouseCode;

    /**
     * @return 上门揽仓库code
     */
    public String getWarehouseCode() {
        return warehouseCode;
    }

    /**
     *     上门揽仓库code     *
     * 参数示例：<pre>any</pre>     
     *
     */
    public void setWarehouseCode(String warehouseCode) {
        this.warehouseCode = warehouseCode;
    }

    private String preSelectPayChannel;

    /**
     * @return 预选的支付渠道，用作财务订单分流。订单信息查询接口返回：result.exAttributes.preSelectPayChannel ，该值是创建订单接口时传入的预选的支付渠道标记。
     */
    public String getPreSelectPayChannel() {
        return preSelectPayChannel;
    }

    /**
     *     预选的支付渠道，用作财务订单分流。订单信息查询接口返回：result.exAttributes.preSelectPayChannel ，该值是创建订单接口时传入的预选的支付渠道标记。     *
     * 参数示例：<pre>alipay</pre>     
     *
     */
    public void setPreSelectPayChannel(String preSelectPayChannel) {
        this.preSelectPayChannel = preSelectPayChannel;
    }

    private String smallProcurement;

    /**
     * @return 是否小额采购，目前AE供货可用，取值y/n，默认为n
     */
    public String getSmallProcurement() {
        return smallProcurement;
    }

    /**
     *     是否小额采购，目前AE供货可用，取值y/n，默认为n     *
     * 参数示例：<pre>y</pre>     
     *
     */
    public void setSmallProcurement(String smallProcurement) {
        this.smallProcurement = smallProcurement;
    }

    private Boolean noUseRedEnvelope;

    /**
     * @return 不使用红包：true不使用，false使用。默认使用红包
     */
    public Boolean getNoUseRedEnvelope() {
        return noUseRedEnvelope;
    }

    /**
     *     不使用红包：true不使用，false使用。默认使用红包     *
     * 参数示例：<pre>false</pre>     
     *
     */
    public void setNoUseRedEnvelope(Boolean noUseRedEnvelope) {
        this.noUseRedEnvelope = noUseRedEnvelope;
    }

    private String dropshipping;

    /**
     * @return 是否转运订单，取值y/n，默认为n
     */
    public String getDropshipping() {
        return dropshipping;
    }

    /**
     *     是否转运订单，取值y/n，默认为n     *
     * 参数示例：<pre>y</pre>     
     *
     */
    public void setDropshipping(String dropshipping) {
        this.dropshipping = dropshipping;
    }

}
