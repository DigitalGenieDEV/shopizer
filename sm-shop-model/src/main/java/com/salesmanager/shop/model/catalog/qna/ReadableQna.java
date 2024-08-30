package com.salesmanager.shop.model.catalog.qna;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReadableQna {
	private Long id;
	private String qnaDate;
	private String qnaTitle;
	private String qnaDescription;
	private boolean secret;
	private String category;
	private String replyStatus;
	private Long customerId;
	private String customerFirstName;
	private String customerLastName;
	private String productType;
	private Long productId;
	private String productTitle;
}
