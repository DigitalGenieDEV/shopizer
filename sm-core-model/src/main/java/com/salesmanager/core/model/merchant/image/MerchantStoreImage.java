package com.salesmanager.core.model.merchant.image;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.TableGenerator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.salesmanager.core.model.generic.SalesManagerEntity;
import com.salesmanager.core.model.merchant.MerchantStore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "MERCHANT_IMAGE")
public class MerchantStoreImage extends SalesManagerEntity<Long, MerchantStoreImage> {
	
	@Id
	@Column(name = "MERCHANT_IMAGE_ID")
	@TableGenerator(name = "TABLE_GEN", table = "SM_SEQUENCER", pkColumnName = "SEQ_NAME", valueColumnName = "SEQ_COUNT", pkColumnValue = "MERCHANT_IMG_SEQ_NEXT_VAL")
	@GeneratedValue(strategy = GenerationType.TABLE, generator = "TABLE_GEN")
	private Long id;
	
	@Column(name = "MERCHANT_IMAGE")
	private String merchantImage;
	
	@Column(name = "MERCAHNT_IMAGE_URL")
	private String merchantImageUrl;
	
	@JsonIgnore
	@ManyToOne(targetEntity = MerchantStore.class)
	@JoinColumn(name = "MERCHANT_ID", nullable = false)
	private MerchantStore merchantStore;
	
	@Column(name = "SORT_ORDER")
	private Integer sortOrder = 0;

}
