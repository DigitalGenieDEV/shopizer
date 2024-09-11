package com.salesmanager.core.model.order;

import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.math.BigDecimal;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AdditionalPayment {
	@ApiParam(value = "원산지 표시")
	@Column (name ="ADDITIONAL_ORIGIN_CHARGE")
	private BigDecimal originCharge = BigDecimal.ZERO;

	@ApiParam(value = "원산지 증명서 발급")
	@Column (name ="ADDITIONAL_ORIGIN_CERTIFICATION_CHARGE")
	private BigDecimal originCertificationCharge = BigDecimal.ZERO;

	@ApiParam(value = "항구 운송비")
	@Column (name ="ADDITIONAL_PORT_TRANSLATION_CHARGE")
	private BigDecimal portTranslationCharge = BigDecimal.ZERO;

	@ApiParam(value = "수출 신고비")
	@Column (name ="ADDITIONAL_EXPORT_CHARGE")
	private BigDecimal exportCharge = BigDecimal.ZERO;

	@ApiParam(value = "해상운임")
	@Column (name ="ADDITIONAL_OCEAN_FREIGHT_CHARGE")
	private BigDecimal oceanFreightCharge = BigDecimal.ZERO;

	@ApiParam(value = "해상보험")
	@Column (name ="ADDITIONAL_MARINE_INSURANCE_CHARGE")
	private BigDecimal marineInsuranceCharge = BigDecimal.ZERO;

	@ApiParam(value = "도착항서류비용")
	@Column (name ="ADDITIONAL_ARRIVAL_ACCOUNT_CHARGE")
	private BigDecimal arrivalAccountingCharge = BigDecimal.ZERO;

	@ApiParam(value = "HANDLING FEE")
	@Column (name ="ADDITIONAL_HANDLING_CHARGE")
	private BigDecimal handlingCharge = BigDecimal.ZERO;

	@ApiParam(value = "보세창고운반/사용료")
	@Column (name ="ADDITIONAL_WAREHOUSE_CHARGE")
	private BigDecimal warehouseCharge = BigDecimal.ZERO;

	@ApiParam(value = "관세")
	@Column (name ="ADDITIONAL_TARIFF")
	private BigDecimal tariff = BigDecimal.ZERO;

	@ApiParam(value = "부가세")
	@Column (name ="ADDITIONAL_TAX")
	private BigDecimal tax = BigDecimal.ZERO;

	@ApiParam(value = "통관 수수료")
	@Column (name ="ADDITIONAL_DUTY")
	private BigDecimal duty = BigDecimal.ZERO;

	@ApiParam(value = "국내운송료")
	@Column (name ="ADDITIONAL_SHIPPING_CHARGE")
	private BigDecimal shippingCharge = BigDecimal.ZERO;

	// ********* LCL ********
	@ApiParam(value = "팔렛 작업비")
	@Column (name ="ADDITIONAL_PALETTE_CHARGE")
	private BigDecimal paletteCharge = BigDecimal.ZERO;

	@ApiParam(value = "내륙운송비")
	@Column (name ="ADDITIONAL_WAREHOUSE_TRANSLATION_CHARGE")
	private BigDecimal warehouseTranslationCharge = BigDecimal.ZERO;

	// ********* FCL ********
	@ApiParam(value = "수출항 비용")
	@Column (name ="ADDITIONAL_EXPORT_PORT_CHARGE")
	private BigDecimal exportPortCharge = BigDecimal.ZERO;

	@ApiParam(value = "운임할증료")
	@Column (name ="ADDITIONAL_FREIGHT_SURCHAGE")
	private BigDecimal freightSurcharge = BigDecimal.ZERO;

	@ApiParam(value = "도착항비용")
	@Column (name ="ADDITIONAL_PORT_ARRIVAL_CHARGE")
	private BigDecimal portArrivalCharge = BigDecimal.ZERO;
}