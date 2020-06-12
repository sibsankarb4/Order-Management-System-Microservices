package com.tfs.product.handler;

import org.joda.time.DateTime;
import org.joda.time.Seconds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.tfs.product.contstant.ProductConstants;
import com.tfs.product.enums.EventType;
import com.tfs.product.model.ExchangeEvent;
import com.tfs.product.producer.ExchangeEventProducer;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;


/**
 * 
 * @author AmlanSaha
 */
@Component
@Slf4j
public class CustomHandlerInterceptor implements WebFilter {

	@Autowired
	ExchangeEventProducer exchangeEventProducer;
	
	private static final Logger logger = LogManager.getLogger(CustomHandlerInterceptor.class);
	
	/**
	 * 
	 * 
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
		
		ExchangeEvent exchangeEvent = new ExchangeEvent();
		
		exchangeEvent.setTraceId(exchange.getRequest().getId());
		exchangeEvent.setEventType(EventType.ORDER_MS_REQUEST);
		exchangeEvent.setUri(exchange.getRequest().getURI().toString());
		exchangeEvent.setHttpMethodType(exchange.getRequest().getMethod());
		
		DateTime startedTime = DateTime.now();
		exchangeEvent.setRequestedAt(startedTime.toString(ProductConstants.DATE_FORMAT));
		
		ServerHttpResponse response = exchange.getResponse();
		
		return chain.filter(exchange)
				.doOnSuccess((done) -> successResponseEventLog(exchange, response, exchangeEvent, startedTime))
				.doOnError((cause) -> {
					if (response.isCommitted()) {
			            errorResponseEventLog(exchange, exchangeEvent, response, cause, startedTime);
			        }  else {
			            response.beforeCommit(() -> {
			                errorResponseEventLog(exchange, exchangeEvent, response, cause, startedTime);
			                return Mono.empty();
			            });
			        }
				});
	}
	
	/**
	 * 
	 * @param exchange
	 * @param exchangeEvent
	 */
	private void successResponseEventLog(ServerWebExchange exchange, ServerHttpResponse response, ExchangeEvent exchangeEvent, DateTime startedTime) {
		try {
			DateTime completedTime = DateTime.now();
			exchangeEvent.setExecutionDuration(Seconds.secondsBetween(startedTime, completedTime).getSeconds() % 60 + " seconds");
			exchangeEvent.setExecutioncompletedAt(completedTime.toString(ProductConstants.DATE_FORMAT));
			exchangeEvent.setStatusCode(response.getRawStatusCode());
			exchangeEventProducer.sendExchangeEvent(exchangeEvent);
		} catch (JsonProcessingException e) {
			logger.error("Error Sending the Message and the exception is {}", e.getMessage());
		}
	}

	/**
	 * 
	 * @param exchange
	 * @param exchangeEvent
	 * @param cause
	 */
	private void errorResponseEventLog(ServerWebExchange exchange, ExchangeEvent exchangeEvent, ServerHttpResponse response, Throwable cause, DateTime startedTime) {
		try {
			DateTime completedTime = DateTime.now();
			exchangeEvent.setExecutionDuration(Seconds.secondsBetween(startedTime, completedTime).getSeconds() % 60 + " seconds");
			exchangeEvent.setExecutioncompletedAt(completedTime.toString(ProductConstants.DATE_FORMAT));
			exchangeEvent.setStatusCode(response.getRawStatusCode());
			exchangeEvent.setErrorMsg(cause.getMessage());
			exchangeEventProducer.sendExchangeEvent(exchangeEvent);
		} catch (JsonProcessingException e) {
			logger.error("Error Sending the Message and the exception is {}", e.getMessage());
		}
	}
}
