package com.salesmanager.shop.store.api.v1.content;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.salesmanager.core.model.content.FileContentType;
import com.salesmanager.shop.model.content.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.content.ContentType;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.content.box.PersistableContentBox;
import com.salesmanager.shop.model.content.box.ReadableContentBox;
import com.salesmanager.shop.model.content.page.PersistableContentPage;
import com.salesmanager.shop.model.content.page.ReadableContentPage;
import com.salesmanager.shop.model.entity.Entity;
import com.salesmanager.shop.model.entity.EntityExists;
import com.salesmanager.shop.model.entity.ReadableEntityList;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;
import com.salesmanager.shop.utils.ImageFilePath;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.SwaggerDefinition;
import io.swagger.annotations.Tag;
import springfox.documentation.annotations.ApiIgnore;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Content management resource (Content Management Api)" })
@SwaggerDefinition(
		tags = { @Tag(
				name = "Content management resource", description = "Add pages, content boxes, manage images and files"
		) }
)
public class ContentApi {

	private static final Logger LOGGER = LoggerFactory.getLogger(ContentApi.class);

	private static final String DEFAULT_PATH = "/";

	private final static String BOX = "BOX";
	private final static String PAGE = "PAGE";

	@Inject
	private ContentFacade contentFacade;

	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

//	/**
//	 * List content pages
//	 * @param merchantStore
//	 * @param language
//	 * @param page
//	 * @param count
//	 * @return
//	 */
//	@GetMapping(value = {"/private/content/pages", "/content/pages"}, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiOperation(httpMethod = "GET", value = "Get page names created for a given MerchantStore", notes = "", produces = "application/json", response = List.class)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public ReadableEntityList<ReadableContentPage> pages(
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language,
//			int page,
//			int count) {
//		return contentFacade
//				.getContentPages(merchantStore, language, page, count);
//	}

//	/**
//	 * List all boxes
//	 *
//	 * @param merchantStore
//	 * @param language
//	 * @return
//	 */
//	@GetMapping(value = {"/content/boxes","/private/content/boxes"}, produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiOperation(httpMethod = "GET", value = "Get boxes for a given MerchantStore", notes = "", produces = "application/json", response = List.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public ReadableEntityList<ReadableContentBox> boxes(
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language,
//			int page,
//			int count
//			) {
//		return contentFacade.getContentBoxes(ContentType.BOX, merchantStore, language, page, count);
//	}

//	/**
//	 * List specific content box
//	 * @param code
//	 * @param merchantStore
//	 * @param language
//	 * @return
//	 */
//	@GetMapping(value = "/content/pages/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiOperation(httpMethod = "GET", value = "Get page content by code for a given MerchantStore", notes = "", produces = "application/json", response = ReadableContentPage.class)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public ReadableContentPage page(@PathVariable("code") String code, @ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		return contentFacade.getContentPage(code, merchantStore, language);
//
//	}

//	/**
//	 * Get content page by name
//	 * @param name
//	 * @param merchantStore
//	 * @param language
//	 * @return
//	 */
//	@GetMapping(value = "/content/pages/name/{name}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiOperation(httpMethod = "GET", value = "Get page content by code for a given MerchantStore", notes = "", produces = "application/json", response = ReadableContentPage.class)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public ReadableContentPage pageByName(@PathVariable("name") String name, @ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		return contentFacade.getContentPageByName(name, merchantStore, language);
//
//	}

