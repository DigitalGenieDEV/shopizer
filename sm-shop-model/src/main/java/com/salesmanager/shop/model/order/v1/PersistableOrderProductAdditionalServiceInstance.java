package com.salesmanager.shop.model.order.v1;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class PersistableOrderProductAdditionalServiceInstance extends OrderProductAdditionalServiceInstance {

    private String content;
    private Boolean sendNotice = Boolean.FALSE;

    private String replyContent;
    private String replyFrom;
    private Integer replyTo;

}
