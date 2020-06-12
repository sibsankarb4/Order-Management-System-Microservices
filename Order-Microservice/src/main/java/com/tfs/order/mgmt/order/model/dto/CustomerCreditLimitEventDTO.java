package com.tfs.order.mgmt.order.model.dto;

import com.tfs.order.mgmt.order.enums.ActionType;

public class CustomerCreditLimitEventDTO {
	
	public String orderId;
	public String customerId;
	public double creditLimitAmount;
	public ActionType actionType;
	
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}	
	
	public double getCreditLimitAmount() {
		return creditLimitAmount;
	}
	public void setCreditLimitAmount(double creditLimitAmount) {
		this.creditLimitAmount = creditLimitAmount;
	}
	public ActionType getActionType() {
		return actionType;
	}
	public void setActionType(ActionType actionType) {
		this.actionType = actionType;
	}
	
}
