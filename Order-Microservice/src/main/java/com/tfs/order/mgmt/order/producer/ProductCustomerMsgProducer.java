package com.tfs.order.mgmt.order.producer;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfs.order.mgmt.order.model.dto.CustomerCreditLimitEventDTO;
import com.tfs.order.mgmt.order.model.dto.ProductCountEventDTO;


@Service
public class ProductCustomerMsgProducer {

	private static final Logger LOG = LoggerFactory.getLogger(ProductCustomerMsgProducer.class);
	
	@Autowired
	private KafkaTemplate<String, String> orderKafkaTemplate;
	
	@Autowired
	ObjectMapper objectMapper;

	@Value(value = "${kafka.message.productTopic.name}")
	private String productTopicName;
	
	@Value(value = "${kafka.message.customerTopic.name}")
	private String customerTopicName;

	public void sendToProduct(ProductCountEventDTO productCountEventDTO) throws JsonProcessingException {

	
		String productMessage = objectMapper.writeValueAsString(productCountEventDTO);
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		ListenableFuture<SendResult<String, String>> future = orderKafkaTemplate.send(productTopicName, productMessage);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				LOG.info("Sent message successfully");
			}

			@Override
			public void onFailure(Throwable ex) {
				LOG.debug("Unable to send message due to : " + ex.getMessage());
			}
		});

	}
	
	public void sendToCustomer(CustomerCreditLimitEventDTO customerCreditLimitEventDTO) throws JsonProcessingException {

		
		String customerMessage = objectMapper.writeValueAsString(customerCreditLimitEventDTO);
	
		objectMapper.setSerializationInclusion(Include.NON_NULL);
		ListenableFuture<SendResult<String, String>> future = orderKafkaTemplate.send(customerTopicName, customerMessage);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {

			@Override
			public void onSuccess(SendResult<String, String> result) {
				LOG.info("Sent message successfully");
			}

			@Override
			public void onFailure(Throwable ex) {
				LOG.debug("Unable to send message due to : " + ex.getMessage());
			}
		});

	}
}
