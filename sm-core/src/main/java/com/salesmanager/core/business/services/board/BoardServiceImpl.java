package com.salesmanager.core.business.services.board;

import javax.inject.Inject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.repositories.board.BoardRepository;
import com.salesmanager.core.business.repositories.board.PageableBoardRepository;
import com.salesmanager.core.business.services.common.generic.SalesManagerEntityServiceImpl;
import com.salesmanager.core.model.board.Board;
import com.salesmanager.core.model.board.ReadBoard;

@Service("boardService")
public class BoardServiceImpl extends SalesManagerEntityServiceImpl<Integer, Board> implements BoardService {

	@Inject
	private BoardRepository boardRepository;

	@Inject
	private PageableBoardRepository pageableMBoardRepository;

	@Inject
	public BoardServiceImpl(BoardRepository boardRepository) {
		super(boardRepository);
		this.boardRepository = boardRepository;
	}

	
	public Page<ReadBoard> getBoardList(String gbn, String keyword, String bbsId, String type, String sdate, String edate, int page, int count)
			throws ServiceException {
		Pageable pageRequest = PageRequest.of(page, count);
		return pageableMBoardRepository.getBoardList(gbn, keyword, bbsId, type, sdate, edate, pageRequest);
	}
	
	@Override
	public void saveOrUpdate(Board board) throws ServiceException {
		// save or update (persist and attach entities
		if (board.getId() != null && board.getId() > 0) {
			super.update(board);
		} else {
			this.create(board);
		}

	}
	
	public int getMaxId() throws ServiceException{
		return  boardRepository.getMaxId();
	}
	
	public void updateReplyContent(Board board) throws  ServiceException{
		 boardRepository.updateReplyContent(board);
	}
}
