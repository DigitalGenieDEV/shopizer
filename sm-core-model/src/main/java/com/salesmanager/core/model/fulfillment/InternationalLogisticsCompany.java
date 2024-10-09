package com.salesmanager.core.model.fulfillment;

import lombok.Getter;

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
