package com.tfs.order.mgmt.customer.listener;

import java.util.concurrent.CountDownLatch;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfs.order.mgmt.customer.domain.dto.CustomerCreditLimitEventDTO;
import com.tfs.order.mgmt.customer.domain.dto.ExchangeEvent;
import com.tfs.order.mgmt.customer.handler.CustomHandlerInterceptor;
import com.tfs.order.mgmt.customer.service.CustomerService;

import lombok.extern.slf4j.Slf4j;

/**
 * 
 * @author AmlanSaha
 *
 */
@Component
@Slf4j
public class CustomerCreditLimitListener {
	
@Autowired
ObjectMapper objectMapper;
	
@Autowired
CustomerService customerService;
private static final Logger logger = LogManager.getLogger(CustomerCreditLimitListener.class);
	
private CountDownLatch CustomerCreditLimitLatch = new CountDownLatch(1);
	
@KafkaListener(topics = "${kafka.message.customerTopic.name}", containerFactory = "customerCreditLimitKafkaListenerContainerFactory")
public void exchangeEventListener(String customerCreditLimitStr) {
	System.out.println("************" + customerCreditLimitStr);
	try {
		CustomerCreditLimitEventDTO customerCreditLimitEvent = objectMapper.readValue(customerCreditLimitStr, CustomerCreditLimitEventDTO.class);
		logger.info("Recieved message: " + customerCreditLimitEvent);
		customerService.updateCustomerCreditLimit(customerCreditLimitEvent);
		this.CustomerCreditLimitLatch.countDown();

	} catch (JsonProcessingException e) {
		logger.error("Error Sending the Message and the exception is {}", e.getMessage());
	}
  }
}
