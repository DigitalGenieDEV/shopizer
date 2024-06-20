package com.salesmanager.core.model.common.file;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotEmpty;

import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.utils.CloneUtils;

@Entity
@Table(name = "COMMON_FILE", uniqueConstraints = { @UniqueConstraint(columnNames = { "PRG_CODE", "DATA_ID" }) })
public class CommonFile  extends SalesManagerEntity<Integer, CommonFile> implements Serializable {
	private static final long serialVersionUID = 1321251632883237664L;

	@Id
	@Column(name = "FILE_ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "FILE_ID_NEXT_VALUE")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Integer id;

	@NotEmpty
	@Column(name = "PRG_CODE", length = 20, nullable = false, updatable = false, columnDefinition = "varchar(20) not null comment '프로그램 코드'")
	private String prgCode;


	@Column(name = "DATA_ID", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '게시글ID'")
	private int dataId = 0;

	@NotEmpty
	@Column(name = "FILE_URL", length = 255, nullable = false, updatable = false, columnDefinition = "varchar(255) not null comment 'S3 FILE_URL'")
	private String fileUrl;

	@NotEmpty
	@Column(name = "FILE_NAME", length = 255, nullable = false, updatable = false, columnDefinition = "varchar(255) not null comment '원본파일명'")
	private String fileName;

	
	@Column(name = "FILE_SIZE", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '파일크기'")
	private Long fileSize;

	@NotEmpty
	@Column(name = "FILE_TYPE", length = 10, nullable = false, updatable = false, columnDefinition = "varchar(255) not null comment '파일타입'")
	private String fileType;

	@Column(name = "DOWNLOAD_CNT", length = 11, nullable = true, updatable = true, columnDefinition = "INT(11) not null comment '다운로드 수'")
	private int downCnt = 0;

	@NotEmpty
	@Column(name = "DEL_YN", length = 1, nullable = false, updatable = false, columnDefinition = "varchar(1) not null comment '삭제여부'")
	private String delYn = "N";

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "REG_DATE", length = 0, nullable = false)
	private Date regDate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DEL_DATE", length = 0)
	private Date delDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPrgCode() {
		return prgCode;
	}

	public void setPrgCode(String prgCode) {
		this.prgCode = prgCode;
	}

	public int getDataId() {
		return dataId;
	}

	public void setDataId(int dataId) {
		this.dataId = dataId;
	}

	public String getFileUrl() {
		return fileUrl;
	}

	public void setFileUrl(String fileUrl) {
		this.fileUrl = fileUrl;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Long getFileSize() {
		return fileSize;
	}

	public void setFileSize(Long fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public int getDownCnt() {
		return downCnt;
	}

	public void setDownCnt(int downCnt) {
		this.downCnt = downCnt;
	}

	public String getDelYn() {
		return delYn;
	}

	public void setDelYn(String delYn) {
		this.delYn = delYn;
	}

	public Date getRegDate() {
		return CloneUtils.clone(regDate);
	}

	public void setRegDate(Date regDate) {
		this.regDate = CloneUtils.clone(regDate);
	}

	public Date getDelDate() {
		return delDate;
	}

	public void setDelDate(Date delDate) {
		this.delDate = delDate;
	}

}