	/**
	 * // * Create content box // * // * @param page // * @param merchantStore //
	 * * @param language // * @param pageCode //
	 */
//	@PostMapping(value = "/private/content/box")
//	@ResponseStatus(HttpStatus.CREATED)
//	@ApiOperation(httpMethod = "POST", value = "Create content box", notes = "", response = Entity.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public Entity createBox(
//			@RequestBody @Valid PersistableContentBox box,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		Long id = contentFacade.saveContentBox(box, merchantStore, language);
//		Entity entity = new Entity();
//		entity.setId(id);
//		return entity;
//	}
//
//	@GetMapping(value = "/private/content/box/{code}/exists")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(httpMethod = "GET", value = "Check unique content box", notes = "", response = EntityExists.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public EntityExists boxExists(
//			@PathVariable String code,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		boolean exists = contentFacade.codeExist(code, BOX, merchantStore);
//		EntityExists entity = new EntityExists(exists);
//		return entity;
//	}

//	@GetMapping(value = "/private/content/page/{code}/exists")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(httpMethod = "GET", value = "Check unique content page", notes = "", response = EntityExists.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public EntityExists pageExists(
//			@PathVariable String code,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		boolean exists = contentFacade.codeExist(code, PAGE, merchantStore);
//		EntityExists entity = new EntityExists(exists);
//		return entity;
//	}

//	/**
//	 * Create content page
//	 * @param page
//	 * @param merchantStore
//	 * @param language
//	 */
//	@PostMapping(value = "/private/content/page")
//	@ResponseStatus(HttpStatus.CREATED)
//	@ApiOperation(httpMethod = "POST", value = "Create content page", notes = "", response = Entity.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public Entity createPage(
//			@RequestBody @Valid PersistableContentPage page,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		Long id = contentFacade.saveContentPage(page, merchantStore, language);
//		Entity entity = new Entity();
//		entity.setId(id);
//		return entity;
//	}

	/**
	 * // * Delete content page // * @param id // * @param merchantStore // * @param
	 * language //
	 */
//	@DeleteMapping(value = "/private/content/page/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(httpMethod = "DELETE", value = "Delete content page", notes = "", response = Void.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public void deletePage(
//			@PathVariable Long id,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		contentFacade.delete(merchantStore, id);
//
//	}
//
//	/**
//	 * Delete content box
//	 * @param id
//	 * @param merchantStore
//	 * @param language
//	 */
//	@DeleteMapping(value = "/private/content/box/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(httpMethod = "DELETE", value = "Delete content box", notes = "", response = Void.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public void deleteBox(
//			@PathVariable Long id,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		contentFacade.delete(merchantStore, id);
//
//	}

//	@PutMapping(value = "/private/content/page/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(httpMethod = "PUT", value = "Update content page", notes = "", response = Void.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public void updatePage(
//			@RequestBody @Valid PersistableContentPage page,
//			@PathVariable Long id,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		contentFacade.updateContentPage(id, page, merchantStore, language);
//	}

//	@PutMapping(value = "/private/content/box/{id}")
//	@ResponseStatus(HttpStatus.OK)
//	@ApiOperation(httpMethod = "PUT", value = "Update content box", notes = "", response = Void.class)
//	@ApiImplicitParams({
//		@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public void updateBox(
//			@RequestBody @Valid PersistableContentBox box,
//			@PathVariable Long id,
//			@ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		contentFacade.updateContentBox(id, box, merchantStore, language);
//	}

//	@GetMapping(value = "/private/content/boxes/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiOperation(httpMethod = "GET", value = "Manage box content by code for a code and a given MerchantStore", notes = "", produces = "application/json", response = List.class)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public ReadableContentBox manageBoxByCode(@PathVariable("code") String code, @ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//		return contentFacade.getContentBox(code, merchantStore, language);
//	}
//
//	@GetMapping(value = "/content/boxes/{code}", produces = MediaType.APPLICATION_JSON_VALUE)
//	@ApiOperation(httpMethod = "GET", value = "Get box content by code for a code and a given MerchantStore", notes = "", produces = "application/json", response = List.class)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public ReadableContentBox getBoxByCode(@PathVariable("code") String code, @ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//		return contentFacade.getContentBox(code, merchantStore, language);
//	}

