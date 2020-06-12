package com.tfs.order.mgmt.customer.domain.dto;

import com.tfs.order.mgmt.customer.enums.ActionType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CustomerCreditLimitEventDTO {
	
	private String orderId;
	private String customerId;
	private ActionType actionType;
	private Double creditLimitAmount;
	
	
}
