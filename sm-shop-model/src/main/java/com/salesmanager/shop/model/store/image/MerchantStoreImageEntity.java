package com.salesmanager.shop.model.store.image;

import java.io.Serializable;

import com.salesmanager.core.model.merchant.MerchantStore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class MerchantStoreImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;
	private String merchantImage;
	private String merchantImageUrl;
	private MerchantStore merchantStore;
	private Integer sortOrder = 0;
}
