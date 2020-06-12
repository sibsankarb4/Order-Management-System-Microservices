/**
 * 
 */
package com.tfs.product.consumer;

import java.util.concurrent.CountDownLatch;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tfs.product.model.ProductCountEventDTO;
import com.tfs.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

/**
 * @author AvkashKumar
 *
 */
@Service

public class ProductCountConsumer {
	
	@Autowired
    ObjectMapper objectMapper;
	
	@Autowired
	ProductService productService;
	
	
	@KafkaListener(topics = "${kafka.message.productTopic.name}", containerFactory = "productCountLimitKafkaListenerContainerFactory")
    public void exchangeEventListener(String productCountlimit) {
		
		try {
			ProductCountEventDTO productCountEventDTO = objectMapper.readValue(productCountlimit, ProductCountEventDTO.class);
			productService.updateProductCount(productCountEventDTO);
	       
	        
		} catch (JsonProcessingException e) {
			//log.error("Error Sending the Message and the exception is {}", e.getMessage());
		}
    }
}
