package com.salesmanager.core.business.alibaba.fenxiao.crossborder.param;

public class ComAlibabaOpenapiSharedCommonRecommendResultModel {

    private Boolean success;

    /**
     * @return 是否成功
     */
    public Boolean getSuccess() {
        return success;
    }

    /**
     *     是否成功     *
     * 参数示例：<pre>true</pre>     
     *
     */
    public void setSuccess(Boolean success) {
        this.success = success;
    }

    private String code;

    /**
     * @return 消息码
     */
    public String getCode() {
        return code;
    }

    /**
     *     消息码     *
     * 参数示例：<pre>200</pre>     
     *
     */
    public void setCode(String code) {
        this.code = code;
    }

    private String message;

    /**
     * @return 消息内容
     */
    public String getMessage() {
        return message;
    }

    /**
     *     消息内容     *
     * 参数示例：<pre>成功</pre>     
     *
     */
    public void setMessage(String message) {
        this.message = message;
    }

    private ComAlibabaCbuOfferRecommendModelProductInfoModel[] result;

    /**
     * @return 结果
     */
    public ComAlibabaCbuOfferRecommendModelProductInfoModel[] getResult() {
        return result;
    }

    /**
     *     结果     *
     * 参数示例：<pre>{   "result": [     {       "subjectTrans": "Stainless steel corn Shaver peeling corn artifact household kitchen thresher corn kernel separator stripper tool",       "priceInfo": {         "price": "1.70",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 4861,       "sellerIdentities": [],       "traceInfo": "object_id%40666353603800%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "不锈钢玉米刨 剥玉米神器家用厨房脱粒器玉米粒分离器剥离器工具",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN01mMCr3s1LkqgqluWHA_!!2209441141338-0-cib.jpg",       "onePsale": false,       "offerId": 666353603800,       "repurchaseRate": "22%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Lamen Noodle Bowl Japanese Bowl Tableware Noodle Bowl Ceramic Bowl Commercial Rice Bowl Bucket Bowl Large Bowl Malatang Vegetable Soup Noodle Bowl",       "priceInfo": {         "price": "8.30",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 1839,       "sellerIdentities": [],       "traceInfo": "object_id%40606760936648%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "拉面碗日式碗餐具面碗陶瓷碗商用饭碗斗笠碗大碗麻辣烫冒菜汤面碗",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/2020/102/168/14732861201_1132645567.jpg",       "onePsale": false,       "offerId": 606760936648,       "repurchaseRate": "19%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Factory direct supply men's bag shoulder bag men's business PU messenger bag men's trendy fashion shoulder bag men's bag",       "priceInfo": {         "price": "26.00",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 640,       "sellerIdentities": [],       "traceInfo": "object_id%40543978204894%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "厂家直供男包单肩包男商务PU斜挎包男士潮流时尚单肩包包男士包",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/2017/575/089/3773980575_1153808554.jpg",       "onePsale": false,       "offerId": 543978204894,       "repurchaseRate": "32%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Storage Box Wardrobe Clothes Clothes Quilt Storage Box Household Transparent Plastic Folding Box Snack Toy Storage Box",       "priceInfo": {         "price": "25.48",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 410,       "sellerIdentities": [         "powerful_merchants"       ],       "traceInfo": "object_id%40694615731159%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "收纳箱衣柜衣服衣物被子储物盒家用透明塑料折叠箱零食玩具整理箱",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN01kZMq9n2CzCtIrrmFy_!!3926048544-0-cib.jpg",       "onePsale": false,       "offerId": 694615731159,       "repurchaseRate": "33%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Fake Flower Five-headed Phalaenopsis Bonsai Set Artificial Flower Factory Direct Supply Artificial Green Plant Creative Ornaments Simulation Potted Plant",       "priceInfo": {         "price": "7.03",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 909,       "sellerIdentities": [         "powerful_merchants"       ],       "traceInfo": "object_id%40632873906275%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "假花五头蝴蝶兰盆景套装仿真花厂家直供人造绿植创意摆件仿真盆栽",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN01QJJsIK238ETQpyuaK_!!2200735397210-0-cib.jpg",       "onePsale": false,       "offerId": 632873906275,       "repurchaseRate": "21%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Children's Underwear Set 2023 Autumn and Winter New Cotton Autumn Clothes for Boys and Girls Autumn Trousers Baby Baby Home Clothes",       "priceInfo": {         "price": "12.50",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 941,       "sellerIdentities": [         "powerful_merchants"       ],       "traceInfo": "object_id%40652741980374%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "儿童内衣套装2024秋冬新款纯棉男女童秋衣秋裤婴儿宝宝家居服代发",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN016oCOTk2Aib9yDnDGX_!!2200712158237-0-cib.jpg",       "onePsale": false,       "offerId": 652741980374,       "repurchaseRate": "25%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Fish tank filter cotton 8D glue-free cotton washable thickened high density water purification bacteria honeycomb Water Cube cotton biochemical Cotton",       "priceInfo": {         "price": "0.88",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 2064,       "sellerIdentities": [],       "traceInfo": "object_id%40648876908463%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "鱼缸过滤棉8D无胶棉洗不烂加厚高密度净水培菌蜂巢水立方棉生化棉",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN01LxRhSB1mpsgxGRjrK_!!2209727665004-0-cib.jpg",       "onePsale": false,       "offerId": 648876908463,       "repurchaseRate": "17%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Whole wood wholesale solid wood Black sandalwood cutting board whole wood cutting board cutting board wooden home cutting board",       "priceInfo": {         "price": "9.00",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 471,       "sellerIdentities": [         "super_factory",         "powerful_merchants"       ],       "traceInfo": "object_id%40616242004615%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "整木批发实木乌檀木菜板整木砧板切菜板木质家用砧板案板",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN01Gme4g61W3lNUjoKUk_!!940802733-0-cib.jpg",       "onePsale": false,       "offerId": 616242004615,       "repurchaseRate": "4%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Magic Fish December birthday stone ring Europe and the United States new December women's titanium steel ring wholesale",       "priceInfo": {         "price": "2.98",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 3814,       "sellerIdentities": [],       "traceInfo": "object_id%40687991610482%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "Magic Fish 12月生辰石素圈指环 欧美新品十二月女士钛钢戒指批发",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN01azXz581ZkIs08ANtx_!!2214820053232-0-cib.jpg",       "onePsale": false,       "offerId": 687991610482,       "repurchaseRate": "47%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     },     {       "subjectTrans": "Universal washing machine base bracket mobile universal wheel storage bracket drum refrigerator pad high pulsator rack tripod",       "priceInfo": {         "price": "19.00",         "class": "com.alibaba.cbu.offer.model.PriceInfo"       },       "monthSold": 770,       "sellerIdentities": [],       "traceInfo": "object_id%40638759712782%5Eobject_type%40offer%5Etrace%40212cd8dd17097866355711562e4915%5EoutMemberId%40nghiapalmyran",       "subject": "通用洗衣机底座托架移动万向轮置物支架滚筒冰箱垫高波轮架子脚架",       "imageUrl": "https://cbu01.alicdn.com/img/ibank/O1CN01zvN7592C84nne33tB_!!2210419958428-0-cib.jpg",       "onePsale": false,       "offerId": 638759712782,       "repurchaseRate": "19%",       "class": "com.alibaba.cbu.offer.model.ProductInfoModel"     }   ],   "code": "200",   "success": true,   "class": "com.alibaba.openapi.shared.common.ResultModel" }</pre>     
     *
     */
    public void setResult(ComAlibabaCbuOfferRecommendModelProductInfoModel[] result) {
        this.result = result;
    }

}
