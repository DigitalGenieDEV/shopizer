package com.salesmanager.shop.store.facade.board;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.salesmanager.core.business.exception.ConversionException;
import com.salesmanager.core.business.exception.ServiceException;
import com.salesmanager.core.business.services.board.BoardService;
import com.salesmanager.core.model.board.Board;
import com.salesmanager.core.model.board.ReadBoard;
import com.salesmanager.core.model.merchant.MerchantStore;
import com.salesmanager.shop.model.board.PersistableBoard;
import com.salesmanager.shop.model.board.ReadableBoard;
import com.salesmanager.shop.model.board.ReadableBoardList;
import com.salesmanager.shop.model.content.ContentFile;
import com.salesmanager.shop.populator.board.PersistableBoardPopulator;
import com.salesmanager.shop.store.api.exception.ServiceRuntimeException;
import com.salesmanager.shop.store.controller.board.facade.BoardFacade;
import com.salesmanager.shop.store.controller.content.facade.ContentFacade;

@Service
public class BoardFacadeImpl implements BoardFacade {

	@Inject
	private ContentFacade contentFacade;

	@Inject
	private BoardService boardService;

	@Inject
	private PersistableBoardPopulator persistableBoardPopulator;

	@Inject
	private ObjectMapper objectMapper;

	public ReadableBoardList getBoardList(String gbn, String keyword, String bbsId, String type, String sdate,
			String edate, int page, int count) throws Exception {
		try {
			List<ReadBoard> board = null;
			List<ReadableBoard> targetList = new ArrayList<ReadableBoard>();
			ReadableBoardList returnList = new ReadableBoardList();
			org.springframework.data.domain.Page<ReadBoard> pageable = boardService.getBoardList(gbn, keyword, bbsId,
					type, sdate, edate, page, count);
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


	public PersistableBoard saveBoard(PersistableBoard board) throws Exception {
		try {

			int boardId = board.getId();
			Board target = Optional.ofNullable(boardId)
					.filter(id -> id > 0)
					.map(boardService::getById)
					.orElse(new Board());
			
		
			Board dbBoard = populateBoard(board, target);
			boardService.saveOrUpdate(dbBoard);
			
			return board;
		} catch (ServiceException e) {
			throw new ServiceRuntimeException("Error while updating Board", e);
		}
		
	}
	
	@SuppressWarnings({ "unused", "deprecation" })
	public void saveBoardFile(Map<String, MultipartFile> files,MerchantStore merchantStore) throws Exception{
		Iterator<Entry<String, MultipartFile>> itr = files.entrySet().iterator();
		MultipartFile file;

			while (itr.hasNext()) {
			    Entry<String, MultipartFile> entry = itr.next();
	
			    file = entry.getValue();
			    String orginFileName = file.getOriginalFilename();
			    System.out.println("orginFileName"+orginFileName);
			    //--------------------------------------
			    // 원 파일명이 null인 경우 처리
			    //--------------------------------------
			    if (orginFileName == null) {
			    	orginFileName = "";
			    }
			    ////------------------------------------
	
			    //--------------------------------------
			    // 원 파일명이 없는 경우 처리
			    // (첨부가 되지 않은 input file type)
			    //--------------------------------------
			    if ("".equals(orginFileName)) {
				continue;
			    }
			    
			    ContentFile f = new ContentFile();
			    int index = orginFileName.lastIndexOf(".");
			    //String fileName = orginFileName.substring(0, index);
			    String fileNameWithoutExtension = orginFileName.substring(index + 1);
			    
				f.setContentType(file.getContentType());
				f.setName(file.getOriginalFilename());
				f.setFile(file.getBytes());
				contentFacade.addContentFile(f, merchantStore.getCode());
			}
		
	}
	
	public ReadableBoard getById(int id) throws Exception {
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

	private Board populateBoard(PersistableBoard dept, Board target) {
		try {
			return persistableBoardPopulator.populate(dept, target);
		} catch (ConversionException e) {
			throw new ServiceRuntimeException(e);
		}
	}

}
