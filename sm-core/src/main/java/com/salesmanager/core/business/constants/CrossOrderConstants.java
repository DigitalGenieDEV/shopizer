package com.salesmanager.core.business.constants;

public interface CrossOrderConstants {

    String STATUS_WAITBUYERPAY = "waitbuyerpay";
    String STATUS_WAITSELLERSEND = "waitsellersend";
    String STATUS_WAITBUYERRECEIVE = "waitbuyerreceive";
    String STATUS_CONFIRM_GOODS = "confirm_goods";
    String STATUS_SUCCESS = "success";
    String STATUS_CANCEL = "cancel";
    String STATUS_TERMINATED = "terminated";

    /**
     *  WAITACCEPT:未受理;
     *  CANCEL:已撤销;
     *  ACCEPT:已受理;
     *  TRANSPORT:运输中;
     *  NOGET:揽件失败;
     *  SIGN:已签收;
     *  UNSIGN:签收异常
     */
    String LOGISTICS_STATUS_WAITACCEPT = "WAITACCEPT";
    String LOGISTICS_STATUS_CANCEL = "CANCEL";
    String LOGISTICS_STATUS_ACCEPT = "ACCEPT";
    String LOGISTICS_STATUS_TRANSPORT = "TRANSPORT";
    String LOGISTICS_STATUS_NOGET = "NOGET";
    String LOGISTICS_STATUS_SIGN = "SIGN";
    String LOGISTICS_STATUS_UNSIGN = "UNSIGN";
}
