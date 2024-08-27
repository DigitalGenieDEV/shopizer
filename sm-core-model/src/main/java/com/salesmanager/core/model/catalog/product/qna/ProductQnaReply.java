package com.salesmanager.core.model.catalog.product.qna;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.salesmanager.core.constants.SchemaConstant;
import com.salesmanager.core.model.common.description.Description;
import com.salesmanager.core.model.reference.language.Language;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "PRODUCT_QNA_REPLY")
@TableGenerator(name = "description_gen", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "product_qna_reply_seq", allocationSize = SchemaConstant.DESCRIPTION_ID_ALLOCATION_SIZE, initialValue = SchemaConstant.DESCRIPTION_ID_START_VALUE)
public class ProductQnaReply extends Description {
	private static final long serialVersionUID = 1L;
	
	@OneToOne
	@JoinColumn(name="PRODUCT_QNA_ID")
	private ProductQna productQna;
	
	public ProductQnaReply() {
	}
	
	public ProductQnaReply(Language language, String name) {
		this.setLanguage(language);
		this.setName(name);
	}
}
