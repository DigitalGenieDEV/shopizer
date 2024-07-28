package com.salesmanager.core.model.merchant.certificationfile;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

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
@Table(name = "CERTIFICATION_FILE", indexes = @Index(columnList = "CERTIFICATION_FILE_ID, TEMPLETE_ID"))
public class CertificationFileEntity extends SalesManagerEntity<Long, CertificationFileEntity> implements Auditable {

	private static final long serialVersionUID = 1L;
	public final static String DEFAULT_STORE = "DEFAULT";
	
	@Id
	@Column(name = "CERTIFICATION_FILE_ID", unique = true, nullable = false)
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "CERTIFICATION_FILE_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Embedded
	private AuditSection auditSection = new AuditSection();
	
	@Column(name = "TEMPLETE_ID", length = 50)
	private Long templeteId;
	
	@Column(name = "FILE_URL", length = 256)
	private String fileUrl;
	
	@Column(name = "STORAGE_PATH", length = 256)
	private String storagePath;
	
	@Column(name = "READ_FILE_NAME", length = 128, nullable = false)
	private String readFileName;
	
	@Column(name = "FILE_SIZE", length = 10, nullable = false)
	private Long fileSize;
	
	@Column(name = "FILE_EXT", length = 4, nullable = false)
	private String fileExt;
	
	@Column(name = "BASE_YN", length = 1, nullable = false)
	private String baseYn;
	
	public CertificationFileEntity(Long templeteId, String fileName, String userName, Long fileSize) {
		// TODO Auto-generated constructor stub
		this.templeteId = templeteId;
		this.readFileName = fileName;
		this.fileExt = fileName.substring(fileName.indexOf(".") + 1);
		this.auditSection.setModifiedBy(userName);
		this.auditSection.setDateCreated(new Date());
		this.auditSection.setDateModified(new Date());
		this.fileSize = Long.valueOf(fileSize);
		this.baseYn = "N";
		
	}
}
