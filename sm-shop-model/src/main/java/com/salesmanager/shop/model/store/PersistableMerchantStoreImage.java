package com.salesmanager.shop.model.store;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.merchant.image.MerchantStoreImage;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersistableMerchantStoreImage extends MerchantStoreImage {
	private static final long serialVersionUID = 1L;
	private String fileName;
//	private MultipartFile file;

}
