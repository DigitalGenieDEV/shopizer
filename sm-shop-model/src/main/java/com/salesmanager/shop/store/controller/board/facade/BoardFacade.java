package com.salesmanager.shop.store.controller.board.facade;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.board.PersistableBoard;
import com.salesmanager.shop.model.board.ReadableBoard;
import com.salesmanager.shop.model.board.ReadableBoardList;

public interface BoardFacade {
	
	/**
	 *
	 * @param gbn
	 * @param keyword
	 * @param bbsId
	 * @param type
	 * @param sdate
	 * @param edate
	 * @param page
	 * @param count
	 * @return ReadableBoardList
	 */
	ReadableBoardList getBoardList(String gbn, String keyword, String bbsId, String type, String sdate, String edate, int page, int count) throws Exception;

	PersistableBoard saveBoard(PersistableBoard board, Map<String, MultipartFile> files,  MerchantStore merchantStore) throws Exception;
	
	void saveBoardFile(Map<String, MultipartFile> files,MerchantStore merchantStore) throws Exception;

	ReadableBoard getById(int id) throws Exception;
	
	void deleteBoard(int id) throws Exception;
	
	void updateReplyContent(PersistableBoard board) throws Exception;
}
