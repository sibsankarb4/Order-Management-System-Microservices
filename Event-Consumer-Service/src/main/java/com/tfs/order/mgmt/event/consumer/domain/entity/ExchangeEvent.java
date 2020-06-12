package com.tfs.order.mgmt.event.consumer.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.tfs.order.mgmt.event.consumer.enums.EventType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@JsonInclude(Include.NON_NULL)
@Document("exchange_events")
public class ExchangeEvent {
	
	@Id
	private String id;
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
