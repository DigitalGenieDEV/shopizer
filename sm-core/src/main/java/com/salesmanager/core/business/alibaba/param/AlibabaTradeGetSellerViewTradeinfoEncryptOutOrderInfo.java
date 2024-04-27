package com.salesmanager.core.business.alibaba.param;

public class AlibabaTradeGetSellerViewTradeinfoEncryptOutOrderInfo {

    private String outPlatformOrderNo;

    /**
     * @return 外部订单号
     */
    public String getOutPlatformOrderNo() {
        return outPlatformOrderNo;
    }

    /**
     *     外部订单号     *
     * 参数示例：<pre>25662254541</pre>     
     *    
     */
    public void setOutPlatformOrderNo(String outPlatformOrderNo) {
        this.outPlatformOrderNo = outPlatformOrderNo;
    }

    private String outPlatformCode;

    /**
     * @return 下游平台,淘宝-thyny，天猫-tm，淘特-taote，阿里巴巴C2M-c2m，京东-jingdong，拼多多-pinduoduo，微信-weixin，跨境-kuajing，快手-kuaishou，有赞-youzan，抖音-douyin，寺库-siku，美团团好货-meituan，小红书-xiaohongshu，当当-dangdang，苏宁-suning，大V店-davdian，行云-xingyun，蜜芽-miya，菠萝派商城-boluo，其他-other
     */
    public String getOutPlatformCode() {
        return outPlatformCode;
    }

    /**
     *     下游平台,淘宝-thyny，天猫-tm，淘特-taote，阿里巴巴C2M-c2m，京东-jingdong，拼多多-pinduoduo，微信-weixin，跨境-kuajing，快手-kuaishou，有赞-youzan，抖音-douyin，寺库-siku，美团团好货-meituan，小红书-xiaohongshu，当当-dangdang，苏宁-suning，大V店-davdian，行云-xingyun，蜜芽-miya，菠萝派商城-boluo，其他-other     *
     * 参数示例：<pre>tm</pre>     
     *    
     */
    public void setOutPlatformCode(String outPlatformCode) {
        this.outPlatformCode = outPlatformCode;
    }

    private String outPlatformAppkey;

    /**
     * @return 获取下游订单信息的下游平台的appkey
     */
    public String getOutPlatformAppkey() {
        return outPlatformAppkey;
    }

    /**
     *     获取下游订单信息的下游平台的appkey     *
     * 参数示例：<pre>65345</pre>     
     *    
     */
    public void setOutPlatformAppkey(String outPlatformAppkey) {
        this.outPlatformAppkey = outPlatformAppkey;
    }

    private String oaid;

    /**
     * @return 淘宝oaid
     */
    public String getOaid() {
        return oaid;
    }

    /**
     *     淘宝oaid     *
     * 参数示例：<pre>xxx-xxxx-xxx</pre>     
     *    
     */
    public void setOaid(String oaid) {
        this.oaid = oaid;
    }

    private String encryptReceiverName;

    /**
     * @return 下游加密收货人姓名
     */
    public String getEncryptReceiverName() {
        return encryptReceiverName;
    }

    /**
     *     下游加密收货人姓名     *
     * 参数示例：<pre>***</pre>     
     *    
     */
    public void setEncryptReceiverName(String encryptReceiverName) {
        this.encryptReceiverName = encryptReceiverName;
    }

    private String encryptReceiverMobile;

    /**
     * @return 下游加密收货人电话
     */
    public String getEncryptReceiverMobile() {
        return encryptReceiverMobile;
    }

    /**
     *     下游加密收货人电话     *
     * 参数示例：<pre>***</pre>     
     *    
     */
    public void setEncryptReceiverMobile(String encryptReceiverMobile) {
        this.encryptReceiverMobile = encryptReceiverMobile;
    }

    private String encryptReceiverAddress;

    /**
     * @return 下游加密收货人地址
     */
    public String getEncryptReceiverAddress() {
        return encryptReceiverAddress;
    }

    /**
     *     下游加密收货人地址     *
     * 参数示例：<pre>***</pre>     
     *    
     */
    public void setEncryptReceiverAddress(String encryptReceiverAddress) {
        this.encryptReceiverAddress = encryptReceiverAddress;
    }

    private String outPatformExtraInfo;

    /**
     * @return 其他扩展信息
     */
    public String getOutPatformExtraInfo() {
        return outPatformExtraInfo;
    }

    /**
     *     其他扩展信息     *
     * 参数示例：<pre>{}</pre>     
     *    
     */
    public void setOutPatformExtraInfo(String outPatformExtraInfo) {
        this.outPatformExtraInfo = outPatformExtraInfo;
    }

    private String outShopId;

    /**
     * @return 下游平台shopId
     */
    public String getOutShopId() {
        return outShopId;
    }

    /**
     *     下游平台shopId     *
     * 参数示例：<pre>21343123</pre>     
     *    
     */
    public void setOutShopId(String outShopId) {
        this.outShopId = outShopId;
    }

    private ComAlibabaOceanOpenplatformBizTradeParamOutAddress outOriginAddress;

    /**
     * @return 外部原始地址信息	
     */
    public ComAlibabaOceanOpenplatformBizTradeParamOutAddress getOutOriginAddress() {
        return outOriginAddress;
    }

    /**
     *     外部原始地址信息	     *
     * 参数示例：<pre>{}</pre>     
     *    
     */
    public void setOutOriginAddress(ComAlibabaOceanOpenplatformBizTradeParamOutAddress outOriginAddress) {
        this.outOriginAddress = outOriginAddress;
    }

    private String outShopName;

    /**
     * @return 下游平台店铺名称
     */
    public String getOutShopName() {
        return outShopName;
    }

    /**
     *     下游平台店铺名称     *
     * 参数示例：<pre>三生智能</pre>     
     *    
     */
    public void setOutShopName(String outShopName) {
        this.outShopName = outShopName;
    }

}
