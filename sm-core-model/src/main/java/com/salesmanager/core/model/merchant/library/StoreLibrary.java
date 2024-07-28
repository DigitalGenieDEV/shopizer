package com.salesmanager.core.model.merchant.library;

import java.util.Date;

import javax.annotation.Nullable;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import org.hibernate.annotations.ColumnDefault;

import com.salesmanager.core.model.common.audit.AuditSection;
import com.salesmanager.core.model.common.audit.Auditable;
import com.salesmanager.core.model.generic.SalesManagerEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor 
@Entity
@Table(name = "LIBRARY_FILE", indexes = @Index(columnList = "LIBRARY_ID, MERCHANT_ID"))
public class StoreLibrary extends SalesManagerEntity<Long, StoreLibrary> implements Auditable {

	private static final long serialVersionUID = 1L;
	public final static String DEFAULT_STORE = "DEFAULT";
  
	@Id
	@Column(name = "LIBRARY_ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "LIBRARY_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;

	@Embedded
	private AuditSection auditSection = new AuditSection();

	@Column(name = "MERCHANT_ID", length = 50)
	private Integer storeId; 
	
	@Column(name = "FILE_URL", length = 256)
	private String fileUrl;
	
	@Column(name = "STORAGE_PATH", length = 256)
	private String storagePath;
	
	@Column(name = "READ_FILE_NAME", length = 128, nullable = false)
	private String readFileName;
	
	@Column(name = "FILE_TYPE", length = 1, nullable = false)
	private String fileType;
	
	@Column(name = "FILE_SIZE", length = 10, nullable = false)
	private Long fileSize;
	
	@Column(name = "FILE_EXT", length = 4, nullable = false)
	private String fileExt;
	
	@Column(name = "DELETE_YN", length = 1, nullable = true)
	@ColumnDefault("N")
	private String deleteYn;
	
	public StoreLibrary(Integer storeId, String fileName, String fileType, String userName, Long fileSize) {
		// TODO Auto-generated constructor stub
		this.storeId = storeId;
		this.readFileName = fileName;
		this.fileExt = fileName.substring(fileName.indexOf(".") + 1);
		this.fileType = fileType;
		this.auditSection.setModifiedBy(userName);
		this.auditSection.setDateCreated(new Date());
		this.auditSection.setDateModified(new Date());
		this.fileSize = Long.valueOf(fileSize);
		this.deleteYn = "N";
	}

}
