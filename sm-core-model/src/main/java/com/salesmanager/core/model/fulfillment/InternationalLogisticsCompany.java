package com.salesmanager.core.model.fulfillment;

import lombok.Getter;

/**
 * This enum is copy from {@link com.kuaidi100.sdk.contant.CompanyConstant}
 */

@Getter
public enum InternationalLogisticsCompany {
    SF("shunfeng"),
    ZT("zhongtong"),
    YT("yuantong"),
    HT("huitongkuaidi"),
    ST("shentong"),
    YD("yunda"),
    EM("ems"),
    JD("jd"),
    ZJ("zhaijisong"),
    DB("debangkuaidi"),
    SS("shansong"),
    KF("kfw"),
    ;

    private final String code;

    InternationalLogisticsCompany(String code) {
        this.code = code;
    }

}
