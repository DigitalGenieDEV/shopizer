package com.salesmanager.shop.model.order.v1;

import com.salesmanager.core.model.order.AdditionalPayment;
import com.salesmanager.core.model.order.ConfirmedAdditionalPayment;
import com.salesmanager.shop.model.entity.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAdditionalPayment extends Entity {
    private static final long serialVersionUID = 1L;
    private String orderId;
    private AdditionalPayment additionalPayment;
    private ConfirmedAdditionalPayment confirmedAdditionalPayment;
}
