package com.salesmanager.shop.model.references;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NicepayPayment {
	@Id
	private Long id;
	private String resultCode;
	private String resultMsg;
	private String tid;
	private String cancelledTid;
	private String orderId;
	private ZonedDateTime ediDate;
	private String signature;
	private NicepayPaymentStatus status;
	private String paidAt;
	private String failedAt;
	private String cancelledAt;
	private NicepayPayMethod payMethod;
	private BigInteger amount;
	private BigInteger balanceAmt;
	private String goodsName;
	private String mallReserved;
	private Boolean useEscrow;					// 에스크로 거래 여부 false : 일반 거래, true 에스크로 거래
	private String currency;					// KRW, USD, CNY
	private String channel;						// ['pc', 'mobile', null]
	private String approveNo;
	private String buyerName;
	private String buyerTel;
	private String buyerEmail;
	private String receiptUrl;
	private String mallUserId;
	private Boolean issuedCashReceipt;			// 현금영수증 발급여부 true : 발행, false : 미발행
	private BigInteger couponAmt;
}