	/**
	 * 
	 * @param parent
	 * @param folder
	 * @param merchantStore
	 * @param language
	 */
	@DeleteMapping(value = "/content/folder", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") }
	)
	public void addFolder(@RequestParam
	String parent,
			@RequestParam
			String folder,
			@ApiIgnore
			MerchantStore merchantStore,
			@ApiIgnore
			Language language
	) {

	}
	@GetMapping(value = "/private/content/images/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get store content images", notes = "", response = ContentFolder.class)
	@ApiImplicitParams(
			{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT") }
	)
	public ContentFolder getAdminImagesByPath(@ApiIgnore MerchantStore merchantStore, @PathVariable String path) {
		return getImagesByPathInternal(merchantStore, path);
	}

	@GetMapping(value = "/auth/content/images/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get store content images", notes = "", response = ContentFolder.class)
	@ApiImplicitParams(
			{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT") }
	)
	public ContentFolder getSellerImagesByPath(@ApiIgnore MerchantStore merchantStore, @PathVariable String path) {
		return getImagesByPathInternal(merchantStore, path);
	}

	@GetMapping(value = "/content/images/{path}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get store content images", notes = "", response = ContentFolder.class)
	@ApiImplicitParams(
			{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT") }
	)
	public ContentFolder getImagesByPath(@ApiIgnore MerchantStore merchantStore, @PathVariable String path) {
		return getImagesByPathInternal(merchantStore, path);
	}




	private ContentFolder getImagesByPathInternal(MerchantStore merchantStore, String path) {
		ContentFolder folder = new ContentFolder();
		try {
			folder = contentFacade.getContentFolder(merchantStore, path);
		} catch (Exception e) {
			LOGGER.error("Error fetching images", e);
		}
		return folder;
	}


	/**
	 * @return
	 * @throws Exception
	 */
	@GetMapping(value = "/content/images", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get store content images", notes = "", response = ContentFolder.class)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT") }
	)
	public ContentFolder images(@ApiIgnore MerchantStore merchantStore,
								@RequestParam(value = "fileContentType", required = true) String fileContentType) {
		ContentFolder folder = new ContentFolder();
		try {
			folder = contentFacade.getContentFolder(merchantStore, FileContentType.valueOf(fileContentType));
			return folder;
		}catch (Exception e){
			LOGGER.error("images is error");
		}
		return folder;
	}


	@GetMapping(value = "/private/content/files", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get store content images", notes = "", response = ContentFolder.class)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") }
	)
	public ContentFolder getFileUrls(@ApiIgnore
	MerchantStore merchantStore,
			@ApiIgnore
			Language language,
			HttpServletRequest request,
			@RequestParam(value = "fileContentType", required = true)
			String fileContentType,
			HttpServletResponse response
	) throws Exception {
		ContentFolder folder = contentFacade.getContentFolder(
				merchantStore,
				FileContentType.valueOf(fileContentType)
		);
		return folder;
	}

	@GetMapping(value = "/auth/content/files", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get store content images", notes = "", response = ContentFolder.class)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") }
	)
	public ContentFolder getFileUrlsForSeller(@ApiIgnore
	MerchantStore merchantStore,
			@ApiIgnore
			Language language,
			HttpServletRequest request,
			@RequestParam(value = "fileContentType", required = true)
			String fileContentType,
			@RequestParam(value = "contentListQueryRequest", required = true)
			ContentListQueryRequest contentListQueryRequest,
			HttpServletResponse response
	) throws Exception {
		ContentFolder folder = contentFacade.getContentFolder(
				merchantStore,
				FileContentType.valueOf(fileContentType),
				contentListQueryRequest
		);
		return folder;
	}

	/**
	 * Need type, name and entity
	 *
	 * @param file
	 */
	@PostMapping(value = "/private/content/type/file")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") }
	)
	public String upload(@RequestParam("file")
	MultipartFile file,
			@ApiIgnore
			MerchantStore merchantStore,
			@ApiIgnore
			Language language,
			@RequestParam(value = "fileContentType", required = true)
			String fileContentType
	) {

		ContentFile f = new ContentFile();
		f.setContentType(file.getContentType());
		f.setName(file.getOriginalFilename());
		try {
			f.setFile(file.getBytes());
		} catch (IOException e) {
			throw new ServiceRuntimeException("Error while getting file bytes");
		}

		contentFacade.addContentFile(
				f,
				merchantStore.getCode(),
				FileContentType.valueOf(fileContentType)
		);

		return imageUtils.buildStaticImageUtils(
				merchantStore,
				f.getName()
		);
	}

	/**
	 * Need type, name and entity
	 *
	 * @param file
	 */
	@PostMapping(value = "/auth/content/type/file")
	@ResponseStatus(HttpStatus.CREATED)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") }
	)
	public String uploadForSeller(@RequestParam("file")
	MultipartFile file,
			@ApiIgnore
			MerchantStore merchantStore,
			@ApiIgnore
			Language language,
			@RequestParam(value = "fileContentType", required = true)
			String fileContentType
	) {

		ContentFile f = new ContentFile();
		f.setContentType(file.getContentType());
		f.setName(file.getOriginalFilename());
		try {
			f.setFile(file.getBytes());
		} catch (IOException e) {
			throw new ServiceRuntimeException("Error while getting file bytes");
		}

		contentFacade.addContentFile(
				f,
				merchantStore.getCode(),
				FileContentType.valueOf(fileContentType)
		);

		return imageUtils.buildStaticImageUtils(
				merchantStore,
				f.getName()
		);
	}

	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = "/auth/content/count", produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get store content images", notes = "", response = ContentFolder.class)
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT") })
	public Integer getContentFilesCount(@ApiIgnore
	MerchantStore merchantStore,
			@RequestParam(value = "fileContentType", required = true)
			String fileContentType
	) {
		return contentFacade.getContentFilesCount(
				merchantStore.getCode(),
				FileContentType.valueOf(fileContentType)
		);
	}

	/**
	 * Need type, name and entity
	 *
	 * @param file
	 */
