package com.tfs.order.mgmt.customer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.tfs.order.mgmt.customer.constant.Constants;
import com.tfs.order.mgmt.customer.domain.dto.CustomerCreditLimitEventDTO;

@Configuration
public class KafkaProducerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${kafka.message.exchangeTopic.name}")
    private String exchangeTopicName;
    @Value(value = "${kafka.message.customerTopic.name}")
    private String customerTopicName;
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
    public NewTopic exchangeEvents() {
        return TopicBuilder.name(exchangeTopicName)
                .partitions(3)
                .replicas(3)
                .build();
    }
    
    @Bean
    public NewTopic customerEvents() {
        return TopicBuilder.name(customerTopicName)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public KafkaTemplate<String, String> customKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    private ConsumerFactory<String, CustomerCreditLimitEventDTO> customerCreditLimitConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.KAFKA_GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(CustomerCreditLimitEventDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, CustomerCreditLimitEventDTO> customerCreditLimitKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, CustomerCreditLimitEventDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(customerCreditLimitConsumerFactory());
        return factory;
    }

}
