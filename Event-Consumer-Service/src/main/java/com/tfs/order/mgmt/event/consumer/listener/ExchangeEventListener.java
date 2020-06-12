package com.tfs.order.mgmt.event.consumer.listener;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfs.order.mgmt.event.consumer.domain.entity.ExchangeEvent;
import com.tfs.order.mgmt.event.consumer.service.ExchangeEventService;

import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

/**
 * 
 * @author AmlanSaha
 *
 */
@Component
@Slf4j
public class ExchangeEventListener {
	
	@Autowired
    ObjectMapper objectMapper;
	
	private static final Logger logger = LogManager.getLogger(ExchangeEventListener.class);
	
	@Autowired
	ExchangeEventService exchangeEventService;
	
	private CountDownLatch exchangeEventLatch = new CountDownLatch(1);
	
	@KafkaListener(topics = "${kafka.message.exchangeTopic.name}", containerFactory = "exchangeEventKafkaListenerContainerFactory")
    public void exchangeEventListener(String exchangeEventStr) {
		
		try {
			ExchangeEvent exchangeEvent = objectMapper.readValue(exchangeEventStr, ExchangeEvent.class);
			logger.info("Recieved message: " + exchangeEvent);
			exchangeEventService.saveExchangeEvent(exchangeEvent);
	        this.exchangeEventLatch.countDown();
		} catch (JsonProcessingException e) {
			logger.error("Error Sending the Message and the exception is {}", e.getMessage());
		}
    }
}
