package com.salesmanager.core.business.services.board;

import org.springframework.data.domain.Page;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityService;
import com.salesmanager.core.model.board.Board;
import com.salesmanager.core.model.board.ReadBoard;

public interface BoardService extends SalesManagerEntityService<Integer, Board> {

	Page<ReadBoard> getBoardList(String gbn, String keyword, String bbsId, String type, String sdate, String edate, int page, int count, String userId) throws ServiceException;

	void saveOrUpdate(Board board) throws ServiceException;
	
	int getMaxId() throws ServiceException;
	
	void updateReplyContent(Board board) throws  ServiceException;
	
	void updateViewCnt(Integer id) throws  ServiceException;
}
