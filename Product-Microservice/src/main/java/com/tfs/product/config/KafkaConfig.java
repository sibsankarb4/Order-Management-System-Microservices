package com.tfs.product.config;

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
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


import com.tfs.product.contstant.ProductConstants;
import com.tfs.product.model.ProductCountEventDTO;
/**
 * @author AvkashKumar
 *
 */
@EnableKafka
@Configuration
public class KafkaConfig {
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    @Value(value = "${kafka.message.exchangeTopic.name}")
    private String exchangeTopicName;
    @Value(value = "${kafka.message.productTopic.name}")
    private String productTopicName;
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
    public NewTopic productEvents() {
        return TopicBuilder.name(productTopicName)
                .partitions(3)
                .replicas(3)
                .build();
    }

    @Bean
    public KafkaTemplate<String, String> customKafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }
    
    
    private ConsumerFactory<String, ProductCountEventDTO> productCountLimittConsumerFactory() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, ProductConstants.KAFKA_GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(config, new StringDeserializer(), new JsonDeserializer<>(ProductCountEventDTO.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ProductCountEventDTO> productCountLimitKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ProductCountEventDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(productCountLimittConsumerFactory());
        return factory;
    }

}
