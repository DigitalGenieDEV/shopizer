package com.salesmanager.core.model.merchant.library;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PersistableStoreLibrary extends StoreLibrary {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String store;
	private String fileContentType;
}
