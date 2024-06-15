package com.salesmanager.core.business.repositories.popup;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.popup.Popup;
import com.salesmanager.core.model.popup.ReadUserPopup;

public interface PopupRepository extends JpaRepository<Popup, Integer> {
	@Query(value = "SELECT ID, NAME, TYPE, URL, CASE WHEN TARGET = 'N' THEN '_self' ELSE  '_blank' END LINKTARGET,  IMAGE,  ALT, POP_WIDTH , POP_HEIGHT , POP_LEFT, POP_TOP  FROM POPUP \r\n"
			+ "WHERE VISIBLE = 0 \r\n"
			+ "	AND (SITE = 0 OR SITE = ?1  ) \r\n"
			+ "	AND NOW() BETWEEN STR_TO_DATE(SDATE, '%Y-%m-%d %H:%i:%s') AND STR_TO_DATE(EDATE, '%Y-%m-%d %H:%i:%s')\r\n"
			+ "ORDER BY ORD ASC", nativeQuery = true)
	List<ReadUserPopup> getPopupUserList(String site);
}
