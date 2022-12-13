package com.javainuse.config;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.kafka.KafkaAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Component
public class KafkaConfiguration {

@Value(value = "${kafka.connect.url}")
private String bootstrapServer;

@Bean
public KafkaTemplate<String, String> configureProducer(@Value ("${kafka.connect.url}")String bootstrapServer){
    return new KafkaTemplate<>(new DefaultKafkaProducerFactory<>(producerConfig(bootstrapServer)));
}

private Map<String, Object> producerConfig(String bootstarpServer){
    final Map<String, Object> map = new HashMap<>();
    map.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstarpServer);
    map.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    map.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
    return map;
}

    public ConsumerFactory<String, String> consumerFactory()
    {
        Map<String, Object> config = new HashMap<>();
        // Adding the Configuration
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,"localhost:9092");
        config.put(ConsumerConfig.GROUP_ID_CONFIG,"group_id");
        config.put( ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,StringDeserializer.class);

        return new DefaultKafkaConsumerFactory<>(config);
    }

    //Listner
   // @ConditionalOnMissingBean(ConsumerFactory.class)
    @Bean
    @ConditionalOnMissingBean(name = "kafkaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, String> KafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }

 }
