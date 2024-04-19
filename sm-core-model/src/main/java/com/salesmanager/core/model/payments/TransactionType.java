package com.salesmanager.core.model.payments;

public enum TransactionType {

	// 初始化
	INIT,

	// 授权签名
	AUTHORIZE,

	// 扣款
	CAPTURE,

	// 授权扣款
	AUTHORIZECAPTURE,

	// 退款
	REFUND,

	// 合并支付记录
	COMBINESTAMP,

	// 交易结束
	OK

}
