package com.salesmanager.core.business.repositories.board;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.salesmanager.core.model.board.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

	@Query( value ="SELECT MAX(ID) AS ID FROM BOARD", nativeQuery=true)
	int getMaxId();
	
	
	@Modifying
	@Query(value ="UPDATE BOARD SET "
				+ "STATE = 'Y', "
				+ "REPLY_CONTENT = :#{#board.replyContent}, "
				+ "MOD_ID = :#{#board.auditSection.modId} , "
				+ "MOD_IP = :#{#board.auditSection.modIp}, "
				+ "MOD_DATE = NOW() "
				+ "WHERE ID = :#{#board.id} "
				, nativeQuery = true)
	void updateReplyContent(@Param("board") Board board);
	
	
	@Modifying
	@Query(value ="UPDATE BOARD SET "
				+ "VIEWCNT = (VIEWCNT + 1) "
				+ "WHERE ID = ?1 "
				, nativeQuery = true)
	void updateViewCnt(Integer id);
}
