package com.javainuse.service;

import org.springframework.kafka.annotation.KafkaListener;

public class KafkaConsumer {



    @KafkaListener(topics = "KafkaFirst",containerFactory = "kafkaListenerContainerFactory")
    public void consume(String message){
        System.out.println("message received " + message);
    }
}
