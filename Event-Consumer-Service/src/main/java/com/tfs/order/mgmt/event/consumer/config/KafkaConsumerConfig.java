package com.tfs.order.mgmt.event.consumer.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.tfs.order.mgmt.event.consumer.constant.Constants;
import com.tfs.order.mgmt.event.consumer.domain.entity.ExchangeEvent;

@EnableKafka
@Configuration
public class KafkaConsumerConfig {

    @Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
    
    private ConsumerFactory<String, ExchangeEvent> exchangeEventConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, Constants.KAFKA_GROUP_ID);
        return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(ExchangeEvent.class));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, ExchangeEvent> exchangeEventKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, ExchangeEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(exchangeEventConsumerFactory());
        return factory;
    }
}
