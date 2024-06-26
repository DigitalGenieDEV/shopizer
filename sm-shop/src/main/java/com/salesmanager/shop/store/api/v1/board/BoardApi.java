package com.salesmanager.shop.store.api.v1.board;

import static org.springframework.http.HttpStatus.OK;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.core.model.reference.language.Language;
import com.salesmanager.shop.model.board.PersistableBoard;
import com.salesmanager.shop.model.board.ReadableBoard;
import com.salesmanager.shop.model.board.ReadableBoardList;
import com.salesmanager.shop.store.api.exception.UnauthorizedException;
import com.salesmanager.shop.store.controller.board.facade.BoardFacade;
import com.salesmanager.shop.store.controller.manager.facade.ManagerFacade;
import com.salesmanager.shop.utils.CommonUtils;
import com.salesmanager.shop.utils.ImageFilePath;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import springfox.documentation.annotations.ApiIgnore;

@RestController
@RequestMapping(value = "/api/v1")
@Api(tags = { "Board management resource (Board Management Api)" })
public class BoardApi {
	@Inject
	private BoardFacade boardFacade;
	
	@Inject
	private ManagerFacade managerFacade;

	
	@ResponseStatus(HttpStatus.OK)
	@GetMapping(value = { "/private/board" }, produces = MediaType.APPLICATION_JSON_VALUE)
	@ApiOperation(httpMethod = "GET", value = "Get list of board", notes = "", response = ReadableBoardList.class)
	public ReadableBoardList list(@RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
			@RequestParam(value = "count", required = false, defaultValue = "10") Integer count,
			@RequestParam(value = "gbn", required = false) String gbn,
			@RequestParam(value = "keyword", required = false) String keyword,
			@RequestParam(value = "bbsId", required = false) String bbsId,
			@RequestParam(value = "type", required = false) String type,
			@RequestParam(value = "sdate", required = false) String sdate,
			@RequestParam(value = "edate", required = false) String edate,
			HttpServletRequest request) throws Exception {

		return boardFacade.getBoardList(gbn, keyword, bbsId, type, sdate, edate, page, count);
	}
	
	
	@ResponseStatus(HttpStatus.OK)
	@PostMapping(value = "/private/board", produces = { APPLICATION_JSON_VALUE })
	@ApiImplicitParams({ @ApiImplicitParam(name = "store", dataType = "String", defaultValue = "DEFAULT"),
		@ApiImplicitParam(name = "lang", dataType = "String", defaultValue = "en") })
	public PersistableBoard create(PersistableBoard board, final MultipartHttpServletRequest multiRequest, @ApiIgnore MerchantStore merchantStore)
			throws Exception {
		System.out.println(board.toString());
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		managerFacade.authorizedMenu(authenticatedManager, multiRequest.getRequestURI().toString());
		board.setUserId(authenticatedManager);
		board.setUserIp(CommonUtils.getRemoteIp(multiRequest));
		
		return boardFacade.saveBoard(board, files, merchantStore);
	}

	
	@GetMapping(value = "/private/board/{id}", produces = { APPLICATION_JSON_VALUE })
	@ApiOperation(httpMethod = "GET", value = "Get Board list for an given Board id", notes = "List current AccessControll and child access")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "List of Board found", response = ReadableBoard.class) })
	public ReadableBoard get(@PathVariable(name = "id") int id) throws Exception {
		ReadableBoard data = boardFacade.getById(id);
		return data;
	}
	
	@PutMapping(value = "/private/board/{id}", produces = { APPLICATION_JSON_VALUE })
	public PersistableBoard update(@PathVariable int id,PersistableBoard board, final MultipartHttpServletRequest multiRequest, @ApiIgnore MerchantStore merchantStore) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
		final Map<String, MultipartFile> files = multiRequest.getFileMap();
		managerFacade.authorizedMenu(authenticatedManager, multiRequest.getRequestURI().toString());
		board.setId(id);
		board.setUserId(authenticatedManager);
		board.setUserIp(CommonUtils.getRemoteIp(multiRequest));
		return boardFacade.saveBoard(board,files,merchantStore);
	}
	
	@DeleteMapping(value = "/private/board/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void delete(@PathVariable int id,HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		boardFacade.deleteBoard(id);
	}
	
	@PatchMapping(value = "/private/board/{id}", produces = { APPLICATION_JSON_VALUE })
	@ResponseStatus(OK)
	public void updateReplyContent(@PathVariable int id, @RequestBody PersistableBoard board, HttpServletRequest request) throws Exception {
		String authenticatedManager = managerFacade.authenticatedManager();
		if (authenticatedManager == null) {
			throw new UnauthorizedException();
		}
	
		managerFacade.authorizedMenu(authenticatedManager, request.getRequestURI().toString());
		board.setId(id);
		board.setUserId(authenticatedManager);
		board.setUserIp(CommonUtils.getRemoteIp(request));
		boardFacade.updateReplyContent(board);
	}

}
