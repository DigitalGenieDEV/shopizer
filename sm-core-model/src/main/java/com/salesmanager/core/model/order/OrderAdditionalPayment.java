package com.salesmanager.core.model.order;

import com.salesmanager.core.model.generic.SalesManagerEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table (name="ORDER_ADDITIONAL_PAYMENT")
public class OrderAdditionalPayment extends SalesManagerEntity<String, OrderAdditionalPayment> {
	private static final long serialVersionUID = 1L;

	@Id
	@Column (name ="ORDER_ID" , unique=true , nullable=false )
	private String id;

	@Embedded
	private AdditionalPayment additionalPayment;

	@Embedded
	private ConfirmedAdditionalPayment confirmedAdditionalPayment;

	@Enumerated(EnumType.STRING)
	@Column (name = "STATUS")
	private OrderAdditionalPaymentStatus status = OrderAdditionalPaymentStatus.WAITING;



}