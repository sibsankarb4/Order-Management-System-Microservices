package com.tfs.order.mgmt.event.consumer.service;

import com.tfs.order.mgmt.event.consumer.domain.entity.ExchangeEvent;

import reactor.core.publisher.Mono;

/**
 * This Service interface is responsible to expose following contracts of Customer entity :
 * <ol>
 * 		<li>API to responsible to create a new Exchange Event.</li>
 * </ol> 
 * @author AmlanSaha
 *
 */
public interface ExchangeEventService {

	/**
	 * This API is responsible to create a new ExchangeEvent
	 * 
	 * @param exchangeEvent
	 * 			Exchange Event
	 * 
	 * @author AmlanSaha
	 * 
	 * @return Mono<ExchangeEvent>
	 */
	ExchangeEvent saveExchangeEvent(ExchangeEvent exchangeEvent);
}
