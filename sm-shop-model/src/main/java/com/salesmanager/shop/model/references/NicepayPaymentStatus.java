package com.salesmanager.shop.model.references;

public enum NicepayPaymentStatus {
	paid,				// 결제완료
	ready,				// 준비됨
	failed,				// 결제실패
	cancelled,			// 취소됨
	partialCancelled,	// 부분 취소됨
	expired				// 만료됨
}