//	@PostMapping(value = "/private/file")
//	@ResponseStatus(HttpStatus.CREATED)
//	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public void upload(@RequestParam("file") MultipartFile file, @ApiIgnore MerchantStore merchantStore,
//			@ApiIgnore Language language) {
//
//		ContentFile f = new ContentFile();
//		f.setContentType(file.getContentType());
//		f.setName(file.getOriginalFilename());
//		try {
//			f.setFile(file.getBytes());
//		} catch (IOException e) {
//			throw new ServiceRuntimeException("Error while getting file bytes");
//		}
//
//		contentFacade.addContentFile(f, merchantStore.getCode());
//
//	}

//	@PostMapping(value = "/private/files", consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
//	@ResponseStatus(HttpStatus.CREATED)
//	@ApiImplicitParams({
//			// @ApiImplicitParam(name = "file[]", value = "File stream object",
//			// required = true,dataType = "MultipartFile",allowMultiple = true),
//			@ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
//			@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") })
//	public void uploadMultipleFiles(@RequestParam(value = "file[]", required = true) MultipartFile[] files,
//			@ApiIgnore MerchantStore merchantStore, @ApiIgnore Language language) {
//
//		for (MultipartFile f : files) {
//			ContentFile cf = new ContentFile();
//			cf.setContentType(f.getContentType());
//			cf.setName(f.getName());
//			try {
//				cf.setFile(f.getBytes());
//				contentFacade.addContentFile(cf, merchantStore.getCode());
//			} catch (IOException e) {
//				throw new ServiceRuntimeException("Error while getting file bytes");
//			}
//		}
//
//	}

	/**
	 * Deletes a file from CMS
	 *
	 * @param name
	 */
	@DeleteMapping(value = "/private/content/")
	@ApiOperation(
			httpMethod = "DELETE", value = "Deletes a file from CMS", notes = "Delete a file from server", response = Void.class
	)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") }
	)
	public void deleteFile(@Valid
	ContentName name,
			@ApiIgnore
			MerchantStore merchantStore,
			@ApiIgnore
			Language language
	) {
		contentFacade.delete(
				merchantStore,
				name.getName(),
				name.getContentType()
		);
	}

	/**
	 * Deletes a file from CMS
	 *
	 * @param name
	 */
	@DeleteMapping(value = "/auth/content/")
	@ApiOperation(
			httpMethod = "DELETE", value = "Deletes a file from CMS", notes = "Delete a file from server", response = Void.class
	)
	@ApiImplicitParams(
		{ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
				@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "ko") }
	)
	public void deleteFileForSeller(@Valid
	ContentName name,
			@ApiIgnore
			MerchantStore merchantStore,
			@ApiIgnore
			Language language
	) {
		contentFacade.delete(
				merchantStore,
				name.getName(),
				name.getContentType()
		);
	}

}
