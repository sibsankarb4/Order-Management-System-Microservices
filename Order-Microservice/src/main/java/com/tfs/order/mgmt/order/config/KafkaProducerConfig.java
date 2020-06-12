package com.tfs.order.mgmt.order.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.tfs.order.mgmt.order.model.dto.CustomerCreditLimitEventDTO;
import com.tfs.order.mgmt.order.model.dto.ProductCountEventDTO;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${kafka.message.exchangeEventTopic.name}")
    private String exchangeEventTopicName;
    @Value(value = "${kafka.message.productTopic.name}")
    private String productCountTopic;
    @Value(value = "${kafka.message.customerTopic.name}")
    private String customerCreditTopic;
    @Value(value = "${kafka.acks}")
    private String acknowledges;
    @Value(value = "${kafka.retries}")
    private int retryAttempts;
    @Value(value = "${kafka.retry.backoff.ms}")
    private int retryAttemptInterval;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        configProps.put(ProducerConfig.ACKS_CONFIG, acknowledges);
        configProps.put(ProducerConfig.RETRIES_CONFIG, retryAttempts);
        configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, retryAttemptInterval);
        
        return new DefaultKafkaProducerFactory<>(configProps);
    }
    
    @Bean
    public NewTopic productCountEvent() {
        return TopicBuilder.name(productCountTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic creditLimitEvent() {
        return TopicBuilder.name(customerCreditTopic)
                .partitions(3)
                .replicas(1)
                .build();
    }
    
    @Bean
    public NewTopic exchangeEventTopic() {
        return TopicBuilder.name(exchangeEventTopicName)
                .partitions(3)
                .replicas(1)
                .build();
    }

    @Bean
    public KafkaTemplate<String, String> orderKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    @Bean
    public ProductCountEventDTO getProductCountEventDTO() {
        return new ProductCountEventDTO();
    }
    
    @Bean
    public CustomerCreditLimitEventDTO getCustomerCreditLimitEventDTO() {
        return new CustomerCreditLimitEventDTO();
    }
}
