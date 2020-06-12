package com.tfs.order.mgmt.event.consumer.service.impl;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.ReactiveMongoTemplate;
import org.springframework.stereotype.Service;

import com.tfs.order.mgmt.event.consumer.domain.entity.ExchangeEvent;
import com.tfs.order.mgmt.event.consumer.repository.ExchangeEventReactiveRepository;
import com.tfs.order.mgmt.event.consumer.service.ExchangeEventService;

/**
 * This Service Implementation class is responsible to for the following functionality of Exchange Event entity :
 * <ol>
 * 		<li>API to responsible to create a new Exchange Event.</li>
 * </ol> 
 * 
 * @author AmlanSaha
 */
@Service
public class ExchangeEventServiceImpl implements ExchangeEventService {

	@Autowired
	private ExchangeEventReactiveRepository exchangeEventReactiveRepository;  
	
	@Autowired
	ReactiveMongoTemplate reactiveMongoTemplate;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ExchangeEvent saveExchangeEvent(ExchangeEvent exchangeEvent) {
		exchangeEvent.setId(UUID.randomUUID().toString());
		return exchangeEventReactiveRepository.insert(exchangeEvent).block(); 
	}
}
