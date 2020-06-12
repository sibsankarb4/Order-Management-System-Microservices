package com.tfs.order.mgmt.order.producer;

import java.math.BigInteger;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfs.order.mgmt.order.model.dto.ExchangeEvent;

import lombok.extern.slf4j.Slf4j;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * 
 * @author AmlanSaha
 *
 */
@Component
@Slf4j
public class ExchangeEventProducer {
	
	@Autowired
    private KafkaTemplate<String, String> customKafkaTemplate;
    @Autowired
    ObjectMapper objectMapper;

    @Value(value = "${kafka.message.exchangeEventTopic.name}")
    private String topicName;
    
    /**
     * 
     * @param customerEvent
     * @return
     * @throws JsonProcessingException
     */
    
    private static final Logger logger = LogManager.getLogger(ExchangeEventProducer.class);

    
    public ListenableFuture<SendResult<String, String>> sendExchangeEvent(ExchangeEvent exchangeEvent) throws JsonProcessingException {

		String message = objectMapper.writeValueAsString(exchangeEvent);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		
		String lUUID = String.format("%040d", new BigInteger(UUID.randomUUID().toString().replace("-", ""), 16));
		
		ListenableFuture<SendResult<String, String>> listenableFuture = customKafkaTemplate.send(topicName, lUUID, message);

		listenableFuture.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onFailure(Throwable ex) {
				handleFailure(ex);
			}

			@Override
			public void onSuccess(SendResult<String, String> result) {
				handleSuccess(message, result);
			}
		});

        return listenableFuture;
    }

    /**
     * 
     * @param ex
     */
    private void handleFailure(Throwable ex) {
    	logger.error("Error Sending the Message and the exception is {}", ex.getMessage());
        try {
            throw ex;
        } catch (Throwable throwable) {
        	logger.error("Error in OnFailure: {}", throwable.getMessage());
        }
    }

    /**
     * 
     * @param value
     * @param result
     */
    private void handleSuccess(String value, SendResult<String, String> result) {
    	logger.info("Message Sent SuccessFully for the value is {} , partition is {}", value, result.getRecordMetadata().partition());
    }
}
