package com.tfs.product.model;

import org.springframework.http.HttpMethod;

import com.tfs.product.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class ExchangeEvent {
	
	private String traceId;
	private EventType eventType;
	private String uri;
	private HttpMethod httpMethodType;
	private String requestedAt;
	private String executionDuration;
	private String executioncompletedAt;
	private Integer statusCode;
	private String errorMsg;
}
