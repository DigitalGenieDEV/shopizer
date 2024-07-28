package com.salesmanager.shop.store.controller.content.facade;

import java.util.List;
import java.util.Optional;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.model.content.ContentType;
import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.core.model.content.OutputContentFile;

//TODO above deprecation, use shop model instead of core model

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.content.*;
import com.salesmanager.shop.model.content.box.PersistableContentBox;
import com.salesmanager.shop.model.content.box.ReadableContentBox;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.model.content.page.PersistableContentPage;
import com.salesmanager.shop.model.content.page.ReadableContentPage;

/**
 * Images and files management
 * @author carlsamson
 *
 */
public interface ContentFacade {
	
	
	ContentFolder getContentFolder(String folder, MerchantStore store) throws Exception;


	ContentFolder getContentFolder(MerchantStore store, FileContentType fileContentType) throws Exception;


	ContentFolder getContentFolder(MerchantStore store, String path) throws Exception;


	ContentFolder getContentFolder(MerchantStore store, FileContentType fileContentType,
								   ContentListQueryRequest contentListQueryRequest) throws Exception;
	/**
	 * File pth
	 * @param store
	 * @param file
	 * @return
	 */
	String absolutePath(MerchantStore store, FileContentType fileContentType);
	
	/**
	 * Deletes a file from CMS
	 * @param store
	 * @param fileName
	 */
	void delete(MerchantStore store, String fileName, String fileType);
	
	/**
	 * Delete content page
	 * @param store
	 * @param id
	 */
	void delete(MerchantStore store, Long id);


	Integer getContentFilesCount(String merchantStoreCode, FileContentType fileContentType);
	/**
	 * Returns page names and urls configured for a given MerchantStore
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ReadableEntityList<ReadableContentPage> getContentPages(MerchantStore store, Language language, int page, int count);
	
	
	/**
	 * Returns page name by code
	 * @param code
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ReadableContentPage getContentPage(String code, MerchantStore store, Language language);
	
	/**
	 * Returns page by name
	 * @param name
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ReadableContentPage getContentPageByName(String name, MerchantStore store, Language language);

	
	/**
	 * Returns a content box for a given code and merchant store
	 * @param code
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ReadableContentBox getContentBox(String code, MerchantStore store, Language language);
	
	
	/**
	 * @param code
	 * @param type
	 * @param store
	 * @return
	 */
	boolean codeExist(String code, String type, MerchantStore store);
	
	
	/**
	 * Returns content boxes created with code prefix
	 * for example return boxes with code starting with <code>_
	 * @param store
	 * @param language
	 * @return
	 * @throws Exception
	 */
	ReadableEntityList<ReadableContentBox> getContentBoxes(ContentType type, String codePrefix, MerchantStore store, Language language, int start, int count);

	ReadableEntityList<ReadableContentBox> getContentBoxes(ContentType type, MerchantStore store, Language language, int start, int count);

	void addContentFile(ContentFile file, String merchantStoreCode);


	void addContentFile(ContentFile file, String merchantStoreCode, FileContentType fileContentType);

	/**
	 * Add multiple files
	 * @param file
	 * @param merchantStoreCode
	 */
	void addContentFiles(List<ContentFile> file, String merchantStoreCode);
	
	String addLibraryFile(ContentFile file, String merchantStoreCode, FileContentType fileContentType);
	
	/**
	 * Creates content page
	 * @param page
	 * @param merchantStore
	 * @param language
	 */
	Long saveContentPage(PersistableContentPage page, MerchantStore merchantStore, Language language);
	
	void updateContentPage(Long id, PersistableContentPage page, MerchantStore merchantStore, Language language);
	
	void deleteContent(Long id, MerchantStore merchantStore);
	
	/**
	 * Creates content box
	 * @param box
	 * @param merchantStore
	 * @param language
	 */
	Long saveContentBox(PersistableContentBox box, MerchantStore merchantStore, Language language);
	
	void updateContentBox(Long id, PersistableContentBox box, MerchantStore merchantStore, Language language);

	
	@Deprecated
	ReadableContentFull getContent(String code, MerchantStore store, Language language);
	
	/**
	 * Get all content types
	 * @param type
	 * @param store
	 * @param language
	 * @return
	 */
	List<ReadableContentEntity> getContents(Optional<String> type, MerchantStore store, Language language);

	/**
	 * Rename file
	 * @param store
	 * @param fileType
	 * @param originalName
	 * @param newName
	 */
	void renameFile(MerchantStore store, FileContentType fileType, String originalName, String newName);
	
	/**
	 * Download file
	 * @param store
	 * @param fileType
	 * @param fileName
	 * @return
	 */
	OutputContentFile download(MerchantStore store, FileContentType fileType, String fileName);

}
