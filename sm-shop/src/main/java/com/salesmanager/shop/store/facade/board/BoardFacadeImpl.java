package com.salesmanager.shop.store.facade.board;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.board.BoardService;
import com.salesmanager.core.model.board.Board;
import com.salesmanager.core.model.board.ReadBoard;
import com.salesmanager.core.model.common.file.ReadFile;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.constants.Constants;
import com.salesmanager.shop.model.board.PersistableBoard;
import com.salesmanager.shop.model.board.ReadableBoard;
import com.salesmanager.shop.model.board.ReadableBoardList;
import com.salesmanager.shop.model.common.FileEntity;
import com.salesmanager.shop.populator.board.PersistableBoardPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.board.facade.BoardFacade;
import com.salesmanager.shop.store.controller.file.facade.FileFacade;
import com.salesmanager.shop.utils.ImageFilePath;

@Service
public class BoardFacadeImpl implements BoardFacade {

	@Inject
	private FileFacade fileFacade;

	@Inject
	private BoardService boardService;
	
	

	@Inject
	private PersistableBoardPopulator persistableBoardPopulator;

	@Inject
	private ObjectMapper objectMapper;
	
	@Inject
	@Qualifier("img")
	private ImageFilePath imageUtils;

	public ReadableBoardList getBoardList(String gbn, String keyword, String bbsId, String type, String sdate,
			String edate, int page, int count, String userId) throws Exception {
		try {
			List<ReadBoard> board = null;
			List<ReadableBoard> targetList = new ArrayList<ReadableBoard>();
			ReadableBoardList returnList = new ReadableBoardList();
			org.springframework.data.domain.Page<ReadBoard> pageable = boardService.getBoardList(gbn, keyword, bbsId,
					type, sdate, edate, page, count, userId);
			board = pageable.getContent();
			returnList.setRecordsTotal(pageable.getTotalElements());
			returnList.setTotalPages(pageable.getTotalPages());
			returnList.setNumber(board.size());
			if (board.size() > 0) {
				for (ReadBoard data : board) {
					objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
					ReadableBoard targetData = objectMapper.convertValue(data, ReadableBoard.class);
					targetList.add(targetData);

				}
			}
			returnList.setData(targetList);
			return returnList;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException(e);
		}
	}


	@Transactional
	public PersistableBoard saveBoard(PersistableBoard board,  Map<String, MultipartFile> files, MerchantStore merchantStore) throws Exception {
		try {

			int boardId = board.getId();
			System.out.println("boardId"+boardId);
			Board target = Optional.ofNullable(boardId)
					.filter(id -> id > 0)
					.map(boardService::getById)
					.orElse(new Board());
			
		
			Board dbBoard = populateBoard(board, target);
			boardService.saveOrUpdate(dbBoard);
			
			int  id = 0;
			if (boardId == 0) {
				id = boardService.getMaxId();
			}else {
				id = boardId;
			}
			
			
			if(files != null) {
				//첨부파일 등록 
				fileFacade.saveFile(id, files,merchantStore, Constants.FILE_PRG_CODE_BOARD);
			}
			
			return board;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Board", e);
		}
		
	}
	
	
	public ReadableBoard getById(int id) throws Exception {
		
		boardService.updateViewCnt(id);
		List<FileEntity> targetList = new ArrayList<FileEntity>();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Board data = boardService.getById(id);
		Date regDate = data.getAuditSection().getRegDate();
		Date modDate = data.getAuditSection().getModDate();
		
		objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
		ReadableBoard targetData = objectMapper.convertValue(data, ReadableBoard.class);
		targetData.setRegId(data.getAuditSection().getRegId());
		targetData.setModId(data.getAuditSection().getModId());
		targetData.setRegIp(data.getAuditSection().getRegIp());
		targetData.setModIp(data.getAuditSection().getModIp());
		targetData.setRegDate(dateFormat.format(regDate));
		targetData.setModDate(dateFormat.format(modDate));
		List<ReadFile> fileList = fileFacade.getFileList(data.getId());
		if(fileList.size() > 0 ) {
			for(ReadFile file :fileList ) {
				objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
				FileEntity targetFile = objectMapper.convertValue(file, FileEntity.class);
				targetList.add(targetFile);
			
			}
		}
		
		targetData.setFileList(targetList);
		return targetData;
	}
	
	public void deleteBoard(int id) throws Exception{
		Board board = new Board();
		board.setId(id);
		boardService.delete(board);
	}
	
	public void updateReplyContent(PersistableBoard board) throws Exception{
		Board dbBoard = new Board();
		dbBoard.setId(board.getId());
		dbBoard.setReplyContent(board.getReplyContent());
		dbBoard.getAuditSection().setModId(board.getUserId());
		dbBoard.getAuditSection().setModIp(board.getUserIp());
		boardService.updateReplyContent(dbBoard );
	}

	private Board populateBoard(PersistableBoard board, Board target) {
		try {
			return persistableBoardPopulator.populate(board, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}

}
