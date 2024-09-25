package com.salesmanager.core.business.repositories.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.board.Board;
import com.salesmanager.core.model.board.ReadBoard;
import com.salesmanager.core.model.board.ReadBoard;

public interface PageableBoardRepository  extends JpaRepository<Board, Integer> {

	@Query(value = "SELECT  ID, TITLE, WRITER, DATE_FORMAT(REG_DATE, '%Y-%m-%d %H:%i:%S') AS REGDATE, TYPE,BBS_ID AS BBSID,STATE, NOTICE, OPEN, VIEWCNT, CONTENT FROM BOARD\r\n"
			+ "WHERE BBS_ID = ?3 \r\n"
			+ "	AND  (CASE WHEN ?5 <> '' AND ?6  <> '' THEN DATE_FORMAT(SDATE, '%Y-%m-%d') BETWEEN ?5 AND ?6 ELSE TRUE END )  \r\n"
			+ "	AND (CASE WHEN  ?4 != '' THEN  TYPE = ?4 ELSE TRUE END  ) \r\n"
			+ "	AND (CASE WHEN  ?1 = 'A' THEN  TITLE LIKE %?2% WHEN ?1 = 'B' THEN WRITER LIKE %?2%  ELSE TRUE  END )\r\n"
			+ "	AND  (CASE WHEN ?7 != '' THEN REG_ID = ?7 ELSE TRUE END )\r\n"
			+ "ORDER BY NOTICE DESC, REG_DATE DESC ",
	      countQuery = "SELECT  COUNT(ID)  FROM BOARD  WHERE BBS_ID = ?3  \r\n"
	    		+ "	AND  (CASE WHEN ?5 <> '' AND ?6  <> '' THEN DATE_FORMAT(SDATE, '%Y-%m-%d') BETWEEN ?5 AND ?6 ELSE TRUE END )  \r\n"
	  			+ "	AND (CASE WHEN  ?4 != '' THEN  TYPE = ?4 ELSE TRUE END  ) \r\n"
	  			+ "	AND (CASE WHEN  ?1 = 'A' THEN  TITLE LIKE %?2% WHEN ?1 = 'B' THEN WRITER LIKE %?2%  ELSE TRUE  END )\r\n"
	  			+ "	AND  (CASE WHEN ?7 != '' THEN REG_ID = ?7 ELSE TRUE END )\r\n", nativeQuery=true)
	Page<ReadBoard> getBoardList(String gbn, String keyword, String bbsId, String type, String sdate, String edate,String userId, Pageable pageable);

	
}
