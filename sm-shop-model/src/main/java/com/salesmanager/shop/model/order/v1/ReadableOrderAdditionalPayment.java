package com.salesmanager.shop.model.order.v1;

import com.salesmanager.core.model.order.AdditionalPayment;
import com.salesmanager.core.model.order.ConfirmedAdditionalPayment;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReadableOrderAdditionalPayment extends OrderAdditionalPayment {
    private static final long serialVersionUID = 1L;
    @ApiParam(value = "추가 결제 청구 정보")
    private AdditionalPayment additionalPayment;
    @ApiParam(value = "추가 결제 확정 정보")
    private ConfirmedAdditionalPayment confirmedAdditionalPayment;

}
