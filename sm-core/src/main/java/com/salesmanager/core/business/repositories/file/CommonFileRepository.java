package com.salesmanager.core.business.repositories.file;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.salesmanager.core.model.common.file.CommonFile;
import com.salesmanager.core.model.common.file.ReadFile;

public interface CommonFileRepository  extends JpaRepository<CommonFile, Integer> {

	@Query(value = "SELECT\r\n"
			+ "	DATA_ID AS DATAID\r\n"
			+ "	,DEL_YN AS DELYN\r\n"
			+ "	,DOWNLOAD_CNT AS DOWNLOADCNT\r\n"
			+ "	,FILE_ID AS FILEID\r\n"
			+ "	,FILE_NAME AS FILENAME\r\n"
			+ "	,FILE_SIZE AS FILESIZE\r\n"
			+ "	,FILE_TYPE AS FILETYPE\r\n"
			+ "	,FILE_URL AS FILEURL\r\n"
			+ "	,ORD AS ORD\r\n"
			+ "	,PRG_CODE AS PRGCODE\r\n"
			+ "FROM COMMON_FILE\r\n"
			+ "WHERE DEL_YN = 'N'\r\n"
			+ "AND DATA_ID = ?1\r\n"
			+ "AND PRG_CODE =?2\r\n"
			+ "ORDER BY ORD ASC", nativeQuery = true)
	List<ReadFile> getFileList(int id, String prgCode);
}
